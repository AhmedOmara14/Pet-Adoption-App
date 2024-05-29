package com.omaradev.pet_adoption.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.ui.home.component.NearByResultListItem
import com.omaradev.pet_adoption.ui.home.viewmodel.HomeViewModel
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorWhite
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    navController: NavHostController,
    boundsTransform: (Rect, Rect) -> TweenSpec<Rect>,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    changeAppTheme: (systemInDarkTheme: Boolean) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val animals = viewModel.animals.collectAsLazyPagingItems()

    sharedTransitionScope.apply {
        Column(
            modifier = Modifier
                .background(if (isDarkTheme) colorBlue else Color.White)
                .fillMaxSize()
        ) {
            Header(isDarkTheme = isDarkTheme, changeAppTheme = changeAppTheme)
            Title(text = "Adopt a new friend near you!", isDarkTheme = isDarkTheme)
            Title(text = "Nearby Results", isDarkTheme = isDarkTheme, topPadding = 28.dp)

            when (animals.loadState.refresh) {
                LoadState.Loading -> {
                    // Show a loading indicator
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    Log.d("TAG", "HomeScreen: error")
                    // Show an error message
                    Text(
                        text = "Failed to load data",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is LoadState.NotLoading -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(animals.itemCount) { index ->
                            animals[index]?.let {
                                it.id?.let { it1 ->
                                    NearByResultListItem(
                                        index = it1,
                                        isDarkTheme = isDarkTheme,
                                        boundsTransform = boundsTransform,
                                        animatedContentScope = animatedContentScope,
                                        sharedTransitionScope = sharedTransitionScope,
                                        animal = it
                                    ) {petId->
                                        navController.navigate("details/${petId}")
                                    }
                                }
                            }
                        }

                        // Handle loading more state
                        when (animals.loadState.append) {
                            LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }

                            is LoadState.Error -> {
                                item {
                                    Text(
                                        text = "Failed to load more data",
                                        color = Color.Red,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(isDarkTheme: Boolean, changeAppTheme: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Hello Dev,",
            modifier = Modifier.weight(1f),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.cairobold)),
                fontSize = 20.sp,
                color = if (isDarkTheme) colorWhite else colorBlue
            )
        )

        Image(
            painter = painterResource(if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode),
            contentDescription = if (isDarkTheme) "Dark Mode" else "Light Mode",
            alignment = Alignment.CenterEnd,
            modifier = Modifier
                .clickable {
                    changeAppTheme(!isDarkTheme)
                }
                .padding(end = 8.dp)
        )
    }
}

@Composable
fun Title(text: String, isDarkTheme: Boolean, topPadding: Dp = 0.dp) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp, top = topPadding),
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.cairosemibold)),
            fontSize = 18.sp,
            color = if (isDarkTheme) colorWhite else colorBlue
        )
    )
}
