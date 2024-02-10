package com.madteam.split.ui.screens.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.domain.model.Group
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val realtime: FirebaseDatabase,
    private val datastore: DatastoreManager,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<GroupUIState> = MutableStateFlow(GroupUIState())
    val state: StateFlow<GroupUIState> = _state

    private var lastUpdatedGroupListener: ValueEventListener? = null
    private val lastUpdatedGroupsRef = realtime.reference.child("lastUpdatedGroups")

    init {
        getUserGroups()
        getCurrentGroup()
        getGroupExpenses(update = false)
        resetLastTimeGroupUpdatedFromLocalToZero()
        obtainLastTimeGroupUpdated()
    }

    private fun getGroupExpenses(update: Boolean) {
        viewModelScope.launch {
            isLoadingState(true)
            val groupExpenses = expenseRepository.getGroupExpenses(
                groupId = _state.value.currentGroupId!!,
                update = update
            )
            if (groupExpenses is Resource.Success) {
                _state.value = _state.value.copy(
                    groupExpenses = groupExpenses.data
                )
            } else {
                _state.value = _state.value.copy(
                    errorRetrievingExpenses = true
                )
            }
            isLoadingState(false)
        }
    }

    private fun getCurrentGroup() {
        _state.value = _state.value.copy(
            currentGroupId = groupRepository.getCurrentGroup(),
            currentGroup = _state.value.userGroups.find { it.id == _state.value.currentGroupId }
                ?: Group()
        )
    }

    private fun isLoadingState(state: Boolean) {
        _state.value = _state.value.copy(
            isLoading = state
        )
    }

    private fun getUserGroups() {
        viewModelScope.launch {
            val response = groupRepository.getUserGroups()
            if (response is Resource.Success) {
                _state.value = _state.value.copy(
                    userGroups = response.data
                )
            }
        }
    }

    private fun resetLastTimeGroupUpdatedFromLocalToZero() = viewModelScope.launch {
        datastore.saveString(_state.value.currentGroupId.toString(), "0")
    }

    private fun obtainLastTimeGroupUpdated() {
        lastUpdatedGroupListener?.let { lastUpdatedGroupsRef.removeEventListener(it) }

        lastUpdatedGroupListener =
            lastUpdatedGroupsRef.child(_state.value.currentGroupId.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val lastUpdated = snapshot.value.toString()
                        getValueFromDatastore(lastUpdated)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Error: ${error.message}")
                    }
                })
    }

    private fun getValueFromDatastore(
        lastUpdated: String,
    ) = viewModelScope.launch {
        val localLastUpdated = datastore.getString(_state.value.currentGroupId.toString()) ?: "0"
        val needsUpdate = lastUpdated > localLastUpdated
        if (needsUpdate) {
            getGroupExpenses(update = true)
            updateDatastoreLastUpdatedIfNoError(lastUpdated)
        }
    }

    private fun updateDatastoreLastUpdatedIfNoError(lastUpdated: String) {
        viewModelScope.launch {
            if (lastUpdated != "0" && !_state.value.errorRetrievingExpenses) {
                datastore.saveString(_state.value.currentGroupId.toString(), lastUpdated)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lastUpdatedGroupListener?.let { lastUpdatedGroupsRef.removeEventListener(it) }
    }
}