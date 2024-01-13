package com.madteam.split.ui.screens.myuser.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.madteam.split.R
import com.madteam.split.ui.navigation.Screens
import com.madteam.split.ui.screens.myuser.state.MyUserUIEvent
import com.madteam.split.ui.screens.myuser.state.MyUserUIState
import com.madteam.split.ui.screens.myuser.viewmodel.MyUserViewModel
import com.madteam.split.ui.theme.DSBasicTextField
import com.madteam.split.ui.theme.DSEmailTextField
import com.madteam.split.ui.theme.DSModalBottomSheet
import com.madteam.split.ui.theme.DangerDialog
import com.madteam.split.ui.theme.ElevatedIconButton
import com.madteam.split.ui.theme.ErrorDialog
import com.madteam.split.ui.theme.InfoMessage
import com.madteam.split.ui.theme.LoadingDialog
import com.madteam.split.ui.theme.ModalOption
import com.madteam.split.ui.theme.NavigationAndActionTopAppBar
import com.madteam.split.ui.theme.ProfileImage
import com.madteam.split.ui.theme.SecondaryLargeButton
import com.madteam.split.ui.theme.SplitTheme
import com.madteam.split.utils.ui.navigateWithPopUpTo

@Composable
fun MyUserScreen(
  navController: NavController,
  viewModel: MyUserViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  if (state.showProfileImageModal) {
    DSModalBottomSheet(
      optionsList = mutableListOf(
        ModalOption(
          iconDescription = R.string.update_profile_image_from_device,
          icon = Icons.Outlined.Image,
          title = R.string.update_profile_image_from_device,
          action = {}
        ),
        ModalOption(
          iconDescription = R.string.delete_profile_image,
          icon = Icons.Outlined.Delete,
          title = R.string.delete_profile_image,
          action = {}
        ),
        ModalOption(
          iconDescription = R.string.choose_an_avatar,
          icon = Icons.Outlined.EmojiEmotions,
          title = R.string.choose_an_avatar,
          action = {}
        )
      ),
      onClose = {
        viewModel.onEvent(MyUserUIEvent.OnShowProfileImageModalStateChanged(false))
      }
    )
  } else if (state.showSettingsModal) {
    DSModalBottomSheet(
      optionsList = mutableListOf(
        ModalOption(
          iconDescription = R.string.log_out,
          icon = Icons.AutoMirrored.Outlined.Logout,
          title = R.string.log_out,
          isDangerOption = true,
          action = {
            viewModel.onEvent(MyUserUIEvent.OnShowSettingsModalStateChanged(false))
            viewModel.onEvent(MyUserUIEvent.OnShowSignOutDialogStateChanged(true))
          }
        )
      ),
      onClose = {
        viewModel.onEvent(MyUserUIEvent.OnShowSettingsModalStateChanged(false))
      }
    )
  }
  Scaffold(
    containerColor = SplitTheme.colors.neutral.backgroundExtraWeak,
    topBar = {
      NavigationAndActionTopAppBar(
        onNavigationClick = {
          navController.popBackStack()
        },
        onActionClick = {
          viewModel.onEvent(MyUserUIEvent.OnShowSettingsModalStateChanged(true))
        },
        title = R.string.your_profile
      )
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(it)
    ) {
      MyUserContent(
        state = state,
        onSignOutDialogStateChanged = { state ->
          viewModel.onEvent(MyUserUIEvent.OnShowSignOutDialogStateChanged(state))
        },
        onShowInfoMessageStateChanged = { state ->
          viewModel.onEvent(MyUserUIEvent.OnShowSharedInfoMessageStateChanged(state))
        },
        onShowProfileImageModalStateChanged = { state ->
          viewModel.onEvent(MyUserUIEvent.OnShowProfileImageModalStateChanged(state))
        },
        onSignOutConfirmed = {
          viewModel.onEvent(MyUserUIEvent.OnSignOutConfirmedClick)
          navController.navigateWithPopUpTo(
            route = Screens.WelcomeScreen.route,
            popUpTo = Screens.MyUserScreen.route,
            inclusive = true
          )
        },
        navigateBackWithError = {
          viewModel.onEvent(MyUserUIEvent.OnShowErrorMessageStateChanged(false))
          navController.popBackStack()
        },
        onSaveInfoClick = {
          viewModel.onEvent(MyUserUIEvent.OnSaveInfoClick)
        },
        onNameChanged = { name ->
          viewModel.onEvent(MyUserUIEvent.OnNameChanged(name))
        }
      )
    }
  }
}

@Composable
fun MyUserContent(
  state: MyUserUIState,
  onSignOutDialogStateChanged: (Boolean) -> Unit,
  onShowInfoMessageStateChanged: (Boolean) -> Unit,
  onShowProfileImageModalStateChanged: (Boolean) -> Unit,
  onNameChanged: (String) -> Unit,
  onSignOutConfirmed: () -> Unit,
  navigateBackWithError: () -> Unit,
  onSaveInfoClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    AnimatedVisibility(
      visible = state.showSharedInfoMessage,
      modifier = Modifier.padding(bottom = 24.dp)
    ) {
      InfoMessage(
        messageText = R.string.shared_user_changes_info,
        cancelable = true,
        onCloseClick = {
          onShowInfoMessageStateChanged(false)
        }
      )
    }
    ConstraintLayout(
      modifier = Modifier.fillMaxWidth()
    ) {
      val (profileImage, editImageButton) = createRefs()
      ProfileImage(
        modifier = Modifier
          .constrainAs(profileImage) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          },
        userInfo = state.userInfo,
        size = 120
      )
      ElevatedIconButton(
        modifier = Modifier
          .constrainAs(editImageButton) {
            start.linkTo(profileImage.end, ((-36).dp))
            top.linkTo(profileImage.bottom, ((-48).dp))
          },
        onClick = {
          onShowProfileImageModalStateChanged(true)
        },
        icon = Icons.Filled.Edit,
        iconDescription = R.string.edit_icon_description
      )
    }

    Spacer(modifier = Modifier.size(36.dp))
    DSEmailTextField(
      value = state.userInfo.email,
      onValueChange = {
        //Not editable
      },
      placeholder = R.string.enter_your_email,
      enabled = false
    )
    DSBasicTextField(
      value = state.userInfo.name,
      onValueChange = {
        onNameChanged(it)
      },
      placeholder = R.string.enter_your_name
    )
  }
  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    verticalAlignment = Alignment.Bottom
  ) {
    SecondaryLargeButton(
      onClick = {
        onSaveInfoClick()
      },
      text = R.string.save_changes,
      enabled = state.hasInfoBeenModified
    )
  }

  if (state.showErrorMessage) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      val errorTitle = stringResource(id = R.string.generic_error_title)
      val errorText = stringResource(id = state.errorMessage ?: R.string.generic_error_text)
      val errorButton = R.string.ok

      ErrorDialog(
        setShowDialog = {
          navigateBackWithError()
        },
        errorTitle = errorTitle,
        errorText = errorText,
        errorButton = errorButton
      )
    }
  }

  if (state.showLoading) {
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

  if (state.showLogOutDialog) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(SplitTheme.colors.neutral.backgroundHeavy.copy(alpha = 0.3f)),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      DangerDialog(
        setShowDialog = {
          onSignOutDialogStateChanged(it)
        },
        title = R.string.is_it_a_goodbye,
        text = R.string.log_out_confirm_text,
        cancelButtonText = R.string.cancel,
        continueButtonText = R.string.continue_log_out,
        onContinueClick = {
          onSignOutConfirmed()
        },
      )
    }
  }
}

@Preview
@Composable
fun MyUserScreenPreview() {
  MyUserScreen(
    rememberNavController()
  )
}