package com.madteam.split.ui.screens.creategroup.info.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madteam.split.data.repository.currency.CurrencyRepository
import com.madteam.split.data.repository.group.GroupRepository
import com.madteam.split.domain.model.Currency
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIEvent
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIState
import com.madteam.split.ui.utils.validateGroupName
import com.madteam.split.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAX_CHAR_GROUP_DESCRIPTION_LENGTH = 250

@HiltViewModel
class CreateGroupInfoViewModel @Inject constructor(
    private val createGroupRepository: GroupRepository,
    private val currencyRepository: CurrencyRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CreateGroupInfoUIState> =
        MutableStateFlow(CreateGroupInfoUIState())
    val state: StateFlow<CreateGroupInfoUIState> = _state

    init {
        getCurrencies()
    }

    fun onEvent(event: CreateGroupInfoUIEvent) {
        when (event) {
            is CreateGroupInfoUIEvent.OnGroupNameChange -> {
                changeGroupName(event.groupName)
            }

            is CreateGroupInfoUIEvent.OnGroupDescriptionChange -> {
                changeGroupDescription(event.groupDescription)
            }

            is CreateGroupInfoUIEvent.OnNextClick -> {
                saveGroupNameAndDescription()
            }

            is CreateGroupInfoUIEvent.OnShowError -> {
                setShowError(event.showError)
            }

            is CreateGroupInfoUIEvent.OnShowCurrencyDialog -> {
                onShowCurrencyDialog(event.showCurrencyDialog)
            }

            is CreateGroupInfoUIEvent.OnCurrencySelected -> {
                onCurrencySelected(event.currency)
            }
        }
    }

    private fun onCurrencySelected(currency: Currency) {
        _state.value = _state.value.copy(
            currencySelected = currency
        )
    }

    private fun onShowCurrencyDialog(showCurrencyDialog: Boolean) {
        _state.value = _state.value.copy(showCurrencyDialog = showCurrencyDialog)
    }

    private fun getCurrencies() {
        showLoading(true)
        viewModelScope.launch {
            val currencies = currencyRepository.getCurrencies()
            if (currencies is Resource.Success) {
                _state.value = _state.value.copy(
                    currencies = currencies.data,
                    currencySelected = currencies.data.firstOrNull { it.enabled }
                )
            } else {
                setShowError(true)
            }
        }
        showLoading(false)
    }

    private fun setShowError(showError: Boolean) {
        _state.value = _state.value.copy(isError = showError)
    }

    private fun showLoading(state: Boolean) {
        _state.value = _state.value.copy(isLoading = state)
    }

    private fun changeGroupDescription(groupDescription: String) {
        _state.value = _state.value.copy(
            groupDescription = groupDescription,
            isGroupDescriptionValid = groupDescription.length <= MAX_CHAR_GROUP_DESCRIPTION_LENGTH
        )
    }

    private fun changeGroupName(groupName: String) {
        _state.value = _state.value.copy(
            groupName = groupName,
            isGroupNameValid = validateGroupName(groupName)
        )
    }

    private fun saveGroupNameAndDescription() {
        createGroupRepository.setNameDescriptionAndCurrency(
            state.value.groupName,
            state.value.groupDescription,
            state.value.currencySelected
        )
    }
}