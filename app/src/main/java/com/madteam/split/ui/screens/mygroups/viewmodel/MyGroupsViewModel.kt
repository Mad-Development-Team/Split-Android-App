package com.madteam.split.ui.screens.mygroups.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyGroupsViewModel @Inject constructor(
): ViewModel() {

    private val _myGroupsUIState = MutableStateFlow(MyGroupsUIState())
    val myGroupsUIState: StateFlow<MyGroupsUIState> = _myGroupsUIState

    fun onEvent(event: MyGroupsUIEvent) {
        when (event) {
            is MyGroupsUIEvent.OnCreateNewGroupClick -> {
                //Not implemented yet
            }
        }
    }

}