package project.elite.chatapp.navigation




sealed class Screens(val route: String) {


    object ChatScreen : Screens("MainHome")
    object HomeScreen : Screens("MainHome")
    object StartScreen : Screens("MainHome")

    object ProfileScreen : Screens("profile")
    object SplashScreen : Screens("splash")
    object  SignInScreen : Screens("si")

}