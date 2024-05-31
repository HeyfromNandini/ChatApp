package project.elite.chatapp.signin


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)