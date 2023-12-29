package com.example.authapp.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.authapp.core.utils.Resource
import com.example.authapp.core.utils.Screen
import com.example.authapp.presentation.common.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.regenerateCode.value) {
        when (val result = viewModel.regenerateCode.value) {
            is Resource.Success -> {
                viewModel.resetResult()
                navController.navigate(Screen.ConfirmCodeScreen.route)
            }

            is Resource.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            }

            is Resource.Inactive -> {

            }

            is Resource.Loading -> {

            }
        }
    }

    var login by remember { mutableStateOf("") }


    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {


        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = {
                Text(text = "Номер телефона")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                if (isValidRussianPhoneNumber(login)) {
                    viewModel.regenerateCode(login.removeRange(0, 0))
                } else {
                    Toast.makeText(context, "Заполните номер правильно", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Отправить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.regenerateCode.value.showDialog()


    }

}

fun isValidRussianPhoneNumber(input: String): Boolean {
    val regex = Regex("""\+7\d{10}""")
    return regex.matches(input)
}


@Composable
fun <T> Resource<T>.showDialog() {
    if (this is Resource.Loading) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}