package de.rogallab.mobile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import de.rogallab.mobile.ui.navigation.AppNavHost
import de.rogallab.mobile.ui.theme.AppTheme

class MainActivity : BaseActivity(tag) {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      // use insets to show to snackbar above ime keyboard
      WindowCompat.setDecorFitsSystemWindows(window, false)

      setContent {
         AppTheme {
            Surface(modifier = Modifier.fillMaxSize(),
               color = MaterialTheme.colorScheme.background
            ){
               Box(Modifier.safeDrawingPadding()) {
                  AppNavHost()
               }
            }
         }
      }
   }

   companion object {
      const val isInfo = true
      const val isDebug = true
      //12345678901234567890123
      private const val tag = "ok>MainActivity       ."
   }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
   AppTheme {
      AppNavHost()
   }
}