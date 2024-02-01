package com.madteam.split.ui.screens.createexpense.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.Expense
import com.madteam.split.domain.model.ExpenseType
import com.madteam.split.domain.model.Member
import com.madteam.split.domain.model.PaidByExpense
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.ui.utils.round
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
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
            val hexColor = member.color?.removePrefix("0x")?.toLong(16) ?: 0xFF000000
            val color = Color(hexColor)
            val memberIsHighlighted = expense.paidBy.any { it.memberId == member.id }
            val amountMemberPaid =
                expense.paidBy.firstOrNull { it.memberId == member.id }?.paidAmount?.round(2) ?: 0.0
            val decimalPart = amountMemberPaid.toString().split(".")[1]
            ConstraintLayout(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape
                    )
            ) {
                val (image, name, degrade, highlighted, amountPaid) = createRefs()
                GlideImage(
                    model = member.profileImage,
                    contentDescription = stringResource(
                        id = R.string.user_profile_image_description
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .size(120.dp)
                        .background(
                            color,
                            CircleShape
                        )
                        .clip(CircleShape)
                        .clickable {
                            onMemberClick(member)
                        }
                )
                if (memberIsHighlighted) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                color = SplitTheme.colors.primary.backgroundStrong.copy(
                                    alpha = 0.9f
                                )
                            )
                            .constrainAs(highlighted) {
                                top.linkTo(image.top)
                                start.linkTo(image.start)
                                end.linkTo(image.end)
                                bottom.linkTo(image.bottom)
                            }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    )
                                )
                            )
                            .constrainAs(degrade) {
                                top.linkTo(image.top)
                                start.linkTo(image.start)
                                end.linkTo(image.end)
                                bottom.linkTo(image.bottom)
                            }
                    )
                }
                Text(
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(image.start)
                            end.linkTo(image.end)
                            bottom.linkTo(image.bottom, 30.dp)
                        },
                    text = member.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = SplitTheme.typography.heading.m
                )
                if (memberIsHighlighted) {
                    Row(
                        modifier = Modifier
                            .constrainAs(amountPaid) {
                                top.linkTo(parent.top, 16.dp)
                                bottom.linkTo(name.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        AnimatedContent(
                            targetState = amountMemberPaid.toInt(),
                            transitionSpec = {
                                if (targetState > initialState) {
                                    slideInVertically { -it } togetherWith slideOutVertically { it }
                                } else {
                                    slideInVertically { it } togetherWith slideOutVertically { -it }
                                }
                            }, label = ""
                        ) { animatedAmountValue ->
                            Text(
                                modifier = Modifier,
                                text = animatedAmountValue.absoluteValue.toString(),
                                color = SplitTheme.colors.neutral.textExtraWeak,
                                style = SplitTheme.typography.display.s
                            )
                        }
                        AnimatedContent(
                            targetState = decimalPart.toInt(),
                            transitionSpec = {
                                if (targetState > initialState) {
                                    slideInVertically { -it } togetherWith slideOutVertically { it }
                                } else {
                                    slideInVertically { it } togetherWith slideOutVertically { -it }
                                }
                            }, label = ""
                        ) { animatedDecimalValue ->
                            Text(
                                modifier = Modifier
                                    .padding(top = 4.dp),
                                text = if (decimalPart == "0" || decimalPart == "00") ""
                                else animatedDecimalValue.toString(),
                                color = SplitTheme.colors.neutral.textExtraWeak,
                                style = SplitTheme.typography.heading.s
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(top = 4.dp),
                            text = "€", //TODO: Hardcoded with euro
                            color = SplitTheme.colors.neutral.textExtraWeak,
                            style = SplitTheme.typography.heading.s
                        )
                    }
                }
            }
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