package com.madteam.split.ui.screens.mygroups.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.Group
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.User
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.mygroups.state.MyGroupsUIState
import com.madteam.split.ui.screens.mygroups.viewmodel.MyGroupsViewModel
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler

@Composable
fun MyGroupsScreen(
    navController: NavController,
    viewModel: MyGroupsViewModel = hiltViewModel(),
) {

    BackPressHandler {
        //Do nothing on back press
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

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
                navigateTo = navController::navigate
            )
        }
    }
}

@Composable
fun MyGroupsContent(
    state: MyGroupsUIState,
    navigateTo: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        MyGroupsTopBar(
            userInfo = state.userInfo,
            navigateTo = navigateTo
        )
        Spacer(modifier = Modifier.size(24.dp))
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
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .size(124.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.secondary.backgroundMedium
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (backgroundImage, degraded, groupName, members) = createRefs()
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
        }

    }
}

@Preview
@Composable
fun GroupListItemPreview() {
    GroupListItem(
        group = Group(
            id = 10,
            name = "Amsterdam",
            description = "",
            inviteCode = "R2PZMT",
            image = "",
            bannerImage = "",
            createdDate = "2024-01-21 18:28:42",
            members = listOf(
                Member(
                    id = 21,
                    name = "adria",
                    profileImage = "",
                    user = 5,
                    color = "",
                    joinedDate = "2024-01-21 18:28:42",
                    groupId = 10
                ),
                Member(
                    id = 22,
                    name = "david",
                    profileImage = "",
                    user = null,
                    color = "",
                    joinedDate = "2024-01-21 18:28:42",
                    groupId = 10
                ),
                Member(
                    id = 23,
                    name = "Berni",
                    profileImage = "",
                    user = null,
                    color = "",
                    joinedDate = "2024-01-21 18:28:42",
                    groupId = 10
                ),
                Member(
                    id = 24,
                    name = "Oscar",
                    profileImage = "",
                    user = null,
                    color = "",
                    joinedDate = "2024-01-21 18:28:42",
                    groupId = 10
                ),
            )
        )
    )
}