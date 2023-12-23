package com.madteam.split.ui.screens.welcome.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madteam.split.R
import com.madteam.split.ui.theme.SplitTheme

@Composable
fun WelcomeScreen() {
    Scaffold(
        containerColor = SplitTheme.colors.neutral.backgroundExtraWeak
    ) {
        Box(
            modifier = Modifier.padding(it),
            contentAlignment = Alignment.Center
        ) {
            WelcomeContent()
        }
    }
}

@Composable
fun WelcomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp),
                painter = painterResource(id = R.drawable.ds_split_logo_black),
                contentDescription = "TODO: Add content description"//TODO: Add content description
            )
            Text(
                text = "Welcome to Split",
                style = SplitTheme.typography.heading.s,
                color = SplitTheme.colors.neutral.textTitle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}