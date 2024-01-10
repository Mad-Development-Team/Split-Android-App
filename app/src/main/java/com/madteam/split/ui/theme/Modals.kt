package com.madteam.split.ui.theme

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R

data class ModalOption(
    val icon: ImageVector,
    val iconDescription: Int,
    val title: Int,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSModalBottomSheet(
    optionsList: List<ModalOption>,
    onClose: () -> Unit
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
                        tint = SplitTheme.colors.neutral.iconHeavy
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = stringResource(id = option.title),
                        style = SplitTheme.typography.body.l,
                        color = SplitTheme.colors.neutral.textTitle
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.size(36.dp))
            }
        }
    }
}

@Preview
@Composable
fun DSModalBottomSheetPreview() {
    DSModalBottomSheet(
        onClose = {},
        optionsList = mutableListOf(
            ModalOption(
                iconDescription = R.string.edit_icon_description,
                icon = Icons.Outlined.Image,
                title = R.string.update_profile_image_from_device,
                action = {}
            ),
            ModalOption(
                iconDescription = R.string.edit_icon_description,
                icon = Icons.Outlined.Delete,
                title = R.string.delete_profile_image,
                action = {}
            ),
            ModalOption(
                iconDescription = R.string.edit_icon_description,
                icon = Icons.Outlined.EmojiEmotions,
                title = R.string.choose_an_avatar,
                action = {}
            )
        )
    )
}