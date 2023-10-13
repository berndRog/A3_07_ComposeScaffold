package de.rogallab.mobile.ui.people.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import de.rogallab.mobile.domain.utilities.logDebug

suspend fun showErrorMessage(
   snackbarHostState: SnackbarHostState, // State ↓
   errorMessage: String,                 // State ↓
   actionLabel: String?,                 // State ↓
   onErrorAction: () -> Unit             // Event ↑
) {

   val tag = "ok>showErrorMessage   ."
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