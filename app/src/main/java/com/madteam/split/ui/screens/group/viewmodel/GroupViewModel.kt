package com.madteam.split.ui.screens.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.group.GroupRepository
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
) : ViewModel() {

    private val _state: MutableStateFlow<GroupUIState> = MutableStateFlow(GroupUIState())
    val state: StateFlow<GroupUIState> = _state

    private var lastUpdatedGroupListener: ValueEventListener? = null
    private val lastUpdatedGroupsRef = realtime.reference.child("lastUpdatedGroups")

    init {
        getUserGroups()
        getCurrentGroup()
        resetLastTimeGroupUpdatedFromLocalToZero()
        obtainLastTimeGroupUpdated()
    }

    private fun getCurrentGroup() {
        _state.value = _state.value.copy(
            currentGroupId = groupRepository.getCurrentGroup()
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
            //It works so when it needs update, it have to be updated
            //the expenses of the group
            //Then on GroupExpenses access to the common state to read the expenses
            //and update the UI
            println("Needs update")
        }
    }

    override fun onCleared() {
        super.onCleared()
        lastUpdatedGroupListener?.let { lastUpdatedGroupsRef.removeEventListener(it) }
    }
}