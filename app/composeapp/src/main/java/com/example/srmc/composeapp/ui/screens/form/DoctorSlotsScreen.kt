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
import com.example.srmc.composeapp.component.dialog.SuccessDialog
import com.example.srmc.composeapp.ui.theme.typography
import com.example.srmc.composeapp.utils.collectState
import com.example.srmc.core.model.ConsultationType
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Slots
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.detail.ScheduleDetailViewModel
import com.example.srmc.view.viewmodel.form.AppointmentFormViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel
import com.example.srmc.view.viewmodel.form.SlotsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DoctorSlotsScreen(
        onNavigateUp : () -> Unit ,
        viewModel1 : DoctorDetailViewModel ,
        viewModel2 : ScheduleDetailViewModel ,
        viewModel3 : SlotsViewModel,
        viewModel4 : AppointmentFormViewModel)
{
    val state1 by viewModel1.collectState()
    val state2 by viewModel2.collectState()
    val state3 by viewModel3.collectState()
    val state4 by viewModel4.collectState()

    val types = listOf(
            ConsultationType(id = 1, displayName = "In Person") ,
            ConsultationType(id = 2, displayName = "Teleconsultation")
                      )

    DoctorSlotsContent(
            isLoading =  state4.isLoading,
            error =  state4.error,
            isSuccess =  state4.isSuccess,
            onSubmitClick =  viewModel4::postAppointment,
            doctor =  state1.data,
            selectedDoctor =  state4.doctor_id,
            onDoctorSelected =  viewModel4::setDoctorId,
            slots =  state3.data,
            selectedSlot =  state4.time,
            onSelectedSlot = viewModel4::setTime,
            onNavigateUp = onNavigateUp,
            schedule = state2.data,
            selectedSched = state4.date,
            onSelectedSched = viewModel4::setDate,
            type = types,
            selectedType = state4.type,
            onSelectedType = viewModel4::setType)
}

@Composable
fun DoctorSlotsContent(
        isLoading : Boolean ,
        error : String? ,
        isSuccess : String? ,
        onSubmitClick : (Int, String) -> Unit ,
        doctor : Doctor ,
        selectedDoctor : Int ,
        onDoctorSelected : (Int) -> Unit ,
        slots : List<Slots> ,
        selectedSlot : String ,
        onSelectedSlot : (String) -> Unit ,
        onNavigateUp : () -> Unit ,
        schedule : Schedules ,
        selectedSched : String ,
        onSelectedSched : (String) -> Unit ,
        type : List<ConsultationType> ,
        selectedType : Int ,
        onSelectedType : (Int) -> Unit)
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

        TopMessageSlots()
        Column(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp))
        {
            DoctorSlotsForm(
                    onSubmitClick = onSubmitClick ,
                    doctor =  doctor,
                    selectedDoctor = selectedDoctor ,
                    onDoctorSelected = onDoctorSelected ,
                    slots = slots ,
                    selectedSlot = selectedSlot ,
                    onSelectedSlot = onSelectedSlot,
                    selectedSched = selectedSched,
                    schedule = schedule,
                    onSelectedSched = onSelectedSched,
                    type = type,
                    selectedType = selectedType,
                    onSelectedType = onSelectedType)
        }
    }
}

@Composable
fun DoctorSlotsForm(
        onSubmitClick : (Int, String) -> Unit,
        doctor : Doctor,
        selectedDoctor : Int,
        onDoctorSelected : (Int) -> Unit,
        slots : List<Slots>,
        selectedSlot : String,
        onSelectedSlot : (String) -> Unit,
        schedule : Schedules,
        selectedSched : String,
        onSelectedSched : (String) -> Unit,
        type : List<ConsultationType>,
        selectedType : Int,
        onSelectedType : (Int) -> Unit)
{



    val isValidate by derivedStateOf { selectedSlot.isNotEmpty() && selectedType != 0 }

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
                Text(text = "Doctor Info" ,
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
                Text(text = "Dr. ${doctor.name.orEmpty()}" ,
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
                Text(text = doctor.email ?: "No Email Provided",
                     style = typography.caption,
                     fontSize = 16.sp,
                     textAlign = TextAlign.Start)
            }
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = doctor.contact_number ?: "No Number Provided",
                     style = typography.caption,
                     fontSize = 16.sp,
                     textAlign = TextAlign.Start)
            }
        }
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp , end = 16.dp , bottom = 16.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center)
        {
            Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "Selected Schedule" ,
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
                Text(text = "${schedule.date_label} (${schedule.date})" ,
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
                Text(text = "Select Consultation Type" ,
                     style = typography.h5 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            type.forEach { consultType ->
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .selectable(selected = (selectedType == consultType.id) , onClick = { onSelectedType(consultType.id) } , role = Role.Button) ,
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                            selected =  (selectedType == consultType.id),
                            onClick = { onSelectedType(consultType.id) },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff15C3DD) ,
                                    unselectedColor = Color(0xff15C3DD).copy(alpha = 0.5f)
                                                               ))
                    Text(text = consultType.displayName ,
                         style = typography.caption ,
                         fontSize = 16.sp ,
                         textAlign = TextAlign.Start)
                }
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
                     style = typography.h5 ,
                     fontSize = 18.sp ,
                     textAlign = TextAlign.Start)
            }
            slots.forEach { index ->
                val timeFormatter24 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

// Parse the time strings from the API
                val startTime = if (!index.start_time.isNullOrEmpty()) timeFormatter24.parse(index.start_time) else null

// Check if parsing was successful before formatting
                val timeFormatter12 = SimpleDateFormat("h:mm a", Locale.getDefault())
                val start = startTime?.let { timeFormatter12.format(it) }


                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 0.dp)
                        .selectable(selected = (selectedSlot == "${index.start_time}") ,
                                    onClick = { index.start_time?.let { onSelectedSlot(it) } } ,
                                    role = Role.Button) ,
                    horizontalArrangement = Arrangement.Start ,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                            selected =  (selectedSlot == "${index.start_time}"),
                            onClick = { index.start_time?.let { onSelectedSlot(it) } },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff15C3DD) ,
                                    unselectedColor = Color(0xff15C3DD).copy(alpha = 0.5f)
                                                               ))
                    Text(text = "$start" ,
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
                Button(onClick = { onSubmitClick(doctor.id!!, schedule.date!!) } ,
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
fun BackToSchedules(modifier : Modifier , onBackClick : () -> Unit)
{
    IconButton(onClick = onBackClick, modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp, top = 20.dp))
    {
        Icon(
                Icons.Outlined.ArrowBackIos , contentDescription = "" ,
                tint = MaterialTheme.colors.onPrimary)
    }
}

@Composable
fun TopMessageSlots()
{
    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) ,
           horizontalAlignment = Alignment.CenterHorizontally ,
           verticalArrangement = Arrangement.Center)
    {
        Text(text = "Select Consultation Type and Time" ,
             style = MaterialTheme.typography.h5 ,
             fontSize = 22.sp ,
             textAlign = TextAlign.Center)
    }
}