package com.madteam.split.ui.screens.creategroup.info.state

import com.madteam.split.domain.model.Currency

data class CreateGroupInfoUIState(
    val groupName: String = "",
    val groupDescription: String = "",
    val isGroupNameValid: Boolean = false,
    val isGroupDescriptionValid: Boolean = true,
    val currencySelected: Currency = Currency("EUR", "Euro", "€"),
)
