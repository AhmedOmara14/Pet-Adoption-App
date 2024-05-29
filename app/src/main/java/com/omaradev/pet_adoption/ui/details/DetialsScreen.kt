package com.omaradev.pet_adoption.ui.details

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.ui.details.viewmodel.DetailsViewModel
import com.omaradev.pet_adoption.ui.details.viewmodel.ShimmerDetailsScreen
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorBlueLight
import com.omaradev.pet_adoption.ui.theme.colorGrayLight2_X
import com.omaradev.pet_adoption.ui.theme.colorWhite
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun DetailsScreen(
    systemInDarkTheme: Boolean,
    petId: Int,
    navController: NavHostController,
    boundsTransform: (androidx.compose.ui.geometry.Rect, androidx.compose.ui.geometry.Rect) -> TweenSpec<androidx.compose.ui.geometry.Rect>,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val viewModel: DetailsViewModel = koinViewModel()

    var isLoadingItem by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(petId) {
        viewModel.getAnimalDetails(petId).collectLatest { state ->
            when (state) {
                is RemoteRequestStatus.ToggleLoading -> isLoadingItem = state.showLoading
                is RemoteRequestStatus.OnSuccessRequest -> {
                    isLoadingItem = false
                    viewModel.animal = state.responseBody
                }


                is RemoteRequestStatus.OnFailedRequest -> {
                    val exception = (state as RemoteRequestStatus.OnFailedRequest<*>).errorMessage
                    Log.d("TAG", "DetailsScreen: $exception")
                }

                else -> {}
            }

        }
    }

    sharedTransitionScope.apply {
        Column(
            modifier = Modifier
                .background(if (systemInDarkTheme) colorBlue else Color.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back Icon",
                    colorFilter = if (systemInDarkTheme) ColorFilter.tint(Color.White) else ColorFilter.tint(
                        Color.Black
                    ),
                    modifier = Modifier.clickable {
                        navController.navigateUp()
                    })
                Image(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "favorite Icon",
                    colorFilter = if (systemInDarkTheme) ColorFilter.tint(Color.White) else ColorFilter.tint(
                        Color.Black
                    )
                )
            }

            if (isLoadingItem)
                ShimmerDetailsScreen(systemInDarkTheme = systemInDarkTheme)
             else
                Column(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "image-$petId"),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = boundsTransform,
                            )
                            .height(320.dp)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                colorGrayLight2_X, RoundedCornerShape(16.dp)
                            )
                    ) {
                        if (!viewModel.animal?.photo.isNullOrEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.animal?.photo?.get(0)?.full).crossfade(true)
                                    .build(),
                                placeholder = painterResource(id = R.drawable.pet),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.clip(RoundedCornerShape(16.dp)).fillMaxWidth()
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "details-$petId"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = boundsTransform,
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween
                        ) {
                            Text(
                                text = (viewModel.animal?.name)?:"No Name", style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.cairobold)),
                                    fontSize = 18.sp,
                                    color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                ), maxLines = 1, overflow = TextOverflow.Ellipsis

                            )

                            Text(
                                text = (viewModel.animal?.status)?:"No Status",
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.cairolight)),
                                    fontSize = 12.sp,
                                    color = colorBlueLight
                                ),
                                modifier = Modifier
                                    .background(
                                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                                    )
                                    .padding(start = 10.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
                                textAlign = TextAlign.End
                            )
                        }


                        Text(
                            text = (viewModel.animal?.description)?:"No Description",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairosemibold)),
                                fontSize = 16.sp,
                                color = (if (systemInDarkTheme) colorWhite else colorBlue)
                            ),
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_location_pin),
                                    contentDescription = "Location Pin",
                                    Modifier.size(15.dp)
                                )

                                Text(
                                    text = (viewModel.animal?.contact?.address?.city)?:"No Address",
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .align(Alignment.CenterVertically),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairosemibold)),
                                        fontSize = 12.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Text(
                                text = "${viewModel.animal?.distance}",
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                                    fontSize = 12.sp,
                                    color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                ),
                                textAlign = TextAlign.End
                            )
                        }

                        Text(
                            text = "About me",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairobold)),
                                fontSize = 18.sp,
                                color = (if (systemInDarkTheme) colorWhite else colorBlue)
                            ),
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = (viewModel.animal?.description)?:"No Description",
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairoregular)),
                                fontSize = 16.sp,
                                color = (if (systemInDarkTheme) colorWhite else colorBlue)
                            ),
                        )

                        Text(
                            text = "Quick Info",
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairobold)),
                                fontSize = 18.sp,
                                color = (if (systemInDarkTheme) colorWhite else colorBlue)
                            ),
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(end = 8.dp, start = 16.dp)
                                    .background(
                                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                                    )
                                    .weight(1f)
                                    .padding(8.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "${viewModel.animal?.age} yrs",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 14.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Age",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 10.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(end = 8.dp, start = 16.dp)
                                    .background(
                                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                                    )
                                    .weight(1f)
                                    .padding(8.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "${viewModel.animal?.colors?.primary}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 14.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Color",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 10.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(end = 8.dp, start = 16.dp)
                                    .background(
                                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                                    )
                                    .weight(1f)
                                    .padding(8.dp)
                                    .align(alignment = Alignment.CenterVertically),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text(
                                    text = "14 Kg",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 14.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Weight",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.cairobold)),
                                        fontSize = 10.sp,
                                        color = (if (systemInDarkTheme) colorWhite else colorBlue)
                                    ),
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }

                    }
                }

        }
    }
}