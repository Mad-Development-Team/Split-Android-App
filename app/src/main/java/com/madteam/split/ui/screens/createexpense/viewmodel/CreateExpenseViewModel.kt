package com.madteam.split.ui.screens.createexpense.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIEvent
import com.madteam.split.ui.screens.createexpense.state.CreateExpenseUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(

) : ViewModel() {

    private val _state: MutableStateFlow<CreateExpenseUIState> =
        MutableStateFlow(CreateExpenseUIState())
    val state: StateFlow<CreateExpenseUIState> = _state

    fun onEvent(event: CreateExpenseUIEvent) {
        when (event) {
            else -> {
                // Do nothing
            }
        }
    }
}