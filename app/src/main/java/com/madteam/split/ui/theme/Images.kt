package com.madteam.split.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.madteam.split.domain.model.User
import com.bumptech.glide.integration.compose.GlideImage
import com.madteam.split.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileImage(
    userInfo: User,
    size: Int
) {
    Column {

    }

    GlideImage(
        model = userInfo.profileImage,
        contentDescription = stringResource(id = R.string.user_profile_image_description),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .background(SplitTheme.colors.primary.backgroundWeak)
            .clip(CircleShape)
    )

}

@Preview
@Composable
fun ProfileImagePreview() {
    ProfileImage(
        userInfo = User(
            id = "1",
            name = "test",
            email = "test@gmail.com",
            profileImage = ""
        ),
        size = 100
    )
}