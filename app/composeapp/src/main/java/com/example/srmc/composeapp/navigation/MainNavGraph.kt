package com.example.srmc.composeapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.srmc.composeapp.component.bottombar.BottomBarHomeItem
import com.example.srmc.composeapp.ui.screens.detail.AboutAppScreen
import com.example.srmc.composeapp.ui.screens.detail.AppointmentDetailScreen
import com.example.srmc.composeapp.ui.screens.detail.DoctorDetailScreen
import com.example.srmc.composeapp.ui.screens.form.DoctorSchedScreen
import com.example.srmc.composeapp.ui.screens.form.DoctorSlotsScreen
import com.example.srmc.composeapp.ui.screens.form.ReschedDateScreen
import com.example.srmc.composeapp.ui.screens.form.ReschedSlotScreen
import com.example.srmc.composeapp.ui.screens.home.AppointmentScreen
import com.example.srmc.composeapp.ui.screens.home.HomeScreen
import com.example.srmc.composeapp.ui.screens.home.ProfileScreen
import com.example.srmc.composeapp.utils.assistedViewModel
import com.example.srmc.view.viewmodel.detail.AppointmentDetailViewModel
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.detail.ScheduleDetailViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel
import com.example.srmc.view.viewmodel.form.SlotsViewModel

@Composable
fun MainNavGraph(navController : NavHostController)
{
    NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = BottomBarHomeItem.Doctors.route)
    {
        composable(route = BottomBarHomeItem.Doctors.route)
        {
            HomeScreen(viewModel = hiltViewModel(),
                onNavigateToDoctorDetail = { navController.navigateToDoctorDetail(it) })
        }

        composable(route = BottomBarHomeItem.Appointments.route)
        {
            AppointmentScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToAppointmentDetail = { navController.navigateToAppointmentDetail(it) })
        }

        composable(route = BottomBarHomeItem.Profile.route)
        {
            ProfileScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Graph.AUTHENTICATION)
                    },
                    onAboutAppClick = { navController.navigateToAboutApp() })
        }

        composable(DetailScreen.Doctor.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType}))
        {
            val doctorId = requireNotNull(it.arguments?.getInt(DetailScreen.Doctor.ARG_DOCTOR_ID))
            DoctorDetailScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel =  assistedViewModel {
                        DoctorDetailViewModel.provideFactory(doctorViewModelFactory(), doctorId)
                    },
                    onNavigateToForm = { it1 -> navController.navigateToAppointmentForm(it1) })
        }

        composable(DetailScreen.AppointmentDetail.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType}))
        {
            val appointmentId = requireNotNull(it.arguments?.getInt(DetailScreen.AppointmentDetail.ARG_APPTDETAIL_ID))

            AppointmentDetailScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel =  assistedViewModel {
                        AppointmentDetailViewModel.provideFactory(appointmentDetailViewModelFactory(), appointmentId)
                    },
                    cancelViewModel = hiltViewModel(),
                    onNavigateToForm = { it1, it2 -> navController.navigateToReschedForm(it1, it2)})
        }

        composable(FormScreen.Appointment.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType}))
        {
            val doctorId = requireNotNull(it.arguments?.getInt(FormScreen.Appointment.ARG_APPOINTMENT_ID))

            DoctorSchedScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel1 =  assistedViewModel {
                        DoctorDetailViewModel.provideFactory(doctorViewModelFactory(), doctorId)
                    },
                    viewModel2 =  assistedViewModel {
                        SchedulesViewModel.provideFactory(scheduleViewModelFactory(), doctorId)
                    },
                    viewModel3 =  hiltViewModel(),
                    onNavigateToSlots = { it1, it2 -> navController.navigateToAppointmentSlots(it1, it2) })
        }

        composable(FormScreen.AppointmentSlots.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType} ,
                                      navArgument("date"){type = NavType.StringType}))
        {
            val doctorId = requireNotNull(it.arguments?.getInt(FormScreen.AppointmentSlots.ARG_APPOINTMENTSLOT_ID))
            val schedDate = requireNotNull(it.arguments?.getString(FormScreen.AppointmentSlots.ARG_APPOINTMENTSLOT_DATE))
            DoctorSlotsScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel1 =  assistedViewModel {
                        DoctorDetailViewModel.provideFactory(doctorViewModelFactory(), doctorId)
                    },
                    viewModel2 =  assistedViewModel {
                        ScheduleDetailViewModel.provideFactory(scheduleDetailViewModelFactory() , doctorId, schedDate)
                    },
                    viewModel3 =  assistedViewModel {
                        SlotsViewModel.provideFactory(slotsViewModelFactory(), doctorId, schedDate)
                    },
                    viewModel4 = hiltViewModel())
        }

        composable(FormScreen.ReschedDate.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType},
                                      navArgument("doctorId"){type = NavType.IntType}))
        {
            val appointmentId = requireNotNull(it.arguments?.getInt(FormScreen.ReschedDate.ARG_RESCHEDULE_ID))
            val doctorId = requireNotNull(it.arguments?.getInt(FormScreen.ReschedDate.ARG_RESCHED_DOCTOR_ID))

            ReschedDateScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel1 =  assistedViewModel {
                        AppointmentDetailViewModel.provideFactory(appointmentDetailViewModelFactory(), appointmentId)
                    },
                    viewModel2 = assistedViewModel {
                        SchedulesViewModel.provideFactory(scheduleViewModelFactory(), doctorId)
                    } ,
                    viewModel3 =  hiltViewModel(),
                    onNavigateToSlots = { it1, it2, it3 -> navController.navigateToReschedSlots(it1, it2, it3) })
        }

        composable(FormScreen.ReschedSlots.route,
                   arguments = listOf(navArgument("id"){type = NavType.IntType},
                                      navArgument("doctorId"){type = NavType.IntType},
                                      navArgument("date"){type = NavType.StringType}))
        {
            val appointmentId = requireNotNull(it.arguments?.getInt(FormScreen.ReschedSlots.ARG_RESCHEDULE_SLOT_ID))
            val doctorId = requireNotNull(it.arguments?.getInt(FormScreen.ReschedSlots.ARG_RESCHEDULE_SLOT_DOCTOR_ID))
            val schedDate = requireNotNull(it.arguments?.getString(FormScreen.ReschedSlots.ARG_RESCHEDULE_SLOT_DATE))

            ReschedSlotScreen(
                    onNavigateUp = { navController.navigateUp() } ,
                    viewModel1 =  assistedViewModel {
                        AppointmentDetailViewModel.provideFactory(appointmentDetailViewModelFactory(), appointmentId)
                    },
                    viewModel2 =  assistedViewModel {
                        ScheduleDetailViewModel.provideFactory(scheduleDetailViewModelFactory(), doctorId, schedDate)
                    },
                    viewModel3 =  assistedViewModel {
                        SlotsViewModel.provideFactory(slotsViewModelFactory(), doctorId, schedDate)
                    },
                    viewModel4 = hiltViewModel())
        }

        composable(DetailScreen.AboutApp.route) { AboutAppScreen(onNavigateUp = { navController.navigateUp() }) }


        authNavGraph(navController)
    }
}

/**
 * Launches detail screen for specified ID
 */
fun NavController.navigateToAboutApp() = navigate(DetailScreen.AboutApp.route)
fun NavController.navigateToDoctorDetail(id : Int) = navigate(DetailScreen.Doctor.route(id))

fun NavController.navigateToAppointmentDetail(id : Int) = navigate(DetailScreen.AppointmentDetail.route(id))

fun NavController.navigateToAppointmentForm(id : Int) = navigate(FormScreen.Appointment.route(id))

fun NavController.navigateToAppointmentSlots(id : Int, date : String) = navigate(FormScreen.AppointmentSlots.route(id, date))

fun NavController.navigateToReschedForm(id : Int, doctorId : Int) = navigate(FormScreen.ReschedDate.route(id, doctorId))

fun NavController.navigateToReschedSlots(id : Int, doctorId : Int, date : String) = navigate(FormScreen.ReschedSlots.route(id, doctorId, date))


/**
 * Sealed class for Detail Screens
 */
sealed class DetailScreen(val route : String, val name : String)
{
    object Doctor : DetailScreen("doctor/{id}", "Doctor Details") {
        fun route(id : Int) = "doctor/$id"
        const val ARG_DOCTOR_ID : String = "id"
    }

    object AppointmentDetail : DetailScreen("appointment/{id}", "Appointment Details") {
        fun route(id : Int) = "appointment/$id"
        const val ARG_APPTDETAIL_ID : String = "id"
    }

    object AboutApp : DetailScreen("about", "About App")

}

/**
 * Sealed class for forms
 */
sealed class FormScreen(val route : String, val name : String)
{
    object Appointment : FormScreen("schedule/{id}", "Appointment Form") {
        fun route(id : Int) = "schedule/$id"

        const val ARG_APPOINTMENT_ID : String = "id"
        const val ARG_APPOINTMENT_DATE : String = "date"
    }

    object AppointmentSlots : FormScreen("schedule/{id}/{date}", "Appointment Slots Form") {
        fun route(id : Int, date : String) = "schedule/$id/$date"

        const val ARG_APPOINTMENTSLOT_ID : String = "id"
        const val ARG_APPOINTMENTSLOT_DATE : String = "date"
     }

    object ReschedDate : FormScreen("reschedule/{id}/{doctorId}", "Reschedule Form") {
        fun route(id : Int, doctorId : Int) = "reschedule/$id/$doctorId"

        const val ARG_RESCHEDULE_ID : String = "id"
        const val ARG_RESCHED_DOCTOR_ID : String = "doctorId"
    }

    object ReschedSlots : FormScreen("reschedule/{id}/{doctorId}/{date}", "Reschedule Slots Form") {
        fun route(id : Int, doctorId : Int, date : String) = "reschedule/$id/$doctorId/$date"

        const val ARG_RESCHEDULE_SLOT_ID : String = "id"
        const val ARG_RESCHEDULE_SLOT_DOCTOR_ID : String = "doctorId"
        const val ARG_RESCHEDULE_SLOT_DATE : String = "date"
    }

}