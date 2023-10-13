package de.rogallab.mobile.ui.people.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import de.rogallab.mobile.domain.utilities.logDebug
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun ShowErrorMessage(
   snackbarHostState: SnackbarHostState, // State ↓
   errorMessage: String,                 // State ↓
   actionLabel: String?,                 // State ↓
   onErrorAction: () -> Unit             // Event ↑
) {

   val tag = "ok>ShowErrorMessage   ."
   logDebug(tag, "Start")

   val snackbarResult = snackbarHostState.showSnackbar(
      message = errorMessage,
      actionLabel = actionLabel,
      withDismissAction = false,
      duration = SnackbarDuration.Short
   )
   if (snackbarResult == SnackbarResult.ActionPerformed) {
         logDebug(tag,"SnackbarResult.ActionPerformed")
         onErrorAction()
   }
}