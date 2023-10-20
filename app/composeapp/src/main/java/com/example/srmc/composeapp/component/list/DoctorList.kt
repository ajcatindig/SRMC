package com.example.srmc.composeapp.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.srmc.composeapp.component.cards.DoctorCard
import com.example.srmc.core.model.Doctor

@Composable
fun DoctorList(data : List<Doctor>, onClick : (Doctor) -> Unit)
{
    LazyVerticalGrid(
            columns = GridCells.Fixed(2) ,
            contentPadding = PaddingValues(horizontal = 8.dp))
    {
        items(
                items = data,
                itemContent = { index ->
                    DoctorCard(
                            imageUrl = index.profile_photo_path ,
                            doctorName = index.name.orEmpty(),
                            doctorTitle = index.title,
                            onDoctorClick = { onClick(index) }
                    )
                },
                key = { Triple(it.id, it.name, it.title) }
             )
    }
}