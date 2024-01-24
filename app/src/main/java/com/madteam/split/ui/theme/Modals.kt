package com.madteam.split.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    onClose: () -> Unit,
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
                .fillMaxSize()
        ) {
            val (bannerImage) = createRefs()
            if (group.bannerImage.isNotEmpty()) {
                GlideImage(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(60.dp)
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
        }
    }
}

@Preview
@Composable
fun DSModalBottomSheetPreview() {
    GroupSettingsModalBottomSheet(
        group = Group(
            id = 1,
            name = "Group Name",
            members = listOf(),
            description = "",
            image = "",
            inviteCode = "",
            bannerImage = "",
            createdDate = "23/11/2001"
        ),
        {}
    )
}