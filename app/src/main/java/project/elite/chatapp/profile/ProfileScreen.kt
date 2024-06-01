package project.elite.chatapp.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import project.elite.chatapp.signin.UserData

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {


        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .fillMaxWidth()
                .height(190.dp)
                .background(color = Color.Black),
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp, horizontal = 15.dp)
                    .background(color = Color.Black),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Column(modifier = Modifier.padding(top = 20.dp)) {

                    Text(
                        text = "Hello!",
                        textAlign = TextAlign.Center,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray

                    )

                    if (userData?.username != null) {
                        Text(
                            text = userData.username,
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White

                        )

                    }

                }

                Spacer(modifier = Modifier.width(8.dp))
                if (userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
//
//                Button(onClick = onSignOut) {
//                    Text(text = "Sign out")
//                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( horizontal = 25.dp)
                    .padding(bottom = 10.dp)
                    .background(color = Color.Black),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Welcome to ChatAPP",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Yellow

                )
            }
        }
    }
}

