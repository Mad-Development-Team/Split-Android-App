package com.madteam.split.ui.screens.createexpense.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.Member
import com.madteam.split.ui.theme.MemberWithAmount
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun PaidByMembersList(
    modifier: Modifier = Modifier,
    membersList: List<Member>,
    expense: Expense,
    onMemberClick: (Member) -> Unit,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(membersList) { _, member ->
            MemberWithAmount(
                member = member,
                isMemberHighLighted = expense.paidBy.any { it.memberId == member.id },
                amount = expense.paidBy.firstOrNull { it.memberId == member.id }?.paidAmount,
                onMemberClick = { onMemberClick(it) }
            )
        }
    }
}

@Composable
fun ForWhomMembersList(
    modifier: Modifier = Modifier,
    membersList: List<Member>,
    expense: Expense,
    onMemberClick: (Member) -> Unit,
    onForAllClick: () -> Unit,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            val backgroundColor = if (expense.forWhom.size == membersList.size) {
                SplitTheme.colors.primary.backgroundStrong
            } else {
                SplitTheme.colors.neutral.backgroundMedium
            }
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        backgroundColor,
                        CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {
                        onForAllClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Groups,
                        contentDescription = null,
                        tint = SplitTheme.colors.neutral.iconExtraWeak,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = stringResource(id = R.string.for_all),
                        color = SplitTheme.colors.neutral.textExtraWeak,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = SplitTheme.typography.heading.m
                    )
                }
            }
        }
        itemsIndexed(membersList) { _, member ->
            MemberWithAmount(
                member = member,
                isMemberHighLighted = expense.forWhom.any { it.memberId == member.id },
                amount = expense.forWhom.firstOrNull { it.memberId == member.id }?.amount,
                onMemberClick = { onMemberClick(it) }
            )
        }
    }
}
