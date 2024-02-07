package com.madteam.split.ui.screens.creategroup.info.state

import com.madteam.split.domain.model.Currency

sealed class CreateGroupInfoUIEvent {
    data class OnGroupNameChange(val groupName: String) : CreateGroupInfoUIEvent()
    data class OnGroupDescriptionChange(val groupDescription: String) : CreateGroupInfoUIEvent()
    data object OnNextClick : CreateGroupInfoUIEvent()
    data class OnShowError(val showError: Boolean) : CreateGroupInfoUIEvent()
    data class OnShowCurrencyDialog(val showCurrencyDialog: Boolean) : CreateGroupInfoUIEvent()
    data class OnCurrencySelected(val currency: Currency) : CreateGroupInfoUIEvent()
}
