package com.autistasgourmet.pethub.ui.components.appCards

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableCard(
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
            .graphicsLayer {
                rotationZ = rotation.value
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            // Si se arrastró lo suficiente a la derecha (Like)
                            if (offsetX.value > screenWidth / 3) {
                                offsetX.animateTo(screenWidth * 2)
                                onSwipeRight()
                            }
                            // Si se arrastró a la izquierda (Dislike)
                            else if (offsetX.value < -screenWidth / 3) {
                                offsetX.animateTo(-screenWidth * 2)
                                onSwipeLeft()
                            }
                            // Devolver a la posición original
                            else {
                                launch { offsetX.animateTo(0f) }
                                launch { offsetY.animateTo(0f) }
                                launch { rotation.animateTo(0f) }
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                            offsetY.snapTo(offsetY.value + dragAmount.y)
                            // Calcula una rotación sutil dependiendo del desplazamiento en X
                            val targetRotation = (offsetX.value / screenWidth) * 15f
                            rotation.snapTo(targetRotation)
                        }
                    }
                )
            }
    ) {
        content()
    }
}
