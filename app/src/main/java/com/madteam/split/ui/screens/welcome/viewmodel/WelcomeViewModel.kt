package com.madteam.split.ui.screens.welcome.viewmodel

import androidx.lifecycle.ViewModel
import com.madteam.split.ui.screens.welcome.state.WelcomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SECONDS_PER_PHASE = 5

@HiltViewModel
class WelcomeViewModel @Inject constructor(

) : ViewModel() {

    private val _welcomeScreenUIState = MutableStateFlow(WelcomeScreenUIState())
    val welcomeScreenUIState: StateFlow<WelcomeScreenUIState> = _welcomeScreenUIState

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        startProgress()
    }

    private fun startProgress() {
        scope.launch {
            while (true) {
                repeat(_welcomeScreenUIState.value.totalPhases) { phase ->
                    for (second in 1..SECONDS_PER_PHASE) {
                        delay(1000)
                        _welcomeScreenUIState.value = _welcomeScreenUIState.value.copy(
                            progressPhase = phase,
                            progressSeconds = second
                        )
                    }
                }
                delay(1000)
                resetProgress()
            }
        }
    }

    private fun resetProgress() {
        scope.launch {
            _welcomeScreenUIState.value = _welcomeScreenUIState.value.copy(
                progressPhase = 0,
                progressSeconds = 0
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}