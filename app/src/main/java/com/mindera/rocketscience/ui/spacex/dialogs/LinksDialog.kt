package com.mindera.rocketscience.ui.spacex.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mindera.rocketscience.R
import com.mindera.rocketscience.ui.components.MyButton
import com.mindera.rocketscience.ui.utils.LinksData

@Composable
fun LinksDialog(linksData: LinksData,
                onDismissClick: () -> Unit,
                onNavigateToLink: (String) -> Unit){

    Dialog(onDismissRequest = onDismissClick) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.margin_default))
        ) {
            Box(
                modifier = Modifier.background(color = Color.White, shape = RoundedCornerShape(10.dp))
            ) {
                Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))) {
                    MyButton(onClick = { onNavigateToLink(linksData.articleLink) },
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(id = R.string.article_link))

                    MyButton(onClick = { onNavigateToLink(linksData.wikipediaLink) },
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(id = R.string.wikipedia))

                    MyButton(onClick = { onNavigateToLink(linksData.videoLink) }, modifier = Modifier.fillMaxWidth(), title = stringResource(
                        id = R.string.video_link))

                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_default)))

                    MyButton(onClick = onDismissClick,
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(id = R.string.cancel))
                }
            }
        }
    }
}