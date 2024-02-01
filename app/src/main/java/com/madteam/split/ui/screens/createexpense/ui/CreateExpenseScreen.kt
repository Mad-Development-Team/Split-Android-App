package com.madteam.split.ui.screens.createexpense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.theme.BigIconButton
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSDateTextField
import com.madteam.split.ui.theme.DSNumericTextField
import com.madteam.split.ui.theme.DefaultFloatingButton
import com.madteam.split.ui.theme.MembersHorizontalList
import com.madteam.split.ui.theme.SmallEmojiButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.BackPressHandler
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun CreateExpenseScreen(
    navController: NavController,
) {
    BackPressHandler {
        //Do nothing on back press
    }

    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
        floatingActionButton = {
            DefaultFloatingButton(
                icon = Icons.Outlined.Done,
                onClick = {}
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            CreateExpenseContent(
                popUpBack = {
                    navController.navigateWithPopUpTo(
                        route = Screens.GroupExpensesScreen.route,
                        popUpTo = Screens.CreateExpenseScreen.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}

@Composable
fun CreateExpenseContent(
    popUpBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { popUpBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = SplitTheme.colors.neutral.iconHeavy,
                    contentDescription = stringResource(id = R.string.icon_back_description)
                )
            }
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = stringResource(id = R.string.add_expense),
                style = SplitTheme.typography.heading.m,
                color = SplitTheme.colors.neutral.textTitle,
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DSBasicTextField(
                modifier = Modifier.weight(1f),
                value = "",
                onValueChange = {},
                placeholder = R.string.expense_title
            )
            Spacer(modifier = Modifier.size(8.dp))
            SmallEmojiButton(
                onClick = {},
                image = R.drawable.emoji_hamburguer
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSBasicTextField(
                value = "",
                onValueChange = {},
                placeholder = R.string.expense_description,
                maxLines = 3
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DSNumericTextField(
                modifier = Modifier.weight(1f),
                value = "",
                onValueChange = {},
                placeholder = R.string.expense_amount,
            )
            Spacer(modifier = Modifier.size(8.dp))
            SmallEmojiButton(
                onClick = {},
                image = R.drawable.emoji_euro_bill
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            DSDateTextField(
                value = "",
                onValueChange = {},
                placeholder = R.string.expense_date
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.add_images),
            style = SplitTheme.typography.heading.m,
        )
        BigIconButton(
            modifier = Modifier
                .padding(start = 24.dp, top = 8.dp),
            icon = Icons.Outlined.Add,
            enabled = true,
            onClick = {}
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.paid_by),
            style = SplitTheme.typography.heading.m,
        )
        Spacer(modifier = Modifier.size(16.dp))
        MembersHorizontalList(
            membersList = listOf(
                Member(
                    id = 0,
                    name = "David",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Oscar",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Bernat",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Adri",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = stringResource(id = R.string.for_whom),
            style = SplitTheme.typography.heading.m,
        )
        Spacer(modifier = Modifier.size(16.dp))
        MembersHorizontalList(
            membersList = listOf(
                Member(
                    id = 0,
                    name = "David",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Oscar",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Bernat",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
                Member(
                    id = 0,
                    name = "Adri",
                    profileImage = "",
                    color = null,
                    groupId = 0,
                    joinedDate = "",
                    user = 0
                ),
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview
@Composable
fun CreateExpenseScreenPreview() {
    CreateExpenseScreen(
        navController = rememberNavController()
    )
}