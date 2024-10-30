package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component.AuthHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component.AuthTextField
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component.PrimaryButton
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth.component.SmallLogoHeadline


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToTopLevelPage:() -> Unit = {}
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val usernameInteractionSource = remember { MutableInteractionSource() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val textMaxLength = 30

    LoginContent(
        modifier = modifier,
        username = username,
        password = password,
        onUsernameChange = {
            if(it.length <= textMaxLength){
                username = it
            }

        },
        onPasswordChange = {
            if(it.length <= textMaxLength){
                password = it
            }
        },
        usernameInteractionSource = usernameInteractionSource,
        passwordInteractionSource = passwordInteractionSource,
        isPasswordVisible = isPasswordVisible,
        changePasswordVisibility = {
            isPasswordVisible = it
        },
        navigateToTopLevelPage = navigateToTopLevelPage
    )
}


@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    username:String = "",
    password:String = "",
    onUsernameChange:(String) -> Unit = {},
    onPasswordChange:(String) -> Unit = {},
    isPasswordVisible:Boolean = false,
    changePasswordVisibility:(Boolean) -> Unit = {},
    usernameInteractionSource: MutableInteractionSource = MutableInteractionSource(),
    passwordInteractionSource: MutableInteractionSource = MutableInteractionSource(),
    navigateToTopLevelPage:() -> Unit = {}
) {
    val topMarginToContentRatio = 0.0838f
    val headlineToLogoMarginRatio = 0.0805f
    val formToHeadlineMarginRatio = 0.0697f
    val buttonToFormMarginRatio =  0.0872f

    val isUsernameTextFieldFocused by usernameInteractionSource.collectIsFocusedAsState()
    val isPasswordTextFieldFocused by passwordInteractionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxHeight(topMarginToContentRatio)
        )

        SmallLogoHeadline()

        Spacer(
            modifier = Modifier
                .fillMaxHeight(headlineToLogoMarginRatio)
        )

        AuthHeaderSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.masuk),
            subTitle = stringResource(R.string.masuk_untuk_akses_fitur_deteksi_penyakit)
        )

        Spacer(
            modifier = Modifier
                .fillMaxHeight(formToHeadlineMarginRatio)
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {

            AuthTextField(
                title = stringResource(R.string.email),
                interactionSource = usernameInteractionSource,
                onValueChange = onUsernameChange,
                value = username,
                isFocused = isUsernameTextFieldFocused
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            AuthTextField(
                title = stringResource(R.string.password),
                interactionSource = passwordInteractionSource,
                onValueChange = onPasswordChange,
                value = password,
                isFocused = isPasswordTextFieldFocused,
                visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable{
                                changePasswordVisibility(!isPasswordVisible)
                            },
                        imageVector = if(isPasswordVisible)
                            ImageVector
                            .vectorResource(
                                R.drawable.ic_eye,
                            )
                        else ImageVector
                            .vectorResource(R.drawable.ic_eye),
                        contentDescription = stringResource(R.string.visibilitas_password)
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.End),
                textAlign = TextAlign.Right,
                text = stringResource(R.string.lupa_password),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.fillMaxHeight(buttonToFormMarginRatio)
            )

           PrimaryButton(
               text = stringResource(R.string.masuk),
               onNavigate = navigateToTopLevelPage
           )

        }



    }
}


@Preview(showBackground = true)
@Composable
private fun LoginContentPreview() {
    KamekAppTheme {
        LoginContent()
    }
}