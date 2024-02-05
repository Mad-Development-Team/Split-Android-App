package com.madteam.split.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.Currency
import com.madteam.split.domain.model.Member

@Composable
fun AddMembersHorizontalList(
    modifier: Modifier = Modifier,
    memberList: List<Member>,
    memberSelected: Member? = null,
    onDeleteSelectedMember: () -> Unit = {},
    onMemberSelected: (Member) -> Unit = {},
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(memberList) { _, member ->
            val memberIsSelected = memberSelected != null && memberSelected.name == member.name
            ConstraintLayout {
                val (blob, name, deleteIcon) = createRefs()
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                onMemberSelected(member)
                            }
                        )
                        .constrainAs(blob) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    painter = painterResource(id = R.drawable.blob),
                    colorFilter = ColorFilter.tint(
                        if (memberIsSelected) {
                            SplitTheme.colors.error.backgroundDefault
                        } else {
                            SplitTheme.colors.primary.backgroundMedium
                        }
                    ),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(blob.start)
                            end.linkTo(blob.end)
                            bottom.linkTo(blob.bottom, 40.dp)
                        },
                    text = member.name,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = SplitTheme.typography.heading.m
                )
                if (memberIsSelected) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable(
                                onClick = {
                                    onDeleteSelectedMember()
                                }
                            )
                            .constrainAs(deleteIcon) {
                                top.linkTo(blob.top, 36.dp)
                                end.linkTo(blob.end)
                                start.linkTo(blob.start)
                            },
                        imageVector = Icons.Outlined.Delete,
                        tint = SplitTheme.colors.neutral.iconExtraWeak,
                        contentDescription = stringResource(id = R.string.delete_member)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MembersHorizontalList(
    modifier: Modifier = Modifier,
    membersList: List<Member>,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.size(8.dp))
        }
        itemsIndexed(membersList) { _, member ->
            val hexColor = member.color?.removePrefix("0x")?.toLong(16) ?: 0xFF000000
            val color = Color(hexColor)
            ConstraintLayout(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape
                    )
            ) {
                val (image, name, degrade) = createRefs()
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
                )
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
            }
        }
        item {
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MemberWithAmount(
    member: Member,
    isMemberHighLighted: Boolean,
    amount: Double? = null,
    currency: Currency,
    onMemberClick: (Member) -> Unit,
) {
    val hexColor = member.color?.removePrefix("0x")?.toLong(16) ?: 0xFF000000
    val gradientModifier = when (isMemberHighLighted) {
        false -> Modifier
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

        true -> Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(
                color = SplitTheme.colors.primary.backgroundStrong.copy(
                    alpha = 0.9f
                )
            )
    }

    ConstraintLayout(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = CircleShape
            )
    ) {
        val (image, name, highlighted, amountPaid) = createRefs()
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
                    Color(hexColor),
                    CircleShape
                )
                .clip(CircleShape)
                .clickable {
                    onMemberClick(member)
                }
        )
        Box(
            modifier = gradientModifier
                .constrainAs(highlighted) {
                    top.linkTo(image.top)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                }
        )
        if (amount != null) {
            Row(
                modifier = Modifier
                    .constrainAs(amountPaid) {
                        top.linkTo(parent.top, 16.dp)
                        bottom.linkTo(name.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                AmountTextView(
                    amount = amount,
                    currency = currency
                )
            }
        }
        Text(
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                    bottom.linkTo(image.bottom, 30.dp)
                },
            text = member.name,
            color = SplitTheme.colors.neutral.textExtraWeak,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = SplitTheme.typography.heading.m
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddMembersHorizontalListPreview() {
    AddMembersHorizontalList(
        memberList = listOf(
            Member(
                id = 1,
                name = "John Doe",
                profileImage = null,
                user = null,
                color = "f4a73d",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
            Member(
                id = 2,
                name = "John Doe",
                profileImage = null,
                user = null,
                color = "2656a6",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
            Member(
                id = 3,
                name = "John Doe",
                profileImage = null,
                user = null,
                color = "60aa3e",
                joinedDate = "2021-09-01",
                groupId = 1
            ),
        )
    )
}