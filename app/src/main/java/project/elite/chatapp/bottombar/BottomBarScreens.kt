package project.elite.chatapp.bottombar

import project.elite.chatapp.R
import project.elite.chatapp.navigation.Screens


sealed class BottomBarScreens(val route: String?, val title: String?, val icon: Int) {


    object Home : BottomBarScreens(
        Screens.HomeScreen.route,
        "Home",
        R.drawable.google_icon
    )
//    object Community : BottomBarScreens(
//        Screens.Communities.route,
//        "Community",
//        R.drawable.b2community
//    )
//    object MainNav: BottomBarScreens(
//        Screens.MainHomeScreen.route,
//        "Home",
//        R.drawable.categories
//    )
//
//    object Contribute : BottomBarScreens(
//        Screens.Contribute.route,
//        "Contribute",
//        R.drawable.b4contribute
//    )
//    object Profile : BottomBarScreens(
//        Screens.MainHomeScreen.route,
//        "Profile",
//        R.drawable.userimg
//    )

}

val items = listOf(
    BottomBarScreens.Home,
//    BottomBarScreens.Community,
//    BottomBarScreens.MainNav,
//    BottomBarScreens.Contribute,
//    BottomBarScreens.Profile,

    )