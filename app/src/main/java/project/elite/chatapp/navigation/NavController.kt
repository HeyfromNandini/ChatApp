package project.elite.chatapp.navigation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import project.elite.chatapp.profile.ProfileScreen
import project.elite.chatapp.screens.ChatScreen
import project.elite.chatapp.screens.HomeScreen
import project.elite.chatapp.screens.StartScreen
import project.elite.chatapp.signin.GoogleAuthUiClient
import project.elite.chatapp.signin.SignInScreen
import project.elite.chatapp.signin.SignInViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    ) {
        val context = LocalContext.current

     val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context.applicationContext,
            oneTapClient = Identity.getSignInClient(context.applicationContext)
        )
    }
    val coroutineScope = rememberCoroutineScope()

        NavHost(
            navController = navController,
            startDestination = "sign_in",
//        startDestination = Screens.MainHomeScreen.route,
        ) {

            composable(
                Screens.StartScreen.route,
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                },
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -300 },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                }
            ) {
              StartScreen(navHostController = navController)
            }


            composable("sign_in") {
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if(googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate("profile")
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if(result.resultCode == Activity.RESULT_OK) {
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
                    if(state.isSignInSuccessful) {
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
                HomeScreen(navController= navController)
            }
            composable(Screens.ChatScreen.route) {
                ChatScreen(navController= navController)
            }
            composable(Screens.StartScreen.route) {
                StartScreen(navHostController = navController)
            }
            composable(Screens.SplashScreen.route) {
               SplashScreen(navController = navController)
            }

    }

}
