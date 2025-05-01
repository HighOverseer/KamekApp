package com.neotelemetrixgdscunand.kamekapp.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.collectChannelWhenStarted
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToAuthPage: () -> Unit = {},
    navigateToMainPage: () -> Unit = {},
    navigateToOnBoarding: () -> Unit = {},
    viewModel: SplashViewModel = hiltViewModel()
) {

    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(true) {
        delay(2000L)

        lifecycle.collectChannelWhenStarted(viewModel.isReadyEvent) {
            val (isAlreadyLoggedIn, isFirstTime) = it
            if (!isAlreadyLoggedIn) {
                navigateToAuthPage()
            } else {
                if (isFirstTime) {
                    navigateToOnBoarding()
                } else navigateToMainPage()
            }
        }
    }

    SplashContent(
        modifier = modifier
    )
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Maroon55),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(157.dp)
                .height(157.dp),
            imageVector = ImageVector
                .vectorResource(R.drawable.ic_logo),
            contentDescription = stringResource(R.string.logo)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    KamekAppTheme {
        SplashScreen()
    }
}

