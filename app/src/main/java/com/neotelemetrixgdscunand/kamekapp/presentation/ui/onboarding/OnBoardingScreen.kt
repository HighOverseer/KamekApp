package com.neotelemetrixgdscunand.kamekapp.presentation.ui.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey45
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey71
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Orange90
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component.PrimaryButton


@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = { },
    navigateToMainPage: () -> Unit = {}
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val constraints = rememberConstraintSet(selectedTabIndex)

    val onBoardingImageResId = remember(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> R.drawable.onboarding_1
            1 -> R.drawable.onboarding_2
            else -> R.drawable.onboarding_3
        }
    }

    val headlineResId = remember(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> R.string.onboarding_1
            1 -> R.string.onboarding_2
            else -> R.string.onboarding_3
        }
    }

    val descResId = remember(selectedTabIndex) {
        when (selectedTabIndex) {
            0 -> R.string.onboarding_1_desc
            1 -> R.string.onboarding_desc_2
            else -> R.string.onboarding_desc_3
        }
    }

    BackHandler(
        enabled = true
    ) {
        if (selectedTabIndex > 0) {
            selectedTabIndex--
        } else navigateUp()
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize(),
        constraintSet = constraints
    ) {
        val configuration = LocalConfiguration.current
        val onBoardingImageAspectRatio = 1.88f
        val leftCloudWidthRatio = 0.55f
        val leftCloudHeightRatio = 0.1138f
        val rightCloudWidthRatio = 0.40f
        val rightCloudHeightRatio = 0.0687f
        val birdAspectRatio = 5.02f
        val birdWidthToParentRatio = 0.2f
        val birdWidth = remember {
            (configuration.screenWidthDp * birdWidthToParentRatio).dp
        }
        val buttonWidthMin = remember {
            (configuration.screenWidthDp * 0.456f).dp
        }


        Image(
            modifier = Modifier
                .width(birdWidth)
                .aspectRatio(birdAspectRatio)
                .layoutId(LayoutUtil.BIRD_ID),
            painter = painterResource(R.drawable.bird),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .fillMaxHeight(rightCloudHeightRatio)
                .fillMaxWidth(rightCloudWidthRatio)
                .layoutId(LayoutUtil.RIGHT_CLOUD_ID),
            painter = painterResource(
                R.drawable.right_cloud
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )


        Image(
            modifier = Modifier
                .fillMaxHeight(leftCloudHeightRatio)
                .fillMaxWidth(leftCloudWidthRatio)
                .layoutId(LayoutUtil.LEFT_CLOUD_ID),
            painter = painterResource(
                R.drawable.left_cloud
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(LayoutUtil.HEADLINE_ID),
            textAlign = TextAlign.Center,
            text = stringResource(headlineResId),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )


        AnimatedContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .aspectRatio(onBoardingImageAspectRatio)
                .layoutId(LayoutUtil.ON_BOARDING_IMAGE_ID),
            targetState = onBoardingImageResId,
            label = ""
        ) { targetOnBoardingImageResId ->
            Image(
                modifier = Modifier,
                painter = painterResource(
                    targetOnBoardingImageResId
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(LayoutUtil.DESC_ID),
            textAlign = TextAlign.Center,
            text = stringResource(descResId),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.W500
            ),
            color = Grey45
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(LayoutUtil.TABS_ID),
            horizontalArrangement = Arrangement.Center

        ) {
            Box(
                Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .drawWithCache {
                        onDrawWithContent {
                            drawCircle(color = if (selectedTabIndex == 0) Orange90 else Grey71)
                        }
                    }
            )
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .drawWithCache {
                        onDrawWithContent {
                            drawCircle(color = if (selectedTabIndex == 1) Orange90 else Grey71)
                        }
                    }
            )
            Spacer(Modifier.width(8.dp))
            Box(
                Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .drawWithCache {
                        onDrawWithContent {
                            drawCircle(color = if (selectedTabIndex == 2) Orange90 else Grey71)
                        }
                    }
            )
        }


        PrimaryButton(
            modifier = Modifier
                .height(44.dp)
                .widthIn(min = buttonWidthMin)
                .layoutId(LayoutUtil.BUTTON_ID),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.5.dp),
            text = if (selectedTabIndex < 2) {
                stringResource(R.string.selanjutnya)
            } else stringResource(
                R.string.mulai
            ),
            trailingIcon = if (selectedTabIndex < 2) {
                {
                    Icon(
                        Icons.Default.ArrowForward,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            } else null,
            onClick = {
                if (selectedTabIndex < 2) {
                    selectedTabIndex++
                } else navigateToMainPage()
            }
        )
    }
}

@Composable
private fun rememberConstraintSet(selectedTabIndex: Int): ConstraintSet {
    val configuration = LocalConfiguration.current
    val screenWidthDp = remember {
        configuration.screenWidthDp.dp
    }
    val screenHeightDp = remember {
        configuration.screenHeightDp.dp
    }

    val birdToLeftCloudMargin = remember(selectedTabIndex) {
        val leftCloudHeightToParentRatio = 0.1138f
        val cloudHeight = screenHeightDp * leftCloudHeightToParentRatio
        val birdToLeftCloudMarginRatio = when (selectedTabIndex) {
            0 -> 0f
            1 -> -0.75f
            else -> -0.9f
        }
        cloudHeight * birdToLeftCloudMarginRatio
    }

    val birdToParentMargin = remember(selectedTabIndex) {
        val birdToParentMarginRatio = when (selectedTabIndex) {
            0 -> 0.135f
            1 -> 0.3f
            else -> 0.76f
        }
        (screenWidthDp * birdToParentMarginRatio)
    }

    val leftCloudToParentMargin = remember(selectedTabIndex) {
        val leftCloudWidthEstimation = (screenWidthDp * 0.55f)
        val ratio = when (selectedTabIndex) {
            0 -> 0f
            1 -> -0.45f
            else -> -0.7f
        }
        (leftCloudWidthEstimation * ratio)
    }

    val rightCloudToParentMargin = remember(selectedTabIndex) {
        val rightCloudWidthEstimation = (screenWidthDp * 0.40f)
        val ratio = when (selectedTabIndex) {
            0, 1 -> -0.2f
            else -> 0f
        }
        (rightCloudWidthEstimation * ratio)
    }

    val animateRightCloudToParentMargin by animateDpAsState(
        targetValue = rightCloudToParentMargin,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    val animateLeftCloudToParentMargin by animateDpAsState(
        targetValue = leftCloudToParentMargin,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    val animateBirdToParentMargin by animateDpAsState(
        targetValue = birdToParentMargin,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    val animateBirdToLeftCloudMargin by animateDpAsState(
        targetValue = birdToLeftCloudMargin,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    // Just Need One To Trigger
    return remember(animateBirdToParentMargin) {
        val rightCloudToLeftCloudMarginRatio = -0.0356f
        val rightCloudToLeftCloudMargin = (rightCloudToLeftCloudMarginRatio * screenHeightDp)

        val leftCloudToHeadlineMarginRatio = 0.061f
        val leftCloudToHeadlineMargin = (screenHeightDp * leftCloudToHeadlineMarginRatio)

        ConstraintSet {
            val onBoardingImage = createRefFor(LayoutUtil.ON_BOARDING_IMAGE_ID)
            val headline = createRefFor(LayoutUtil.HEADLINE_ID)
            val desc = createRefFor(LayoutUtil.DESC_ID)
            val tabs = createRefFor(LayoutUtil.TABS_ID)
            val button = createRefFor(LayoutUtil.BUTTON_ID)
            val leftCloud = createRefFor(LayoutUtil.LEFT_CLOUD_ID)
            val rightCloud = createRefFor(LayoutUtil.RIGHT_CLOUD_ID)
            val bird = createRefFor(LayoutUtil.BIRD_ID)

            constrain(leftCloud) {
                bottom.linkTo(headline.top, margin = leftCloudToHeadlineMargin)
                start.linkTo(parent.start, margin = animateLeftCloudToParentMargin)
            }

            constrain(bird) {
                start.linkTo(parent.start, margin = animateBirdToParentMargin)
                bottom.linkTo(leftCloud.top, margin = animateBirdToLeftCloudMargin)
            }

            constrain(rightCloud) {
                end.linkTo(parent.end, margin = animateRightCloudToParentMargin)
                bottom.linkTo(leftCloud.top, margin = rightCloudToLeftCloudMargin)
            }

            constrain(headline) {
                bottom.linkTo(onBoardingImage.top, margin = 40.dp)
            }

            constrain(button) {
                top.linkTo(tabs.bottom, margin = 64.dp)
                start.linkTo(tabs.start)
                end.linkTo(tabs.end)
            }

            constrain(tabs) {
                top.linkTo(desc.bottom, margin = 24.dp)
            }

            constrain(desc) {
                top.linkTo(onBoardingImage.bottom, margin = 32.dp)
            }

            constrain(onBoardingImage) {
                centerVerticallyTo(parent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingScreenPreview() {
    KamekAppTheme {
        OnBoardingScreen()
    }
}