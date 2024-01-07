package com.madteam.split.ui.screens.mygroups.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.AuthenticationRepository
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGroupsViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    private val _myGroupsUIState = MutableStateFlow(MyGroupsUIState())
    val myGroupsUIState: StateFlow<MyGroupsUIState> = _myGroupsUIState

    fun onEvent(event: MyGroupsUIEvent) {
        when (event) {
            is MyGroupsUIEvent.OnCreateNewGroupClick -> {
                signOut()
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authenticationRepository.signOut()
        }
    }
}