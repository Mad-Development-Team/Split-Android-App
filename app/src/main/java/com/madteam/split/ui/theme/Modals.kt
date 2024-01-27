package com.madteam.split.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.Group

data class ModalOption(
    val icon: ImageVector,
    val iconDescription: Int,
    val title: Int,
    val action: () -> Unit,
    val isDangerOption: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSModalBottomSheet(
    optionsList: List<ModalOption>,
    onClose: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    BackHandler {
        onClose()
    }

    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            itemsIndexed(optionsList) { _, option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            option.action()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = stringResource(id = option.iconDescription),
                        tint = if (option.isDangerOption) {
                            SplitTheme.colors.error.iconDefault
                        } else SplitTheme.colors.neutral.iconHeavy,
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = stringResource(id = option.title),
                        style = SplitTheme.typography.body.l,
                        color = if (option.isDangerOption) {
                            SplitTheme.colors.error.textDefault
                        } else SplitTheme.colors.neutral.textTitle,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.size(36.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun GroupSettingsModalBottomSheet(
    group: Group,
    isDefault: Boolean,
    onClose: () -> Unit,
    onGroupDefault: (Int) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    BackHandler {
        onClose()
    }

    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = modalBottomSheetState,
        dragHandle = {},
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    ) {
        ConstraintLayout(
            modifier = Modifier
        ) {
            val (bannerImage, degrade, groupName) = createRefs()
            if (group.bannerImage.isNotEmpty()) {
                GlideImage(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(100.dp)
                        .constrainAs(bannerImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentScale = ContentScale.Crop,
                    model = group.bannerImage,
                    contentDescription = stringResource(id = R.string.group_banner_image)
                )
            } else {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .rotate(180f)
                        .constrainAs(bannerImage) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.default_group_banner_image),
                    contentDescription = stringResource(id = R.string.group_banner_image),
                )
            }
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
                    .constrainAs(degrade) {
                        top.linkTo(bannerImage.top)
                        start.linkTo(bannerImage.start)
                        end.linkTo(bannerImage.end)
                        bottom.linkTo(bannerImage.bottom)
                    }
            )
            Text(
                modifier = Modifier
                    .constrainAs(groupName) {
                        bottom.linkTo(bannerImage.bottom, 16.dp)
                        start.linkTo(parent.start, 24.dp)
                    },
                text = group.name,
                style = SplitTheme.typography.heading.s,
                color = SplitTheme.colors.neutral.textExtraWeak,
            )

        }
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onGroupDefault(group.id)
                    }
            ) {
                Icon(
                    imageVector = if (isDefault) {
                        Icons.Filled.RemoveCircle
                    } else {
                        Icons.Filled.Home
                    },
                    contentDescription = null,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = if (isDefault) {
                        stringResource(
                            id = R.string.remove_this_group_as_default
                        ) // It will be different when logic implemented
                    } else {
                        stringResource(id = R.string.mark_this_group_as_default)
                    },
                    style = SplitTheme.typography.body.l,
                    color = SplitTheme.colors.neutral.textTitle,
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun GroupsListModalBottomSheet(
    groupsList: List<Group>,
    currentGroupId: Int,
    onClose: () -> Unit,
    onGroupSelected: (Int) -> Unit,
    onNavigateHomeSelected: () -> Unit,
) {

    val modalBottomSheetState = rememberModalBottomSheetState()
    BackHandler {
        onClose()
    }

    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = modalBottomSheetState,
        dragHandle = {},
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            groupsList.forEach { group ->
                val isCurrentGroup = group.id == currentGroupId
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onGroupSelected(group.id)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = SplitTheme.colors.neutral.backgroundExtraWeak,
                                shape = CircleShape
                            ),
                        model = group.image.ifBlank { R.drawable.default_group_banner_image },
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = group.name,
                        style = SplitTheme.typography.heading.s,
                        color = SplitTheme.colors.neutral.textExtraWeak,
                    )
                    if (isCurrentGroup) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = stringResource(id = R.string.group_selected)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            PrimaryLargeButton(
                onClick = {
                    onNavigateHomeSelected()
                },
                text = R.string.go_to_my_groups
            )
        }

    }
}