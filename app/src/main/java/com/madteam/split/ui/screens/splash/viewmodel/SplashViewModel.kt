package com.madteam.split.ui.screens.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.model.utils.AuthResult
import com.madteam.split.data.repository.authentication.AuthenticationRepository
import com.madteam.split.data.repository.datastore.DatastoreManager
import com.madteam.split.data.repository.expense.ExpenseRepository
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.data.repository.user.UserRepository
import com.madteam.split.ui.screens.splash.state.SplashUIState
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val expenseRepository: ExpenseRepository,
    private val datastoreManager: DatastoreManager,
) : ViewModel() {

    private val _state: MutableStateFlow<SplashUIState> = MutableStateFlow(SplashUIState())
    val state: StateFlow<SplashUIState> = _state

    init {
        checkIfUserIsAuthenticated()
    }

    private fun checkIfUserHasDefaultGroup() {
        viewModelScope.launch {
            try {
                val defaultGroup = datastoreManager.getInt("mainGroupId")
                if (defaultGroup == null) {
                    _state.value = _state.value.copy(
                        defaultGroup = null
                    )
                } else {
                    groupRepository.setCurrentGroup(defaultGroup)
                    _state.value = _state.value.copy(
                        defaultGroup = defaultGroup
                    )
                }
            } catch (e: Exception) {
                println("Error retrieving default group")
            }
            onReadyToGo()
        }
    }

    private fun checkIfUserIsAuthenticated() {
        viewModelScope.launch {
            val result = authenticationRepository.authenticate()
            _state.value = _state.value.copy(
                isAuthenticated = result
            )
            when (result) {
                is AuthResult.Authorized -> {
                    getUserInfo()
                    getUserGroups()
                }

                else -> {
                    deleteDataFromDatabase()
                    onReadyToGo()
                }
            }
        }
    }

    private fun getUserGroups() {
        viewModelScope.launch {
            groupRepository.getUserGroups(
                update = true
            )
            checkIfUserHasDefaultGroup()
        }
    }

    private fun deleteDataFromDatabase() {
        viewModelScope.launch {
            userRepository.deleteAllUserLocalInfo()
            groupRepository.deleteAllGroups()
            expenseRepository.deleteAllExpenses()
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when (val result = userRepository.getUserInfo()) {
                is Resource.Success -> {
                    result.data.let {
                        _state.value = _state.value.copy(
                            userInfo = it
                        )
                    }
                }

                else -> {
                    //Do nothing
                }
            }
        }
    }

    private fun onReadyToGo() {
        _state.value = _state.value.copy(
            isReadyToGo = true
        )
    }
}