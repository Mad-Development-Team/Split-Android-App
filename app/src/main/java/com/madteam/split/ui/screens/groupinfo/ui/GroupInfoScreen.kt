package com.madteam.split.ui.screens.groupinfo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.group.state.GroupUIState
import com.madteam.split.ui.screens.group.viewmodel.GroupViewModel
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIEvent
import com.madteam.split.ui.screens.groupinfo.state.GroupInfoUIState
import com.madteam.split.ui.screens.groupinfo.viewmodel.GroupInfoViewModel
import com.madteam.split.ui.theme.DSBottomNavigation
import com.madteam.split.ui.theme.GroupNavigationTopAppBar
import com.madteam.split.ui.theme.GroupsListModalBottomSheet
import com.madteam.split.ui.theme.MembersHorizontalList
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun GroupInfoScreen(
    navController: NavController,
    viewModel: GroupInfoViewModel = hiltViewModel(),
    commonViewModel: GroupViewModel = hiltViewModel(),
) {
    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val commonState by commonViewModel.state.collectAsStateWithLifecycle()

    if (state.groupsModalIsVisible) {
        GroupsListModalBottomSheet(
            groupsList = commonState.userGroups,
            currentGroupId = commonState.currentGroupId!!,
            onClose = {
                viewModel.onEvent(
                    GroupInfoUIEvent.ShowGroupsModal(
                        show = false
                    )
                )
            },
            onGroupSelected = {},
            onNavigateHomeSelected = {
                GroupInfoUIEvent.ShowGroupsModal(
                    show = false
                )
                navController.navigateWithPopUpTo(
                    route = Screens.MyGroupsScreen.route,
                    popUpTo = Screens.GroupInfoScreen.route,
                    inclusive = true
                )
            }
        )
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        topBar = {
            GroupNavigationTopAppBar(
                currentGroup = commonState.userGroups.first { it.id == commonState.currentGroupId },
                onExpandClicked = {
                    viewModel.onEvent(
                        GroupInfoUIEvent.ShowGroupsModal(
                            show = true
                        )
                    )
                }
            )
        },
        bottomBar = {
            DSBottomNavigation(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GroupInfoContent(
                state = state,
                commonState = commonState,
                navigateTo = navController::navigate
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GroupInfoContent(
    state: GroupInfoUIState,
    commonState: GroupUIState,
    navigateTo: (String) -> Unit,
) {
    val currentGroup = commonState.userGroups.first { it.id == commonState.currentGroupId }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (bannerImage, image) = createRefs()
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .constrainAs(bannerImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = currentGroup.bannerImage.ifBlank { R.drawable.default_group_banner_image },
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            GlideImage(
                modifier = Modifier
                    .size(150.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape
                    )
                    .background(
                        SplitTheme.colors.neutral.backgroundMedium,
                        CircleShape
                    )
                    .clip(CircleShape)
                    .constrainAs(image) {
                        top.linkTo(bannerImage.bottom, (-78).dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                model = currentGroup.image.ifBlank { R.drawable.default_group_banner_image },
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp),
            text = currentGroup.name,
            style = SplitTheme.typography.heading.l,
            color = SplitTheme.colors.neutral.textTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp),
            text = currentGroup.description.ifBlank {
                stringResource(
                    id = R.string.no_group_description_text
                )
            },
            style = SplitTheme.typography.body.l,
            textAlign = TextAlign.Center,
            color = if (currentGroup.description.isEmpty()) {
                SplitTheme.colors.neutral.textDisabled
            } else SplitTheme.colors.neutral.textBody,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.size(16.dp))
        PrimaryLargeButton(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            onClick = { /*TODO*/ },
            text = R.string.edit_group_info,
            enabled = false
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.group_members),
            style = SplitTheme.typography.heading.m,
            color = SplitTheme.colors.neutral.textTitle
        )
        Spacer(modifier = Modifier.size(16.dp))
        MembersHorizontalList(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            membersList = currentGroup.members
        )
        Spacer(modifier = Modifier.size(16.dp))
        PrimaryLargeButton(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            onClick = {
                navigateTo(Screens.InviteCodeScreen.route)
            },
            text = R.string.see_invite_code
        )
        Spacer(modifier = Modifier.size(8.dp))
        SecondaryLargeButton(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            onClick = { /*TODO*/ },
            enabled = false,
            text = R.string.manage_members
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Preview
@Composable
fun GroupInfoScreenPreview() {
    GroupInfoScreen(
        navController = rememberNavController()
    )
}