package com.omaradev.pet_adoption.ui.home

import android.content.Context
import android.preference.PreferenceManager
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.omaradev.pet_adoption.BuildConfig
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.ui.main.viewmodel.MainViewModel
import com.omaradev.pet_adoption.ui.Pet
import com.omaradev.pet_adoption.ui.home.component.NearByResultListItem
import com.omaradev.pet_adoption.ui.home.viewmodel.HomeViewModel
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorWhite
import io.ktor.util.valuesOf
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.math.log


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    pets: List<Pet>,
    navController: NavHostController,
    boundsTransform: (Rect, Rect) -> TweenSpec<Rect>,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    changeAppTheme: (systemInDarkTheme: Boolean) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    var isLoadingItems by remember { mutableStateOf(false) }
    var animalsList by remember { mutableStateOf(emptyList<Animal>()) }

    val animals = viewModel.animals.collectAsLazyPagingItems()
    val animalsState = viewModel.animalsItems.collectAsState()

    /*  when (val newsItems = animalsState.value) {
          is RemoteRequestStatus.ToggleLoading -> isLoadingItems = newsItems.showLoading

          is RemoteRequestStatus.OnSuccessRequest -> {
              isLoadingItems = false
              animalsList = newsItems.responseBody.animals ?: emptyList()
          }

          is RemoteRequestStatus.OnFailedRequest -> {
              val exception = (newsItems as RemoteRequestStatus.OnFailedRequest<*>).errorMessage
              Log.e("TAG_ERROR", "Get Animal Error: $exception")
          }

          else -> {}
      }*/

    sharedTransitionScope.apply {
        Column(
            modifier = Modifier
                .background(if (isDarkTheme) colorBlue else Color.White)
                .fillMaxSize()
        ) {
            Header(isDarkTheme = isDarkTheme, changeAppTheme = changeAppTheme)
            Title(text = "Adopt a new friend near you!", isDarkTheme = isDarkTheme)
            Title(text = "Nearby Results", isDarkTheme = isDarkTheme, topPadding = 28.dp)

            /*LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(animalsList) { index, animal ->
                    NearByResultListItem(
                        index = index,
                        isDarkTheme = isDarkTheme,
                        boundsTransform = boundsTransform,
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope,
                        animal = animal
                    ) {
                        navController.navigate("details/$index")
                    }
                }
            }*/

            when (animals.loadState.refresh) {
                LoadState.Loading -> {
                    Log.d("TAG", "HomeScreen: loading")
                }

                is LoadState.Error -> {
                    Log.d("TAG", "HomeScreen: error")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(animals.itemCount) { index ->
                            animals[index]?.let {
                                NearByResultListItem(
                                    index = index,
                                    isDarkTheme = isDarkTheme,
                                    boundsTransform = boundsTransform,
                                    animatedContentScope = animatedContentScope,
                                    sharedTransitionScope = sharedTransitionScope,
                                    animal = it
                                ) {
                                    navController.navigate("details/${animals[index]?.id}")
                                }
                            }
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