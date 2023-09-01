package id.dayona.eleanorpokemondatabase.ui.routes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

interface LoadingDialog {
    @Composable
    fun CircleLoading(
        circleColor: Color = Color.Magenta,
        animationDelay: Int = 1000
    ) {

        var circleScale by remember {
            mutableStateOf(0f)
        }

        val circleScaleAnimate = animateFloatAsState(
            targetValue = circleScale,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDelay
                )
            ), label = ""
        )

        LaunchedEffect(Unit) {
            circleScale = 1f
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size = 64.dp)
                    .scale(scale = circleScaleAnimate.value)
                    .border(
                        width = 4.dp,
                        color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                        shape = CircleShape
                    )
            ) {

            }
        }
    }

    @Composable
    fun RippleLoading(
        circleColor: Color = Color.Magenta,
        animationDelay: Int = 1500
    ) {

        val circles = listOf(
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            }
        )
        circles.forEachIndexed { index, animatable ->
            LaunchedEffect(Unit) {
                delay(timeMillis = (animationDelay / 3L) * (index + 1))
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = animationDelay,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size = 200.dp)
                    .background(color = Color.Transparent)
            ) {
                circles.forEachIndexed { _, animatable ->
                    Box(
                        modifier = Modifier
                            .scale(scale = animatable.value)
                            .size(size = 200.dp)
                            .clip(shape = CircleShape)
                            .background(
                                color = circleColor
                                    .copy(alpha = (1 - animatable.value))
                            )
                    ) {
                    }
                }
            }
        }
    }
}