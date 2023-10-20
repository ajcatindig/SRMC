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
import com.example.srmc.composeapp.ui.screens.detail.DoctorDetailScreen
import com.example.srmc.composeapp.ui.screens.form.DoctorSchedScreen
import com.example.srmc.composeapp.ui.screens.home.AppointmentScreen
import com.example.srmc.composeapp.ui.screens.home.HomeScreen
import com.example.srmc.composeapp.ui.screens.home.ProfileScreen
import com.example.srmc.composeapp.ui.screens.home.TransactionScreen
import com.example.srmc.composeapp.utils.assistedViewModel
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import com.example.srmc.view.viewmodel.form.SchedulesViewModel

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
            AppointmentScreen(viewModel = hiltViewModel())
        }

        composable(route = BottomBarHomeItem.Transactions.route)
        {
            TransactionScreen(viewModel = hiltViewModel())
        }

        composable(route = BottomBarHomeItem.Profile.route)
        {
            ProfileScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToLogin = {
                        navController.popBackStack()
                        navController.navigate(Graph.AUTHENTICATION)
                    },
                    onAboutAppClick = {})
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
                    onNavigateToSlots = {})
        }


        authNavGraph(navController)
    }
}

/**
 * Launches detail screen for specified ID
 */
fun NavController.navigateToDoctorDetail(id : Int) = navigate(DetailScreen.Doctor.route(id))

fun NavController.navigateToAppointmentForm(id : Int) = navigate(FormScreen.Appointment.route(id))

/**
 * Sealed class for Detail Screens
 */
sealed class DetailScreen(val route : String, val name : String)
{
    object Doctor : DetailScreen("doctor/{id}", "Doctor Details") {
        fun route(id : Int) = "doctor/$id"
        const val ARG_DOCTOR_ID : String = "id"
    }

}

/**
 * Sealed class for forms
 */
sealed class FormScreen(val route : String, val name : String)
{
    object Appointment : FormScreen("appointment/{id}", "Appointment Form") {
        fun route(id : Int) = "appointment/$id"

        const val ARG_APPOINTMENT_ID : String = "id"
    }
}