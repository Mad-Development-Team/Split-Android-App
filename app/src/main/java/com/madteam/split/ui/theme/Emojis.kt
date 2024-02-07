package com.madteam.split.ui.theme

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.madteam.split.R
import com.madteam.split.utils.ui.getEmojiByName
import com.madteam.split.utils.ui.getEmojisByType

data class EmojiType(
    @StringRes val name: Int,
    val emoji: String,
)

private val emojiTypes: List<EmojiType> = listOf(
    EmojiType(R.string.emojis_animals_and_nature, "butterfly"),
    EmojiType(R.string.emojis_food_and_drink, "hamburger"),
    EmojiType(R.string.emojis_activities, "soccer"),
    EmojiType(R.string.emojis_travel_and_places, "airplane"),
    EmojiType(R.string.emojis_objects, "hammer"),
    EmojiType(R.string.emojis_symbols, "heart"),
    EmojiType(R.string.emojis_flags, "melilla"),
)

@Composable
fun EmojiPickerDialog(
    onDismissRequest: () -> Unit,
    onEmojiSelected: (String) -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        val resources = LocalContext.current.resources
        var sectionSelected by remember { mutableStateOf(0) }
        val animalsAndNatureEmojis = getEmojisByType("animals_nature")
        val foodAndDrinkEmojis = getEmojisByType("food_drink")
        val activitiesEmojis = getEmojisByType("activity")
        val travelAndPlacesEmojis = getEmojisByType("travel_places")
        val objectsEmojis = getEmojisByType("objects")
        val symbolsEmojis = getEmojisByType("symbols")
        val flagsEmojis = getEmojisByType("flags")

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = SplitTheme.colors.neutral.backgroundMedium,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    itemsIndexed(emojiTypes) { index, emojiType ->
                        val isSelected = sectionSelected == index
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    sectionSelected = index
                                }
                                .background(
                                    color = if (isSelected) {
                                        SplitTheme.colors.primary.backgroundWeak
                                    } else SplitTheme.colors.neutral.backgroundMedium,
                                    shape = CircleShape
                                )
                                .clip(shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(
                                    id = getEmojiByName(emojiType.emoji)
                                ),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            LazyHorizontalGrid(
                rows = GridCells.Adaptive(28.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                val emojiListSelected = when (sectionSelected) {
                    0 -> animalsAndNatureEmojis
                    1 -> foodAndDrinkEmojis
                    2 -> activitiesEmojis
                    3 -> travelAndPlacesEmojis
                    4 -> objectsEmojis
                    5 -> symbolsEmojis
                    6 -> flagsEmojis
                    else -> animalsAndNatureEmojis
                }
                items(emojiListSelected.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(shape = CircleShape)
                                .clickable {
                                    onEmojiSelected(
                                        resources.getResourceEntryName(emojiListSelected[index])
                                    )
                                    onDismissRequest()
                                },
                            painter = painterResource(
                                id = emojiListSelected[index]
                            ),
                            contentDescription = null
                        )
                    }
                }

            }

        }
    }
}

@Preview
@Composable
fun EmojiPickerPreview() {
    EmojiPickerDialog(
        onDismissRequest = {},
        onEmojiSelected = {}
    )
}