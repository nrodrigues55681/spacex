package com.mindera.rocketscience.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.google.accompanist.insets.ui.TopAppBar
import com.mindera.rocketscience.R
import com.mindera.rocketscience.ui.theme.Black
import com.mindera.rocketscience.ui.theme.Purple700
import com.mindera.rocketscience.ui.theme.Teal200

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

@Composable
fun BarWithTitle(title: String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Teal200)) {
        Text(text = title,
            style = MaterialTheme.typography.button,
            color = Black,
            modifier = Modifier.padding(
                vertical = dimensionResource(id = R.dimen.margin_2half),
                horizontal = dimensionResource(id = R.dimen.margin_default)))
    }
}

@Composable
fun LoadingItem(progressColor: Color = Purple700) {
    CircularProgressIndicator(
        color = progressColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_default))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    progressColor: Color = Purple700
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = progressColor)
    }
}

@Composable
fun ErrorView(
    message: String,
    buttonTitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_default))
    ) {
        Button(onClick = onClick) {
            Text(text = buttonTitle)
        }
        Text(
            text = message,
            maxLines = 1,
            color = Black,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun Text2Style(
    modifier: Modifier = Modifier,
    title: String,
    description: String) {
    Row(modifier = modifier) {
       Text(text = title,
           style = MaterialTheme.typography.subtitle2,
           color = Purple700)

       Text(text = description,
           style = MaterialTheme.typography.subtitle2,
           color = Black)
    }
}
