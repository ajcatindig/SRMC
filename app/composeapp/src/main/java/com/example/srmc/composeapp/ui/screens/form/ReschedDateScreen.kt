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
import com.example.srmc.composeapp.component.dialog.FailureDialog
import com.example.srmc.composeapp.component.dialog.LoaderDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Appointments
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.view.viewmodel.detail.AppointmentDetailViewModel
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.form.ReschedViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel

@Composable
fun ReschedDateScreen(
        onNavigateUp : () -> Unit ,
        viewModel1 : AppointmentDetailViewModel ,
        viewModel2 : SchedulesViewModel ,
        viewModel3 : ReschedViewModel,
        onNavigateToSlots : (Int, Int, String) -> Unit)
{
    val state1 by viewModel1.collectState()
    val state2 by viewModel2.collectState()
    val state3 by viewModel3.collectState()

    ReschedFormContent(
            isLoading =  state2.isLoading,
            error =  state2.error,
            onNavigateUp = onNavigateUp ,
            onNavigateToSlots =  onNavigateToSlots,
            appointment =  state1.data,
            selectedAppointment =  state3.id,
            onSelectedAppointment =  viewModel3::setId,
            schedule =  state2.data,
            selectedDate =  state3.date,
            onSelectedDate = viewModel3::setSchedDate,
            doctor = state1.data.doctor)
}

@Composable
fun ReschedFormContent(
        isLoading : Boolean ,
        error : String? ,
        onNavigateUp : () -> Unit ,
        onNavigateToSlots : (Int, Int, String) -> Unit ,
        appointment : Appointments,
        selectedAppointment : Int,
        onSelectedAppointment : (Int) -> Unit,
        schedule : List<Schedules>,
        selectedDate : String,
        onSelectedDate : (String) -> Unit,
        doctor : Doctor)
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
        BackToAppointment(Modifier.align(Alignment.Start), onBackClick = onNavigateUp)
        TopMessageResched()
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))
        {
            ReschedForm(
                    onNavigateToSlots =  onNavigateToSlots,
                    schedule =  schedule,
                    appointment =  appointment,
                    selectedAppointment =  selectedAppointment,
                    onSelectedAppointment =  onSelectedAppointment,
                    selectedDate =  selectedDate,
                    onSelectedDate = onSelectedDate,
                    doctor = doctor)
        }
    }

}


@Composable
fun ReschedForm(
        onNavigateToSlots : (Int, Int, String) -> Unit ,
        schedule : List<Schedules> ,
        appointment : Appointments ,
        selectedAppointment : Int ,
        onSelectedAppointment : (Int) -> Unit ,
        selectedDate : String ,
        onSelectedDate : (String) -> Unit,
        doctor : Doctor)
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
                Text(text = "Appointment Info" ,
                     style = typography.h5 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Dr. ${appointment.doctor.name.orEmpty()}" ,
                     style = typography.caption ,
                     fontSize = 16.sp ,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Date: ${appointment.scheduled_at}",
                     style = typography.caption ,
                     fontSize = 16.sp ,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Time: ${appointment.start_time} - ${appointment.end_time}",
                     style = typography.caption ,
                     fontSize = 16.sp ,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Consultation Type: ${appointment.type}",
                     style = typography.caption ,
                     fontSize = 16.sp ,
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
                Text(text = "Available Dates" ,
                     style = typography.h5 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            schedule.forEach { index ->
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .selectable(selected = (selectedDate == index.date) , onClick = { onSelectedDate(index.date !!) } , role = Role.Button) ,
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                            selected =  (selectedDate == index.date),
                            onClick = { onSelectedDate(index.date!!) },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff15C3DD) ,
                                    unselectedColor = Color(0xff15C3DD).copy(alpha = 0.5f)
                                                               ))
                    Text(text = "${index.date_label.orEmpty()} (${index.slots} slots) " ,
                         style = typography.caption ,
                         fontSize = 16.sp ,
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
            Button(onClick = { onNavigateToSlots(appointment.id!!, appointment.doctor.id!!, selectedDate) } ,
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
fun BackToAppointment(modifier : Modifier , onBackClick : () -> Unit)
{
    IconButton(onClick = onBackClick, modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp, top = 20.dp))
    {
        Icon(
                Icons.Outlined.ArrowBackIos , contentDescription = "" ,
                tint = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun TopMessageResched()
{
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) ,
           horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.Center)
    {
        Text(text = "Select a New Date" ,
             style = MaterialTheme.typography.h5 ,
             fontSize = 25.sp ,
             textAlign = TextAlign.Center)
    }
}