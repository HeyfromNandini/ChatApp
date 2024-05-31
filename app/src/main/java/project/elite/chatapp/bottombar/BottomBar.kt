package project.elite.chatapp.bottombar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import project.elite.chatapp.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import project.elite.chatapp.navigation.Screens

@Composable
fun BottomBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean> = mutableStateOf(true),
    openDialogue: MutableState<Boolean> = mutableStateOf(false)
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    if (index == 2) {
                        CustomCard(
                            title = item.title ?: "",
                            image = R.drawable.google_icon,
                            onClick = {
                                openDialogue.value = true // Open dialogue for the button at index 2
                            }
                        )
                    } else {
                        BottomNavigationItem(
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        painter = painterResource(id = it),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(35.dp)
                                            .padding(bottom = 5.dp),
                                        tint = Color.Black
                                    )
                                }
                            },
                            label = {
                                item.title?.let {
                                    Text(
                                        text = it,
                                        color = Color.Black,
                                        softWrap = true,
                                        fontSize = 8.5.sp
                                    )
                                }
                            },
                            selected = currentRoute?.hierarchy?.any { nav ->
                                nav.route == item.route
                            } == true,
                            selectedContentColor = Color.Yellow,
                            unselectedContentColor = Color.LightGray,
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.White),
                            onClick = {
                                item.route?.let { route ->
                                    navController.navigate(route) {
                                        popUpTo(Screens.StartScreen.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
        Dialogue(openDialogue = openDialogue, navHostController = navController)
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomCard(
    title: String,
    image: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Card(
            modifier = modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .size(53.dp), shape = CircleShape,
            onClick = onClick,

            ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
//        Text(
//            text = title,
//            modifier = Modifier.padding(8.dp),
//            color = Color.Black,
//            fontSize = 12.sp
//        )
    }
}


@Composable
fun Dialogue(openDialogue: MutableState<Boolean>, navHostController: NavController) {
    if (openDialogue.value) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom){
            AlertDialog(modifier = Modifier.width(250.dp).background(color = Color.Transparent),
                onDismissRequest = { openDialogue.value = false },
                confirmButton = {


                },
                dismissButton = {
                    Card(modifier =Modifier.background(color = Color.Transparent).clickable { openDialogue.value = false }) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            DialogueCardRow(navHostController)
                        }
                    }
                }
            )
        }

    }
}


@Composable
fun DialogueCardRow(navHostController: NavController) {

    Row (modifier = Modifier
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){

        Column{
           DialogueCard(image = R.drawable.google_icon) {
                navHostController.navigate(Screens.StartScreen.route)
            }
            DialogueCard(image = R.drawable.google_icon) {
                navHostController.navigate(Screens.StartScreen.route)
            }
        }
        Column{
            DialogueCard(image = R.drawable.google_icon) {
                navHostController.navigate(Screens.StartScreen.route)
            }
            DialogueCard(image = R.drawable.google_icon) {
                navHostController.navigate(Screens.StartScreen.route)
            }
        }



    }

}

@Composable
fun DialogueCard(image: Int, onClick: () -> Unit) {


    Card(
        modifier = Modifier.run {
            padding(10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { onClick() }
                .size(58.dp)
        }
        , shape = CircleShape,
//        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

    }
}