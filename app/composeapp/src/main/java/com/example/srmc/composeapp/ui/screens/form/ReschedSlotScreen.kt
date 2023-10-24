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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
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
import com.example.srmc.composeapp.component.dialog.SuccessDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.Appointments
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Slots
import com.example.srmc.view.viewmodel.detail.AppointmentDetailViewModel
import com.example.srmc.view.viewmodel.detail.ScheduleDetailViewModel
import com.example.srmc.view.viewmodel.form.ReschedViewModel
import com.example.srmc.view.viewmodel.form.SlotsViewModel

@Composable
fun ReschedSlotScreen(
        onNavigateUp : () -> Unit,
        viewModel1 : AppointmentDetailViewModel,
        viewModel2 : ScheduleDetailViewModel,
        viewModel3 : SlotsViewModel,
        viewModel4 : ReschedViewModel)
{
    val state1 by viewModel1.collectState()
    val state2 by viewModel2.collectState()
    val state3 by viewModel3.collectState()
    val state4 by viewModel4.collectState()

    ReschedSlotsContent(
            isLoading =  state4.isLoading,
            error =  state4.error,
            isSuccess =  state4.isSuccess,
            onSubmitClick =  viewModel4::reschedAppointment,
            doctor =  state1.data.doctor,
            slots =  state3.data,
            selectedSlot =  state4.time,
            onSelectedSlot =  viewModel4::setSchedTime,
            onNavigateUp = onNavigateUp ,
            schedules =  state2.data,
            selectedSched =  state4.date,
            onSelectedSched =  viewModel4::setSchedDate,
            appointment =  state1.data,
            selectedAppointment =  state4.id,
            onSelectedAppointment = viewModel4::setId)
}

@Composable
fun ReschedSlotsContent(
        isLoading : Boolean ,
        error : String? ,
        isSuccess : String? ,
        onSubmitClick : (Int, String) -> Unit ,
        doctor : Doctor ,
        slots : List<Slots>,
        selectedSlot : String,
        onSelectedSlot : (String) -> Unit,
        onNavigateUp : () -> Unit,
        schedules : Schedules,
        selectedSched : String,
        onSelectedSched : (String) -> Unit,
        appointment : Appointments,
        selectedAppointment : Int,
        onSelectedAppointment : (Int) -> Unit)
{
    if (isLoading) {
        LoaderDialog()
    }

    if (error != null) {
        FailureDialog(error)
    }

    if (isSuccess != null) {
        SuccessDialog(isSuccess)
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState()))
    {
        BackToSchedules(Modifier.align(Alignment.Start), onBackClick = onNavigateUp)
        TopMessageReschedSlots()
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))
        {
            ReschedSlotsForm(
                    onSubmitClick = onSubmitClick ,
                    doctor =  doctor,
                    slots =  slots,
                    selectedSlot =  selectedSlot,
                    onSelectedSlot =  onSelectedSlot,
                    schedule =  schedules,
                    selectedSched =  selectedSched,
                    onSelectedSched =  onSelectedSched,
                    appointment =  appointment,
                    selectedAppointment =  selectedAppointment,
                    onSelectedAppointment = onSelectedAppointment)
        }
    }
}

@Composable
fun ReschedSlotsForm(
        onSubmitClick : (Int, String) -> Unit,
        doctor : Doctor,
        slots : List<Slots>,
        selectedSlot : String,
        onSelectedSlot : (String) -> Unit,
        schedule : Schedules,
        selectedSched : String,
        onSelectedSched : (String) -> Unit,
        appointment : Appointments,
        selectedAppointment : Int,
        onSelectedAppointment : (Int) -> Unit)
{
    val isValidate by derivedStateOf { selectedSlot.isNotEmpty() }

    Column(modifier = Modifier
            .fillMaxSize() ,
           horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.Center)
    {
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp , top = 20.dp , bottom = 16.dp) ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) ,
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Appointment Info" ,
                     style = typography.subtitle1 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) ,
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Dr. ${doctor.name.orEmpty()}" ,
                     style = typography.caption ,
                     fontSize = 16.sp , textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) ,
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "New Date Selected: ${schedule.date_label} (${schedule.date})" ,
                     style = typography.caption ,
                     fontSize = 16.sp , textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp) ,
                horizontalArrangement = Arrangement.Start ,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Consultation Type: ${appointment.type}" ,
                     style = typography.caption ,
                     fontSize = 16.sp , textAlign = TextAlign.Start)
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
                Text(text = "Available Slots" ,
                     style = typography.subtitle1 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            slots.forEach { index ->
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .selectable(selected = (selectedSlot == "${index.start_time} - ${index.start_time}") ,
                                    onClick = { onSelectedSlot("${index.start_time} - ${index.end_time}") } ,
                                    role = Role.Button) ,
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                            selected =  (selectedSlot == "${index.start_time} - ${index.end_time}"),
                            onClick = { onSelectedSlot("${index.start_time} - ${index.end_time}") },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff15C3DD) ,
                                    unselectedColor = Color(0xff15C3DD).copy(alpha = 0.5f)))
                    Text(text = "${index.start_time.orEmpty()} - ${index.end_time.orEmpty()}" ,
                         style = typography.caption ,
                         fontSize = 16.sp ,
                         textAlign = TextAlign.Start)
                }
            }
        }
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp, bottom = 40.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Button(onClick = { onSubmitClick(appointment.id!!, schedule.date!!) } ,
                       enabled = isValidate ,
                       modifier = Modifier
                               .fillMaxSize()
                               .height(50.dp) ,
                       colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff15C3DD)) ,
                       shape = RoundedCornerShape(25.dp))
                {
                    Text(text = "SUBMIT REQUEST" , color = Color.White , style = typography.h6)
                }
            }
        }
    }
}

@Composable
fun TopMessageReschedSlots()
{
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) ,
           horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.Center)
    {
        Text(text = "Select a New Time" ,
             style = MaterialTheme.typography.h5 ,
             fontSize = 22.sp ,
             textAlign = TextAlign.Center)
    }
}