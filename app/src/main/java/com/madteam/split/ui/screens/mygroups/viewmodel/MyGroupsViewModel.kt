package com.madteam.split.ui.screens.mygroups.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.domain.model.Group
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGroupsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val dataStoreManager: DatastoreManager,
) : ViewModel() {

    private val _state = MutableStateFlow(MyGroupsUIState())
    val state: StateFlow<MyGroupsUIState> = _state

    init {
        getUserInfo()
        getUserGroups(
            update = false
        )
        retrieveDefaultGroup()
    }

    fun onEvent(event: MyGroupsUIEvent) {
        when (event) {
            is MyGroupsUIEvent.OnCreateNewGroupClick -> {
                //Not implemented yet
            }

            is MyGroupsUIEvent.OnRefreshGroupsList -> {
                reloadUserGroups()
            }

            is MyGroupsUIEvent.OnGroupSelected -> {
                setSelectedGroup(
                    group = event.group,
                    isDefault = event.isDefault
                )
            }

            is MyGroupsUIEvent.OnGroupSelectedAsDefault -> {
                setSelectedGroupAsDefault(event.groupId)
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when (val user = userRepository.getUserInfo()) {
                is Resource.Success -> {
                    user.data.let {
                        _state.value = _state.value.copy(
                            userInfo = it
                        )
                    }
                }

                is Resource.Error -> {
                    println("Error getting user information")
                }

                is Resource.Loading -> {
                    //Not necessary
                }
            }
        }
    }

    private fun getUserGroups(
        update: Boolean = false,
    ) {
        viewModelScope.launch {
            val groups = groupRepository.getUserGroups(
                update = update
            )
            when (groups) {
                is Resource.Success -> {
                    groups.data.let {
                        _state.value = _state.value.copy(
                            userGroups = it
                        )
                    }
                }

                is Resource.Error -> {
                    println("Error getting user groups")
                }

                is Resource.Loading -> {
                    //Not necessary
                }
            }
        }
    }

    private fun reloadUserGroups() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isGroupsListLoading = true
            )
            getUserGroups(
                update = true
            )
            _state.value = _state.value.copy(
                isGroupsListLoading = false
            )
        }
    }

    private fun setSelectedGroup(group: Group?, isDefault: Boolean) {
        _state.value = _state.value.copy(
            groupSelected = group,
            groupSelectedIsDefault = isDefault
        )
    }

    private fun retrieveDefaultGroup() {
        viewModelScope.launch {
            try {
                val defaultGroup = dataStoreManager.getString("mainGroupId")
                if (defaultGroup.isNullOrBlank()) {
                    _state.value = _state.value.copy(
                        defaultGroup = ""
                    )
                } else {
                    _state.value = _state.value.copy(
                        defaultGroup = defaultGroup
                    )
                }
            } catch (e: Exception) {
                println("Error retrieving default group")
            }
        }
    }

    private fun setSelectedGroupAsDefault(groupId: Int) {
        viewModelScope.launch {
            try {
                val actualMainGroup = dataStoreManager.getString("mainGroupId")
                if (actualMainGroup.isNullOrBlank() || actualMainGroup != groupId.toString()) {
                    dataStoreManager.saveString("mainGroupId", groupId.toString())
                    _state.value = _state.value.copy(
                        defaultGroup = groupId.toString()
                    )
                } else {
                    dataStoreManager.removeValue("mainGroupId")
                    _state.value = _state.value.copy(
                        defaultGroup = ""
                    )
                }
            } catch (e: Exception) {
                println("Error saving default group")
            }
        }
    }
}