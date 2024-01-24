package com.madteam.split.ui.screens.mygroups.state

import com.madteam.split.domain.model.Group

sealed class MyGroupsUIEvent {
    data object OnCreateNewGroupClick : MyGroupsUIEvent()
    data object OnRefreshGroupsList : MyGroupsUIEvent()
    data class OnGroupSelected(val group: Group?, val isDefault: Boolean) : MyGroupsUIEvent()
    data class OnGroupSelectedAsDefault(val groupId: Int) : MyGroupsUIEvent()
}
