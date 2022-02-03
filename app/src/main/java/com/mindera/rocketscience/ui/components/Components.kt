package com.mindera.rocketscience.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.insets.ui.TopAppBar
import com.mindera.rocketscience.R

@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClick: (() -> Unit)? = null) {

    TopAppBar(
        title = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = ShowNavigationIcon(showBackButton, onBackButtonClick)
    )
}

@Composable
fun ShowNavigationIcon(showBackButton: Boolean = false,
                       onBackButtonClick: (() -> Unit)? = null): @Composable() (() -> Unit)? {
    return if(showBackButton) {
        {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.margin_default))
                    .clickable {
                        onBackButtonClick?.let { it() }
                    }
            )
        }
    } else {
        null
    }
}