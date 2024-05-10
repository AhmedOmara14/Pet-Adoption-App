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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.omaradev.pet_adoption.BuildConfig
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.main.viewmodel.MainViewModel
import com.omaradev.pet_adoption.ui.Pet
import com.omaradev.pet_adoption.ui.home.component.NearByResultListItem
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorWhite
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


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
    val viewModel: MainViewModel = koinViewModel()
    var isLoadingItems by remember { mutableStateOf(false) }
    val requestTokenResponseItemsState by viewModel.requestTokenResponseItems.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val isDataLoaded = remember { mutableStateOf(false) }

    // Load data only if it hasn't been loaded yet
    if (!isDataLoaded.value) {
        LaunchedEffect(key1 = viewModel) {
            when (val response = requestTokenResponseItemsState) {
                is RemoteRequestStatus.ToggleLoading -> isLoadingItems = response.showLoading

                is RemoteRequestStatus.OnSuccessRequest -> {
                    isLoadingItems = false
                    viewModel.requestTokenResponse.value = response.responseBody
                    // Store token securely here (e.g., SharedPreferences)
                    response.responseBody.access_token?.let {
                        viewModel.saveTokenToLocalPrefs(it, context)
                    }
                }

                is RemoteRequestStatus.OnFailedRequest -> {
                    val exception =
                        (response as RemoteRequestStatus.OnFailedRequest<*>).errorMessage
                    Log.d("TAG", "HomeScreen: $exception")
                }

                else -> {}
            }

            // Mark data as loaded
            isDataLoaded.value = true
        }
    }

    sharedTransitionScope.apply {
        Column(
            modifier = Modifier
                .background(if (isDarkTheme) colorBlue else Color.White)
                .fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Hello Omara,",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.cairobold)),
                        fontSize = 20.sp,
                        color = (if (isDarkTheme) colorWhite else colorBlue)
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

            Text(
                text = "Adopt a new friend near you!",
                modifier = Modifier.padding(start = 8.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                    fontSize = 18.sp,
                    color = (if (isDarkTheme) colorWhite else colorBlue)
                )
            )

            Text(
                text = "Nearby Results",
                modifier = Modifier.padding(top = 28.dp, start = 8.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                    fontSize = 16.sp,
                    color = (if (isDarkTheme) colorWhite else colorBlue)
                )
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(pets) { index, item ->
                    NearByResultListItem(
                        index = index,
                        isDarkTheme = isDarkTheme,
                        boundsTransform = boundsTransform,
                        animatedContentScope = animatedContentScope,
                        sharedTransitionScope = sharedTransitionScope
                    ) {
                        navController.navigate("details/$index")
                    }
                }
            }
        }
    }
}


