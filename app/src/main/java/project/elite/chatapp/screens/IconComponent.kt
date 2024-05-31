package project.elite.chatapp.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun IconComponentImageVector(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: Dp
) {
    Icon(imageVector = icon, contentDescription = "", modifier = modifier.size(size), tint = tint)
}

@Composable
fun IconComponentDrawable(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: Dp
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "",
        modifier = modifier.size(size),
        tint = tint
    )
}



@Composable
fun SpacerWidth(
    width: Dp = 10.dp
) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun SpacerHeight(
    height: Dp = 10.dp
) {
    Spacer(modifier = Modifier.height(height))
}