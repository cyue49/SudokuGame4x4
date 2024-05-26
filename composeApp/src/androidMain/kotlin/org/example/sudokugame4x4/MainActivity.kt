package org.example.sudokugame4x4

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.compose.runtime.Composable
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                /*val zoomX = ObjectAnimator.ofFloat(screen.iconView, View.SCALE_X, 0.4f, 0.0f)
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 1500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(screen.iconView, View.SCALE_Y, 0.4f, 0.0f)
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 1500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()*/
                val alpha = ObjectAnimator.ofFloat(screen.iconView, View.ALPHA, 1f, 0f)
                alpha.duration = 2000L
                alpha.doOnEnd { screen.remove() }

                alpha.start()
            }
        }
        setContent {
//            TestApp(viewModel = SudokuViewModel())

            Navigator(screen = HomeScreen)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    Navigator(screen = HomeScreen)
}