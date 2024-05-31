package project.elite.chatapp.screens


import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jet.firestore.JetFirestore
import com.jet.firestore.append
import com.jet.firestore.getListOfObjects
import project.elite.chatapp.R
import project.elite.chatapp.bottombar.BottomBarScreens.Home.icon
import project.elite.chatapp.data.AllChats
import project.elite.chatapp.data.Chat
import project.elite.chatapp.data.Person
import project.elite.chatapp.data.chatList
import project.elite.chatapp.firestore.updateChatsToFirebase
import project.elite.chatapp.navigation.Collections
import project.elite.chatapp.signin.UserData
import project.elite.chatapp.ui.theme.Gray
import project.elite.chatapp.ui.theme.Gray400
import project.elite.chatapp.ui.theme.LightRed
import project.elite.chatapp.ui.theme.LightYellow
import project.elite.chatapp.ui.theme.Yellow


@Composable
fun ChatScreen(
    navController: NavController,
    userData: UserData?
) {

    val context = LocalContext.current
    var message by remember { mutableStateOf("") }
    val data =
        navController.previousBackStackEntry?.savedStateHandle?.get<Person>("data") ?: Person()

    var allChats by remember { mutableStateOf<List<AllChats>?>(null) }
//    var userChats by remember { mutableStateOf<AllChats>(null) }


    JetFirestore(path = {
        collection(Collections.AllChats.name)
    }, onRealtimeCollectionFetch = { values, _ ->
        allChats = values?.getListOfObjects()

//        userData?.let { userData ->
//            allChats?.let { allChats ->
//                for (chats in allChats) {
//                    if (chats.sender == userData.username) {
//                        userChats?.append()
//                    }
//
//                }
//            }
//        }

    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                UserNameRow(
                    person = data,
                    modifier = Modifier.padding(
                        top = 60.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.White, RoundedCornerShape(
                                topStart = 30.dp, topEnd = 30.dp
                            )
                        )
                        .padding(top = 25.dp)

                ) {
                    LazyColumn(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            top = 25.dp,
                            end = 15.dp,
                            bottom = 75.dp
                        )
                    ) {
                        items(allChats ?: emptyList(), key = { it.time }) { allChats ->
                            userData?.let {
                                if (allChats.sender == it.username) {
                                    ChatRow(chat = allChats, userData = it)
                                }
                            }
                        }
                    }
                }
            }

            CustomTextField(
                text = message, onValueChange = { message = it },
                onClick = {
                      updateChatsToFirebase(
                         context = context,
                          sender = userData?.username ?: "",
                          receiver = data.name ?: "",
                          time = System.currentTimeMillis(),
                          message = message

                      )
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .align(BottomCenter)
            )
        }
    }

}

@Composable
fun ChatRow(
    chat: AllChats,
    userData: UserData
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (chat.sender == userData.username) Alignment.Start else Alignment.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (chat.sender == userData.username) LightRed else LightYellow,
                    RoundedCornerShape(100.dp)
                ),
            contentAlignment = Center
        ) {
            Text(
                text = chat.message, style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp
                ),
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
                textAlign = TextAlign.End
            )
        }
        Text(
            text = chat.time.toString(),
            style = TextStyle(
                color = Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    TextField(
        value = text, onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = stringResource(R.string.type_message),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Gray400,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            textColor = Color.Black
        ),
        leadingIcon = { CommonIconButton(imageVector = Icons.Default.Add) },
        trailingIcon = { CommonIconButtonDrawable(R.drawable.mic, onClick = {
            onClick()
        }) },
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape
    )

}

@Composable
fun CommonIconButton(
    imageVector: ImageVector
) {

    Box(
        modifier = Modifier
            .background(Yellow, CircleShape)
            .size(33.dp), contentAlignment = Center
    ) {
        IconComponentImageVector(icon = imageVector, size = 15.dp, tint = Color.Black)
    }

}

@Composable
fun CommonIconButtonDrawable(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Yellow, CircleShape)
            .size(33.dp), contentAlignment = Center
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .size(15.dp)
                .clickable {
                    onClick()
                }

        )
    }

}

@Composable
fun UserNameRow(
    modifier: Modifier = Modifier,
    person: Person
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {

            IconComponentDrawable(icon = person.icon, size = 42.dp)
            SpacerWidth()
            Column {
                person.name?.let {
                    Text(
                        text = it, style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
                Text(
                    text = stringResource(R.string.online), style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
        IconComponentImageVector(icon = Icons.Default.MoreVert, size = 24.dp, tint = Color.White)
    }

}
