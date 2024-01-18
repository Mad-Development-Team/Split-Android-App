package com.madteam.split.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R
import com.madteam.split.domain.model.User

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    userInfo: User,
    size: Int,
    onClick: () -> Unit? = {},
    isClickable: Boolean = false,
) {
    val textStyle = if (size >= 100) {
        SplitTheme.typography.display.l
    } else {
        SplitTheme.typography.display.m
    }
    Column(
        modifier = modifier
    ) {
        ConstraintLayout {
            val (profileImage, text) = createRefs()
            GlideImage(
                model = userInfo.profileImage,
                contentDescription = stringResource(id = R.string.user_profile_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size.dp)
                    .background(
                        SplitTheme.colors.neutral.backgroundMedium,
                        CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(
                        enabled = isClickable,
                        onClick = { onClick() }
                    )
                    .constrainAs(profileImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            if (userInfo.profileImage.isEmpty() && userInfo.name.isNotEmpty()) {
                Text(
                    text = userInfo.name.firstOrNull().toString().uppercase(),
                    style = textStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .constrainAs(text) {
                            top.linkTo(profileImage.top)
                            start.linkTo(profileImage.start)
                            end.linkTo(profileImage.end)
                            bottom.linkTo(profileImage.bottom)
                        }
                )
            } else if (userInfo.profileImage.isEmpty() && userInfo.name.isEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .constrainAs(text) {
                            top.linkTo(profileImage.top)
                            start.linkTo(profileImage.start)
                            end.linkTo(profileImage.end)
                            bottom.linkTo(profileImage.bottom)
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileImagePreview() {
    ProfileImage(
        userInfo = User(
            id = 1,
            name = "",
            email = "test@gmail.com",
            profileImage = "",
            createdDate = ""
        ),
        size = 60
    )
}