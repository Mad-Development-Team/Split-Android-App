package com.madteam.split.ui.screens.mygroups.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.madteam.split.R
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.User
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIEvent
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import com.madteam.split.ui.screens.mygroups.viewmodel.MyGroupsViewModel
import com.madteam.split.ui.theme.GroupSettingsModalBottomSheet
import com.madteam.split.ui.theme.InfoMessage
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.misc.VibrationUtils
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun MyGroupsScreen(
    navController: NavController,
    viewModel: MyGroupsViewModel = hiltViewModel(),
) {

    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.groupSelected != null) {
        GroupSettingsModalBottomSheet(
            group = state.groupSelected!!,
            isDefault = state.groupSelected!!.id == state.defaultGroup,
            onClose = {
                viewModel.onEvent(MyGroupsUIEvent.OnGroupSelected(null, false))
            },
            onGroupDefault = { selectedGroupId ->
                viewModel.onEvent(
                    MyGroupsUIEvent.OnGroupSelectedAsDefault(
                        selectedGroupId
                    )
                )
            }
        )
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MyGroupsContent(
                state = state,
                onRefreshGroups = {
                    viewModel.onEvent(MyGroupsUIEvent.OnRefreshGroupsList)
                },
                onGroupSelected = { group, isDefault ->
                    viewModel.onEvent(MyGroupsUIEvent.OnGroupSelected(group, isDefault))
                },
                navigateTo = navController::navigate,
                popUpTo = { route, groupId ->
                    viewModel.onEvent(MyGroupsUIEvent.OnGroupClicked(groupId))
                    navController.navigateWithPopUpTo(
                        route = route,
                        popUpTo = Screens.MyGroupsScreen.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}

@Composable
fun MyGroupsContent(
    state: MyGroupsUIState,
    onRefreshGroups: () -> Unit,
    navigateTo: (String) -> Unit,
    popUpTo: (String, Int) -> Unit,
    onGroupSelected: (Group, Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isGroupsListLoading)
        MyGroupsTopBar(
            userInfo = state.userInfo,
            navigateTo = navigateTo
        )
        Spacer(modifier = Modifier.size(8.dp))
        AnimatedVisibility(visible = state.defaultGroup == null && state.userGroups.isNotEmpty()) {
            InfoMessage(
                messageText = R.string.select_default_group_info_message,
                titleText = R.string.pro_tip,
            )
        }
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                onRefreshGroups()
            }
        ) {
            LazyColumn(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(state.userGroups) { _, group ->
                    GroupListItem(
                        group = group,
                        isDefault = state.defaultGroup == group.id,
                        onGroupSelected = { selected, isDefault ->
                            onGroupSelected(selected, isDefault)
                        },
                        onGroupClicked = { clickedGroup ->
                            popUpTo(Screens.GroupInfoScreen.route, clickedGroup.id)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        PrimaryLargeButton(
            onClick = { /*TODO*/ },
            text = R.string.received_an_invitation
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.or),
            style = SplitTheme.typography.body.xxl,
            color = SplitTheme.colors.neutral.textTitle
        )
        Spacer(modifier = Modifier.size(16.dp))
        SecondaryLargeButton(
            onClick = {
                navigateTo(Screens.CreateGroupInfoScreen.route)
            },
            text = R.string.create_a_new_group
        )
    }
}

@Composable
fun MyGroupsTopBar(
    userInfo: User,
    navigateTo: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.your_groups),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
            ProfileImage(
                modifier = Modifier,
                userInfo = userInfo,
                size = 48,
                isClickable = true,
                onClick = {
                    navigateTo(Screens.MyUserScreen.route)
                }
            )
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GroupListItem(
    group: Group,
    isDefault: Boolean = false,
    onGroupSelected: (Group, Boolean) -> Unit,
    onGroupClicked: (Group) -> Unit,
) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .size(124.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        onGroupSelected(group, isDefault)
                        VibrationUtils.vibrate(
                            context = context,
                            duration = 50
                        )
                    },
                    onTap = {
                        onGroupClicked(group)
                    }
                )
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp,
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.secondary.backgroundMedium
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (backgroundImage, groupName, members, default) = createRefs()
            if (group.bannerImage.isNotEmpty()) {
                GlideImage(
                    modifier = Modifier
                        .constrainAs(backgroundImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.Crop,
                    model = group.bannerImage,
                    contentDescription = stringResource(id = R.string.group_banner_image)
                )
            } else {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rotate(180f)
                        .constrainAs(backgroundImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.default_group_banner_image),
                    contentDescription = stringResource(id = R.string.group_banner_image),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black,
                                Color.Transparent
                            ),
                            startY = Float.POSITIVE_INFINITY,
                            endY = 0f
                        )
                    )
            )
            MembersListItemList(
                members = group.members,
                modifier = Modifier
                    .constrainAs(members) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )
            Text(
                modifier = Modifier
                    .constrainAs(groupName) {
                        bottom.linkTo(members.top)
                        start.linkTo(members.start)
                    },
                text = group.name,
                style = SplitTheme.typography.heading.s,
                color = SplitTheme.colors.neutral.textExtraWeak,
            )
            if (isDefault) {
                Icon(
                    modifier = Modifier
                        .constrainAs(default) {
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
                    tint = SplitTheme.colors.neutral.iconExtraWeak,
                    imageVector = Icons.Filled.Home,
                    contentDescription = null
                )
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MembersListItemList(
    modifier: Modifier = Modifier,
    members: List<Member>,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-8).dp)
    ) {
        members.forEachIndexed { _, member ->
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape
                    )
                    .background(
                        color = SplitTheme.colors.secondary.backgroundMedium,
                        shape = CircleShape
                    )
                    .size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!member.profileImage.isNullOrBlank()) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            model = member.profileImage,
                            contentDescription = stringResource(
                                id = R.string.user_profile_image_description
                            ),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Text(
                            text = member.name.firstOrNull().toString().uppercase(),
                            style = SplitTheme.typography.heading.xxs,
                            color = SplitTheme.colors.neutral.textTitle,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    color = SplitTheme.colors.secondary.backgroundMedium,
                                    shape = CircleShape
                                ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
