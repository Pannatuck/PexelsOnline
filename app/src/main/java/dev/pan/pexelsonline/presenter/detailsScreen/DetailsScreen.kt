package dev.pan.pexelsonline.presenter.detailsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.pan.pexelsonline.util.Fonts

@Composable
fun DetailsScreen(
    landscape: String,
    photographer: String,
    photographerUrl: String,
    alt: String
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize(),

    ){
        AsyncImage(
            model = landscape,
            contentDescription = alt,
        )
        Column(
            Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Author",
                    fontWeight = FontWeight.Bold,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = Fonts.ubuntoFamily,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = photographer,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = Fonts.ubuntoFamily,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )

            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Authors page",
                    fontWeight = FontWeight.Bold,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = Fonts.ubuntoFamily,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = photographerUrl,
                    color = Color.Gray,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontFamily = Fonts.ubuntoFamily,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.clickable { uriHandler.openUri(photographerUrl) }
                )
            }

            Text(
                text = alt,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Fonts.ubuntoFamily
            )
        }

    }
}