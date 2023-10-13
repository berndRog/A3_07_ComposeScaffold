package de.rogallab.mobile.ui.people

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.rogallab.mobile.R
import de.rogallab.mobile.domain.model.Person
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logInfo
import de.rogallab.mobile.ui.navigation.NavScreen
import de.rogallab.mobile.ui.people.composables.InputNameMailPhone
import de.rogallab.mobile.ui.people.composables.showErrorMessage
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
   id: UUID?,
   navController: NavController,
   viewModel: PeopleViewModel,
) {

   val tag: String = "ok>PersonDetailScreen ."
   logDebug(tag, "Start")
   // viewModel.clear()

   var savedPerson by remember {
      mutableStateOf(Person("", ""))
   }

   LaunchedEffect(Unit) {
      id?.let {
         logDebug(tag, "ReadById()")
         viewModel.readById(id)
         savedPerson = viewModel.getPersonFromState()
      } ?: run {
         viewModel.onErrorMessage("id not found","PersonDetailScreen")
      }
   }

   BackHandler(
      enabled = true,
      onBack = {
         logInfo(tag, "Back Navigation (Abort)")
         viewModel.setStateFromPerson(savedPerson, savedPerson.id)
         // Navigate to 'PeopleList' destination and clear the back stack. As a
         // result, no further reverse navigation will be possible."
         navController.navigate(route = NavScreen.PeopleList.route) {
            popUpTo(route = NavScreen.PeopleList.route) {
               inclusive = true
            }
         }
      }
   )

   val snackbarHostState = remember { SnackbarHostState() }

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(stringResource(R.string.person_detail)) },
            navigationIcon = {
               IconButton(onClick = {
                  logDebug(tag, "Navigation to Home and save changes")
                  viewModel.update()
                  navController.navigate(route = NavScreen.PeopleList.route) {
                     popUpTo(route = NavScreen.PeopleList.route) { inclusive = true }
                  }
               }) {
                  Icon(
                     imageVector = Icons.Default.ArrowBack,
                     contentDescription = stringResource(R.string.back)
                  )
               }
            }
         )
      },
      snackbarHost = {
         SnackbarHost(hostState = snackbarHostState) { data ->
            Snackbar(
               snackbarData = data,
               actionOnNewLine = true
            )
         }
      },
      content = { innerPadding ->

         Column(
            modifier = Modifier
               .padding(top = innerPadding.calculateTopPadding())
//                .statusBarsPadding()
               .padding(horizontal = 16.dp)
               .fillMaxWidth()
               .verticalScroll(
                  state = rememberScrollState(),
                  enabled = true,
                  reverseScrolling = true
               )
         ) {

            InputNameMailPhone(
               firstName = viewModel.firstName,                         // State ↓
               onFirstNameChange = { viewModel.onFirstNameChange(it) }, // Event ↑
               lastName = viewModel.lastName,                           // State ↓
               onLastNameChange = { viewModel.onLastNameChange(it) },   // Event ↑
               email = viewModel.email,                                 // State ↓
               onEmailChange = { viewModel.onEmailChange(it) },         // Event ↑
               phone = viewModel.phone,                                 // State ↓
               onPhoneChange = { viewModel.onPhoneChange(it) }          // Event ↑
            )
         }
      }
   )

   // testing the snackbar
   // viewModel.onErrorMessage("Test SnackBar: Fehlermeldung ...", "PersonDetailScreen")

   viewModel.errorMessage?.let {
      if (viewModel.errorFrom == "PersonDetailScreen") {
         LaunchedEffect(it) {
            showErrorMessage(
               snackbarHostState = snackbarHostState,
               errorMessage = it,
               actionLabel = "Abbrechen",
               onErrorAction = { viewModel.onErrorAction() }
            )
         }
         viewModel.onErrorMessage(null, null)
      }
   }
}