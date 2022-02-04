package com.mindera.rocketscience.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.insets.ui.TopAppBar
import com.mindera.rocketscience.R
import com.mindera.rocketscience.ui.theme.Black
import com.mindera.rocketscience.ui.theme.Purple700
import com.mindera.rocketscience.ui.theme.Shapes
import com.mindera.rocketscience.ui.theme.Teal200

@Composable
fun TopBar(
    title: String,
    onFilterClick: (() -> Unit)) {

    TopAppBar(
        title = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        actions = {
            IconButton(onClick = { onFilterClick() }) {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                    contentDescription = null
                )
            }
        }
    )
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

@Composable
fun<T> GroupRadioButton(modifier: Modifier,
                     title: String,
                     itemSelected: T,
                     items: List<T>,
                     onRadioButtonClick: ((Int) -> Unit)){
    if(items.isNotEmpty()){
        Column(modifier = modifier) {
            Text(text = title, color = Purple700, style = MaterialTheme.typography.body1)
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                items.forEachIndexed { index, element ->
                    RadioButton(selected = itemSelected == element,
                        onClick = { onRadioButtonClick(index) })
                    Text(text = element.toString(),
                        style = MaterialTheme.typography.caption,
                        color = Black)
                }
            }
        }
    }
}

@Composable
fun OkCancelButton(modifier: Modifier,
                   okTitle: String,
                   cancelTitle: String,
                   onOkClick: (() -> Unit),
                   onCancelClick: (() -> Unit)){
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.End) {
        Button(
            onClick = onCancelClick,
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple700),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_2half)))
        ) { Text(text = cancelTitle, color = Color.White) }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_half)))
        Button(
            onClick = onOkClick,
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple700),
            modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_2half)))
        ) { Text(text = okTitle, color = Color.White) }
    }
}
