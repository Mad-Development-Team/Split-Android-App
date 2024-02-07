package com.madteam.split.ui.theme

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.utils.ui.getEmojiByName
import com.madteam.split.utils.ui.getFlagByCurrency
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun LoadingDialog() {
    ElevatedCard(
        modifier = Modifier
            .size(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
        )
    ) {
        BackHandler {
            //Do nothing on back press
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp),
                color = SplitTheme.colors.primary.backgroundMedium,
                trackColor = SplitTheme.colors.primary.backgroundWeak
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = stringResource(id = R.string.loading),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textStrong
            )
        }
    }
}

@Composable
fun ErrorDialog(
    setShowDialog: (Boolean) -> Unit,
    onContinueClick: () -> Unit = {},
    errorTitle: String? = stringResource(id = R.string.generic_error_title),
    errorText: String? = stringResource(id = R.string.generic_error_text),
    errorButton: Int? = R.string.ok,
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorTitle!!,
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = errorText!!,
                    style = SplitTheme.typography.body.l,
                    color = SplitTheme.colors.neutral.textStrong,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                PrimaryLargeButton(
                    onClick = {
                        setShowDialog(false)
                        onContinueClick()
                    },
                    text = errorButton!!
                )
            }
        }
    }
}

@Composable
fun AddMemberDialog(
    modifier: Modifier = Modifier,
    setShowDialog: (Boolean) -> Unit,
    onContinueClick: () -> Unit = {},
    newMemberName: String,
    onNewMemberNameChange: (String) -> Unit,
    isNameValid: Boolean,
    errorText: Int? = null,
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_member),
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                DSBasicTextField(
                    value = newMemberName,
                    onValueChange = {
                        onNewMemberNameChange(it)
                    },
                    placeholder = R.string.add_member,
                    isError = !isNameValid && newMemberName.isNotEmpty(),
                    isSuccess = isNameValid,
                    supportingText = if (!isNameValid && newMemberName.isNotEmpty()) errorText else null,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = Modifier.size(8.dp))
                DangerLargeButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    text = R.string.cancel
                )
                Spacer(modifier = Modifier.size(8.dp))
                PrimaryLargeButton(
                    onClick = {
                        setShowDialog(false)
                        onContinueClick()
                    },
                    text = R.string.add_member,
                    enabled = isNameValid
                )
            }
        }
    }
}

@Composable
fun DangerDialog(
    setShowDialog: (Boolean) -> Unit,
    title: Int,
    text: Int,
    cancelButtonText: Int,
    continueButtonText: Int,
    onContinueClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = title),
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = stringResource(id = text),
                    style = SplitTheme.typography.body.l,
                    color = SplitTheme.colors.neutral.textStrong,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                DangerLargeButton(
                    onClick = { onContinueClick() },
                    text = continueButtonText
                )
                Spacer(modifier = Modifier.size(16.dp))
                SecondaryLargeButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    text = cancelButtonText
                )

            }

        }

    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ChooseAvatarDialog(
    onChooseAvatar: (Int) -> Unit = {},
    onDismiss: () -> Unit = {},
    imagesList: List<String>,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        pageCount = { imagesList.size },
        initialPage = if (imagesList.isEmpty()) {
            0
        } else {
            Random.nextInt(imagesList.indices)
        }
    )

    LaunchedEffect(pagerState.currentPage) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (imagesList.isNotEmpty()) {
            val vibrationEffect =
                VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.choose_an_avatar),
                    style = SplitTheme.typography.heading.l,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = R.string.swipe_to_see_more),
                    style = SplitTheme.typography.body.m,
                    color = SplitTheme.colors.neutral.textTitle,
                    textAlign = TextAlign.Center
                )
            }
            if (imagesList.isNotEmpty()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                ) { page ->
                    val image = imagesList[page]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        GlideImage(
                            model = image,
                            contentDescription = null
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally),
                    color = SplitTheme.colors.primary.backgroundMedium,
                    trackColor = SplitTheme.colors.primary.backgroundWeak
                )
            }
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryLargeButton(
                    onClick = {
                        onChooseAvatar(pagerState.currentPage)
                    },
                    text = R.string.choose,
                    enabled = imagesList.isNotEmpty()
                )
                Spacer(modifier = Modifier.size(16.dp))
                SecondaryLargeButton(
                    onClick = {
                        onDismiss()
                    },
                    text = R.string.cancel
                )
            }
        }
    }
}

@Composable
fun CurrenciesDialog(
    currencies: List<Currency>,
    selectedCurrency: Currency? = null,
    onCurrencySelected: (Currency) -> Unit,
    onConfirmCurrency: () -> Unit,
    onDismiss: () -> Unit,
) {
    val sortedCurrencies = currencies.sortedByDescending { it.enabled }
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 4.dp)
                    .align(Alignment.CenterHorizontally),
                text = selectedCurrency?.name ?: stringResource(id = R.string.select_a_currency),
                style = SplitTheme.typography.heading.l,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SplitTheme.colors.neutral.textTitle
            )
            Text(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(
                    id = R.string.more_currencies_coming_soon
                ),
                style = SplitTheme.typography.body.s,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SplitTheme.colors.neutral.textMedium
            )
            LazyHorizontalGrid(
                modifier = Modifier
                    .height(150.dp),
                rows = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp),
                content = {
                    items(sortedCurrencies.size) { index ->
                        val currency = sortedCurrencies[index]
                        val flag = getFlagByCurrency(currency)
                        val backgroundColor = if (currency == selectedCurrency) {
                            SplitTheme.colors.primary.backgroundMedium
                        } else if (!currency.enabled) {
                            SplitTheme.colors.neutral.backgroundMedium
                        } else {
                            SplitTheme.colors.secondary.backgroundMedium
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape
                                )
                                .clickable {
                                    if (currency.enabled) {
                                        onCurrencySelected(currency)
                                    }
                                }
                                .clip(CircleShape)
                                .background(
                                    color = backgroundColor,
                                    shape = CircleShape
                                )

                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(Alignment.Center),
                                painter = painterResource(id = flag),
                                contentDescription = null
                            )
                            if (!currency.enabled) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = SplitTheme.colors.neutral.backgroundMedium.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                )
                            }
                        }
                    }
                }
            )
            PrimaryLargeButton(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                onClick = { onConfirmCurrency() },
                text = R.string.select,
                enabled = selectedCurrency?.enabled ?: false
            )
        }

    }
}

@Composable
fun ExpenseTypeDialog(
    expensesList: List<ExpenseType>,
    onDismiss: () -> Unit,
    onExpenseTypeSelected: (ExpenseType) -> Unit,
    onExpenseTypeCreated: (ExpenseType) -> Unit,
    selectedExpenseType: ExpenseType,
    groupId: Int,
) {
    var isOnCreateTypeMode by remember {
        mutableStateOf(false)
    }
    if (isOnCreateTypeMode) {
        CreateExpenseTypeDialog(
            onDismiss = { isOnCreateTypeMode = false },
            onExpenseTypeCreated = {
                onExpenseTypeCreated(it)
                isOnCreateTypeMode = false
            },
            groupId = groupId
        )
    } else {
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 4.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.select_a_category),
                    style = SplitTheme.typography.heading.l,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = SplitTheme.colors.neutral.textTitle
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = SplitTheme.colors.neutral.backgroundMedium,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = stringResource(id = R.string.default_categories),
                                style = SplitTheme.typography.body.m,
                                color = SplitTheme.colors.neutral.textTitle
                            )
                        }
                    }
                    itemsIndexed(expensesList.filter { it.group == null }) { _, expenseType ->
                        val translatableTextForDefaultCategories = when (expenseType.title) {
                            "Food" -> R.string.food
                            "Accommodation" -> R.string.accommodation
                            "Transport" -> R.string.transport
                            "Entertainment" -> R.string.entertainment
                            "Groceries" -> R.string.groceries
                            "Health" -> R.string.health
                            "Restaurants" -> R.string.restaurants
                            "Shopping" -> R.string.shopping
                            "Travel" -> R.string.travel
                            "Fuel" -> R.string.fuel
                            "Bars" -> R.string.bars
                            "Other" -> R.string.other
                            else -> R.string.default_categories
                        }
                        val isSelected = selectedExpenseType == expenseType
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(
                                    color = if (isSelected) {
                                        SplitTheme.colors.primary.backgroundWeak
                                    } else {
                                        Color.Transparent
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onExpenseTypeSelected(expenseType)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = SplitTheme.colors.primary.backgroundWeak,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(
                                        id = getEmojiByName(expenseType.icon)
                                    ),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = stringResource(id = translatableTextForDefaultCategories),
                                style = SplitTheme.typography.heading.s,
                                color = SplitTheme.colors.neutral.textTitle
                            )
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = SplitTheme.colors.neutral.backgroundMedium,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = stringResource(id = R.string.custom_group_categories),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = SplitTheme.typography.body.m,
                                color = SplitTheme.colors.neutral.textTitle
                            )
                        }
                    }
                    itemsIndexed(expensesList.filter { it.group == groupId }) { _, expenseType ->
                        val isSelected = selectedExpenseType == expenseType
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .background(
                                    color = if (isSelected) {
                                        SplitTheme.colors.primary.backgroundWeak
                                    } else {
                                        Color.Transparent
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onExpenseTypeSelected(expenseType)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        color = SplitTheme.colors.primary.backgroundWeak,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(
                                        id = getEmojiByName(expenseType.icon)
                                    ),
                                    contentDescription = null
                                )
                            }
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = expenseType.title,
                                style = SplitTheme.typography.heading.s,
                                color = SplitTheme.colors.neutral.textTitle
                            )
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            SecondaryLargeButton(
                                onClick = {
                                    isOnCreateTypeMode = true
                                },
                                text = R.string.create_group_category
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateExpenseTypeDialog(
    onDismiss: () -> Unit,
    onExpenseTypeCreated: (ExpenseType) -> Unit,
    groupId: Int,
) {
    var emojiSelected by remember {
        mutableStateOf(getEmojiByName("questionmark"))
    }
    var showEmojiPicker by remember {
        mutableStateOf(false)
    }
    var expenseTypeName by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
            )
        ) {
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.create_group_category),
                style = SplitTheme.typography.heading.l,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SplitTheme.colors.neutral.textTitle
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallEmojiButton(
                    image = emojiSelected,
                    onClick = {
                        showEmojiPicker = true
                    }
                )
                Spacer(modifier = Modifier.size(8.dp))
                DSBasicTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = expenseTypeName,
                    onValueChange = { expenseTypeName = it },
                    placeholder = R.string.category_name,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            PrimaryLargeButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                onClick = {
                    onExpenseTypeCreated(
                        ExpenseType(
                            id = 0,
                            title = expenseTypeName,
                            icon = emojiSelected.toString(),
                            group = groupId
                        )
                    )
                },
                text = R.string.create_group_category
            )
            Spacer(modifier = Modifier.size(8.dp))
            SecondaryLargeButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                onClick = { onDismiss() },
                text = R.string.go_back_categories
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
    if (showEmojiPicker) {
        EmojiPickerDialog(
            onEmojiSelected = {
                emojiSelected = getEmojiByName(it)
                showEmojiPicker = false
            },
            onDismissRequest = {
                showEmojiPicker = false
            }
        )
    }
}

@Preview
@Composable
fun CreateExpenseTypeDialogPreview() {
    CreateExpenseTypeDialog(
        onDismiss = {},
        onExpenseTypeCreated = {},
        groupId = 1
    )
}

@Preview
@Composable
fun ExpenseTypeDialogPreview() {
    ExpenseTypeDialog(
        expensesList = listOf(
            ExpenseType(1, "Food", "hamburger"),
            ExpenseType(1, "Accommodation", "housewithgarden"),
            ExpenseType(2, "Transport", "trolleybus"),
            ExpenseType(3, "Entertainment", "joystick"),
            ExpenseType(4, "Groceries", "shoppingcart"),
            ExpenseType(5, "Health", "rescueworkershelmet"),
            ExpenseType(6, "Restaurants", "forkandknifewithplate"),
            ExpenseType(7, "Shopping", "shoppingbags"),
            ExpenseType(8, "Travel", "airplane"),
            ExpenseType(9, "Fuel", "fuelpump"),
            ExpenseType(10, "Bars", "clinkingbeermugs"),
            ExpenseType(9, "Other", "questionmark"),
        ),
        onDismiss = {},
        onExpenseTypeSelected = {},
        groupId = 1,
        onExpenseTypeCreated = {},
        selectedExpenseType = ExpenseType(1, "Food", "hamburger")
    )
}

@Preview
@Composable
fun CurrenciesDialogPreview() {
    var selectedCurrency: Currency? by remember {
        mutableStateOf(null)
    }
    CurrenciesDialog(
        currencies = listOf(
            Currency("USD", "United States Dollar", "$"),
            Currency("EUR", "Euro", "€"),
            Currency("GBP", "British Pound", "£"),
            Currency("CHF", "Swiss", "¥"),
            Currency("SEK", "Swedish Krona", "kr"),
            Currency("NOK", "Norwegian Krone", "kr"),
            Currency("DKK", "Danish Krone", "kr"),
            Currency("PLN", "Polish Zloty", "zł"),
            Currency("CAD", "Canadian Dollar", "$"),
            Currency("MXN", "Mexican Peso", "$"),
            Currency("BRL", "Brazilian Real", "R$"),
            Currency("ARS", "Argentine Peso", "$"),
            Currency("COP", "Colombian Peso", "$"),
            Currency("PEN", "Peruvian Sol", "S/")
        ),
        selectedCurrency = selectedCurrency,
        onCurrencySelected = {
            selectedCurrency = if (it == selectedCurrency) {
                null
            } else {
                it
            }
        },
        onConfirmCurrency = {},
        onDismiss = {}
    )
}

@Preview
@Composable
fun DangerDialogPreview() {
    DangerDialog(
        setShowDialog = {},
        title = R.string.is_it_a_goodbye,
        text = R.string.log_out_confirm_text,
        cancelButtonText = R.string.cancel,
        continueButtonText = R.string.continue_log_out,
        onContinueClick = {}
    )
}

@Preview
@Composable
fun LoadingDialogPreview() {
    LoadingDialog()
}

@Preview
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(setShowDialog = {})
}

@Preview
@Composable
fun AddMemberDialogPreview() {
    AddMemberDialog(
        setShowDialog = {},
        onContinueClick = {},
        newMemberName = "",
        onNewMemberNameChange = {},
        isNameValid = false,
        errorText = R.string.generic_error_text
    )
}