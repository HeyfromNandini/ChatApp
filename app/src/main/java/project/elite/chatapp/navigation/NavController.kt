package project.elite.chatapp.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import project.elite.chatapp.datastore.UserDatastore
import project.elite.chatapp.profile.ProfileScreen
import project.elite.chatapp.screens.ChatScreen
import project.elite.chatapp.screens.HomeScreen
import project.elite.chatapp.screens.StartScreen
import project.elite.chatapp.signin.GoogleAuthUiClient
import project.elite.chatapp.signin.SignInScreen
import project.elite.chatapp.signin.SignInViewModel
import project.elite.chatapp.signin.UserData

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainNavigation(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val datastore = UserDatastore(context)
    val name = datastore.getName.collectAsState(initial = "")
    val isLoggedIn = datastore.getLoginStatus.collectAsState(initial = false)
    var userData by remember {
        mutableStateOf<UserData?>(null)
    }

    LaunchedEffect(key1 = isLoggedIn.value) {
        userData = UserData(userId = "ABC123", username = name.value, profilePictureUrl = null)
    }

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(context.applicationContext)
        )
    }
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
//        startDestination = "sign_in",
        startDestination = Screens.SplashScreen.route,
    ) {
        composable(
            Screens.StartScreen.route,
        ) {
            StartScreen(navHostController = navController)
        }


        composable("sign_in") {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("profile")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context.applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate("profile")
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context.applicationContext,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.popBackStack()
                    }
                }
            )
        }
        composable(Screens.HomeScreen.route) {
            println("UserData is ${googleAuthUiClient.getSignedInUser()}")
            HomeScreen(navController = navController, userData = userData)
        }
        composable(Screens.ChatScreen.route) {
            println("UserDataSS is ${googleAuthUiClient.getSignedInUser()}")
            ChatScreen(navController = navController, userData = googleAuthUiClient.getSignedInUser())
        }
        composable(Screens.StartScreen.route) {
            StartScreen(navHostController = navController)
        }
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

    }

}
