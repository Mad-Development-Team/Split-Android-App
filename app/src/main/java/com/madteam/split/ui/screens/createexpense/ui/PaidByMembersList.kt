package com.madteam.split.ui.screens.createexpense.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.theme.MemberWithAmount

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

@Preview
@Composable
fun PaidByMembersHorizontalListPreview() {
    PaidByMembersList(
        membersList = listOf(
            Member(
                id = 1,
                name = "Adri",
                profileImage = null,
                user = null,
                color = "0xFF55FD01",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
            Member(
                id = 2,
                name = "David",
                profileImage = null,
                user = null,
                color = "0xFF8EE189",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
            Member(
                id = 3,
                name = "Oscar",
                profileImage = null,
                user = null,
                color = "0xFFF91439",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
            Member(
                id = 3,
                name = "Berni",
                profileImage = null,
                user = null,
                color = "0xFFE9A22B",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
        ),
        expense = Expense(
            id = 1,
            title = "Cena",
            description = "Cena de cumpleaños",
            totalAmount = 25.50,
            type = ExpenseType(),
            paidBy = listOf(
                PaidByExpense(
                    memberId = 1,
                    paidAmount = 25.35
                )
            ),
            images = emptyList(),
            paymentMethod = null,
            date = "2021-09-01",
            group = 1
        ),
        onMemberClick = {}
    )
}