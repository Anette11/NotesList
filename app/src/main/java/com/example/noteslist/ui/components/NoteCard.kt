package com.example.noteslist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.noteslist.R

@Composable
fun NoteCard(
    title: String,
    content: String,
    onClick: () -> Unit
) = Card(
    modifier = Modifier
        .padding(
            horizontal = dimensionResource(id = R.dimen._8dp),
            vertical = dimensionResource(id = R.dimen._4dp)
        )
        .fillMaxWidth()
        .clickable { onClick() },
    shape = RoundedCornerShape(dimensionResource(id = R.dimen._4dp)),
    elevation = dimensionResource(id = R.dimen._4dp)
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen._8dp))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = content,
            style = MaterialTheme.typography.body1,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}