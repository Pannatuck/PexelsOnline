@file:OptIn(ExperimentalMaterial3Api::class)

package dev.pan.pexelsonline.presenter.homeScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.pan.pexelsonline.BuildConfig
import dev.pan.pexelsonline.R
import dev.pan.pexelsonline.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.homeState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(HomeEvents.GetPhotos(1, 30))
    }

    // TODO: pass just navigation field
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { route ->
            navController.navigate(route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_icon),
                            contentDescription = "app icon",
                            Modifier.size(64.dp)
                        )
                    }
                },
                modifier = modifier.height(64.dp)
            )
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            contentPadding = innerPadding,
            modifier = modifier
                .fillMaxSize()
        ) {
            // TODO: add circular loading while images are loading add scrollable remember
            items(state.value.photos?.size ?: 0) { index ->
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.value.photos?.get(index)?.src?.original)
                            .addHeader("Authorization", BuildConfig.API_KEY)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)// it's the same even removing comments
                            .build(),
                        contentDescription = "image in list",
                        modifier = modifier.clickable {
                            viewModel.onEvent(HomeEvents.PhotoClicked
                                (state.value.photos?.get(index)?.id ?: 0)
                            )
                        }
                    )
                }
                if (state.value.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}