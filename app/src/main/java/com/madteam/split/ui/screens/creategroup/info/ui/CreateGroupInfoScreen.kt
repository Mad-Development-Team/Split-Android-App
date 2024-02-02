package com.madteam.split.ui.screens.creategroup.info.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.madteam.split.R
import com.madteam.split.domain.model.Currency
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIEvent
import com.madteam.split.ui.screens.creategroup.info.state.CreateGroupInfoUIState
import com.madteam.split.ui.screens.creategroup.info.viewmodel.CreateGroupInfoViewModel
import com.madteam.split.ui.theme.CurrenciesDialog
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.ErrorDialog
import com.madteam.split.ui.theme.LoadingDialog
import com.madteam.split.ui.theme.PrimaryLargeButton
import com.madteam.split.ui.theme.SmallEmojiButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.getFlagByCurrency

@Composable
fun CreateGroupInfoScreen(
    navController: NavController,
    viewModel: CreateGroupInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateGroupInfoScreenContent(
                state = state,
                onGroupNameChanged = { name ->
                    viewModel.onEvent(CreateGroupInfoUIEvent.OnGroupNameChange(name))
                },
                onGroupDescriptionChanged = { description ->
                    viewModel.onEvent(CreateGroupInfoUIEvent.OnGroupDescriptionChange(description))
                },
                onNextClick = {
                    viewModel.onEvent(CreateGroupInfoUIEvent.OnNextClick)
                },
                onShowErrorDialogChanged = { showError ->
                    viewModel.onEvent(CreateGroupInfoUIEvent.OnShowError(showError))
                },
                onShowCurrenciesDialog = { showCurrencyDialog ->
                    viewModel.onEvent(
                        CreateGroupInfoUIEvent.OnShowCurrencyDialog(showCurrencyDialog)
                    )
                },
                onCurrencySelected = { currency ->
                    viewModel.onEvent(CreateGroupInfoUIEvent.OnCurrencySelected(currency))
                },
                navigateTo = navController::navigate,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun CreateGroupInfoScreenContent(
    state: CreateGroupInfoUIState,
    onGroupNameChanged: (String) -> Unit,
    onGroupDescriptionChanged: (String) -> Unit,
    onShowCurrenciesDialog: (Boolean) -> Unit,
    onShowErrorDialogChanged: (Boolean) -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    onNextClick: () -> Unit,
    navigateTo: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_group),
                style = SplitTheme.typography.display.m,
                color = SplitTheme.colors.neutral.textTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.create_group_description),
                style = SplitTheme.typography.body.l,
                color = SplitTheme.colors.neutral.textBody,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(24.dp))
            DSBasicTextField(
                value = state.groupName,
                onValueChange = {
                    onGroupNameChanged(it)
                },
                placeholder = R.string.enter_name_group,
                supportingText = if (!state.isGroupNameValid && state.groupName.isNotEmpty()) {
                    R.string.group_name_rules
                } else null,
                isError = !state.isGroupNameValid && state.groupName.isNotEmpty(),
                enabled = true,
            )
            DSBasicTextField(
                value = state.groupDescription,
                onValueChange = {
                    onGroupDescriptionChanged(it)
                },
                placeholder = R.string.enter_group_description,
                isError = !state.isGroupDescriptionValid && state.groupDescription.isNotEmpty(),
                supportingText = if (!state.isGroupDescriptionValid
                    && state.groupDescription.isNotEmpty()) {
                    R.string.group_description_max_characters
                } else null,
                enabled = true,
                maxLines = 5,
                imeAction = ImeAction.Done
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.select_a_currency),
                        style = SplitTheme.typography.heading.xs,
                        color = SplitTheme.colors.neutral.textTitle,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = stringResource(id = R.string.select_a_currency_description),
                        style = SplitTheme.typography.body.m,
                        color = SplitTheme.colors.neutral.textBody,
                        textAlign = TextAlign.Start,
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                SmallEmojiButton(
                    modifier = Modifier,
                    image = getFlagByCurrency(state.currencySelected),
                    onClick = {
                        onShowCurrenciesDialog(true)
                    },
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            PrimaryLargeButton(
                onClick = {
                    onNextClick()
                    navigateTo(Screens.CreateGroupMembersScreen.route)
                },
                text = R.string.continue_text,
                enabled = state.isGroupNameValid && state.isGroupDescriptionValid
            )
        }
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDialog()
        }
    }

    if (state.isError) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ErrorDialog(
                setShowDialog = {
                    onShowErrorDialogChanged(it)
                },
                onContinueClick = {
                    navigateBack()
                }
            )
        }
    }

    if (state.showCurrencyDialog) {
        CurrenciesDialog(
            currencies = state.currencies,
            onCurrencySelected = {
                onCurrencySelected(it)
            },
            onConfirmCurrency = {
                onShowCurrenciesDialog(false)
            },
            selectedCurrency = state.currencySelected,
            onDismiss = { onShowCurrenciesDialog(false) }
        )
    }
}