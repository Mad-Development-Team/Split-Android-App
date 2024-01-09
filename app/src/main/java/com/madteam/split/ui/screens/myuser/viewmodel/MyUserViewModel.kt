package com.madteam.split.ui.screens.myuser.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyUserViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<MyUserUIState> = MutableStateFlow(MyUserUIState())
    val state: StateFlow<MyUserUIState> = _state

    fun onEvent(event: MyGroupsUIEvent) {
        when (event) {
            MyGroupsUIEvent.OnCreateNewGroupClick -> {
                //TODO: Not implemented yet
            }
        }
    }

}