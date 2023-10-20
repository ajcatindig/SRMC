package com.example.srmc.composeapp.ui.screens.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srmc.composeapp.component.ConnectivityStatus
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog
import com.example.srmc.composeapp.component.list.ScheduleList
import com.example.srmc.composeapp.component.scaffold.SRMCScaffold
import com.example.srmc.composeapp.component.scaffold.form.SchedListTopBar
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.form.AppointmentFormViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun DoctorSchedScreen(
        onNavigateUp : () -> Unit,
        viewModel1 : DoctorDetailViewModel,
        viewModel2 : SchedulesViewModel,
        viewModel3 : AppointmentFormViewModel,
        onNavigateToSlots : () -> Unit)
{
    val state1 by viewModel1.collectState()
    val state2 by viewModel2.collectState()
    val state3 by viewModel3.collectState()

    DoctorSchedFormContent(
            isLoading = state2.isLoading ,
            error =  state2.error,
            onNavigateUp = onNavigateUp ,
            onNavigateToSlots = {  },
            doctor =  state1.data,
            selectedDoctor =  state3.doctor_id,
            onSelectedDoctor =  viewModel3::setDoctorId,
            schedule =  state2.data,
            selectedDate =  state3.date,
            onDateChange = viewModel3::setDate)
}

@Composable
fun DoctorSchedFormContent(
        isLoading : Boolean,
        error : String?,
        onNavigateUp : () -> Unit,
        onNavigateToSlots : () -> Unit,
        doctor : Doctor,
        selectedDoctor : Int,
        onSelectedDoctor : (Int) -> Unit,
        schedule : List<Schedules>,
        selectedDate : String,
        onDateChange : (String) -> Unit
)
{
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(error)
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()),
           horizontalAlignment = Alignment.CenterHorizontally)
    {
        BackToDoctor(Modifier.align(Alignment.Start), onBackClick = onNavigateUp)
        TopMessageSchedule()
        DoctorSchedForm(
                onNavigateToSlots =  onNavigateToSlots,
                schedule =  schedule,
                doctor =  doctor,
                selectedDoctor =  selectedDoctor,
                onSelectedDoctor =  onSelectedDoctor,
                selectedDate =  selectedDate,
                onDateChange = onDateChange)
    }
}

@Composable
fun DoctorSchedForm(
        onNavigateToSlots : () -> Unit,
        schedule : List<Schedules>,
        doctor : Doctor,
        selectedDoctor : Int,
        onSelectedDoctor : (Int) -> Unit,
        selectedDate : String,
        onDateChange : (String) -> Unit)
{
    val isValidate by derivedStateOf { selectedDate.isNotBlank() }

    Column(modifier = Modifier
            .fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center)
    {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp , top = 20.dp , bottom = 16.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Doctor Info",
                     style = typography.subtitle1,
                     fontSize = 20.sp,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Dr. ${doctor.name.orEmpty()}",
                     style = typography.caption,
                     fontSize = 18.sp,
                     textAlign = TextAlign.Start)
            }
        }
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp , bottom = 16.dp),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally)
        {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Available Dates",
                     style = typography.subtitle1,
                     fontSize = 18.sp,
                     textAlign = TextAlign.Start)
            }
            schedule.forEach { index ->
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .selectable(selected = (selectedDate == index.date) , onClick = { onDateChange(index.date !!) } , role = Role.Button),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                            selected =  (selectedDate == index.date),
                            onClick = { onDateChange(index.date!!) },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff15C3DD),
                                    unselectedColor = Color(0xff15C3DD).copy(alpha = 0.5f)
                            ))
                    Text(text = "${index.date_label.orEmpty()} (${index.slots} slots left)",
                         style = typography.caption,
                         fontSize = 18.sp,
                         textAlign = TextAlign.Start)
                }
            }
        }
    }
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp , end = 16.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Button(onClick = { onNavigateToSlots() } ,
                   enabled = isValidate ,
                   modifier = Modifier
                           .fillMaxSize()
                           .height(50.dp) ,
                   colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff15C3DD)) ,
                   shape = RoundedCornerShape(25.dp))
            {
                Text(text = "NEXT" , color = Color.White , style = typography.h6)
            }
        }
    }
}

@Composable
fun BackToDoctor(modifier : Modifier , onBackClick : () -> Unit)
{
    IconButton(onClick = onBackClick, modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp, top = 20.dp))
    {
        Icon(
                Icons.Outlined.ArrowBackIos , contentDescription = "" ,
                tint = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun TopMessageSchedule()
{
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) ,
           horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.Center)
    {
        Text(text = "Select a Date" ,
             style = MaterialTheme.typography.h5 ,
             fontSize = 25.sp ,
             textAlign = TextAlign.Center)
    }
}
