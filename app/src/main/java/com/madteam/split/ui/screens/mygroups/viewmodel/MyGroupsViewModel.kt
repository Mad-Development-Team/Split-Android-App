package com.madteam.split.ui.screens.mygroups.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.user.UserRepository
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
): ViewModel() {

    private val _state = MutableStateFlow(MyGroupsUIState())
    val state: StateFlow<MyGroupsUIState> = _state

    init {
        getUserInfo()
    }

    fun onEvent(event: MyGroupsUIEvent) {
        when (event) {
            is MyGroupsUIEvent.OnCreateNewGroupClick -> {
                //Not implemented yet
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            val user = userRepository.getUserInfo()
            when (user) {
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
}