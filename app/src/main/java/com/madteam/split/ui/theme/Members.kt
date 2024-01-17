package com.madteam.split.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.madteam.split.R
import com.madteam.split.domain.model.Member

@Composable
fun AddMembersHorizontalList(
    modifier: Modifier = Modifier,
    memberList: List<Member>,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(memberList) { _, member ->
            ConstraintLayout {
                val (blob, name) = createRefs()
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .constrainAs(blob) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    painter = painterResource(id = R.drawable.blob),
                    colorFilter = ColorFilter.tint(SplitTheme.colors.primary.backgroundMedium),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .constrainAs(name) {
                            start.linkTo(blob.start)
                            end.linkTo(blob.end)
                            bottom.linkTo(blob.bottom, 36.dp)
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