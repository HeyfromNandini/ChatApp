package project.elite.chatapp.navigation




sealed class Screens(val route: String) {


    object ChatScreen : Screens("ChatScreen")
    object HomeScreen : Screens("HomeScreen")
    object StartScreen : Screens("StartScreen")

    object ProfileScreen : Screens("profile")
    object SplashScreen : Screens("splash")
    object  SignInScreen : Screens("si")

}

sealed class Collections(val name: String) {
    object AllChats: Collections("AllChats")
}