package com.omaradev.pet_adoption.ui.home.component


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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorBlueLight
import com.omaradev.pet_adoption.ui.theme.colorGrayLight2_X
import com.omaradev.pet_adoption.ui.theme.colorWhite

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NearByResultListItem(
    animal: Animal,
    index: Int,
    isDarkTheme: Boolean,
    boundsTransform: (Rect, Rect) -> TweenSpec<Rect>,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    isClick: () -> Unit
) {
    if (!animal.photo.isNullOrEmpty())
        sharedTransitionScope.apply {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .clickable { isClick() }
            ) {
                Box(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image-$index"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = boundsTransform,
                        )
                        .size(130.dp)
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    if (!animal.photo.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(animal.photo[0].full)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.pet),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                        )
                    } else {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.pet),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "details-$index"),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = boundsTransform,
                        )
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = animal.name ?: "",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairobold)),
                                fontSize = 16.sp,
                                color = if (isDarkTheme) colorWhite else colorBlue
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(2f)
                        )

                        Text(
                            text = animal.status ?: "",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairolight)),
                                fontSize = 12.sp,
                                color = colorBlueLight
                            ),
                            modifier = Modifier
                                .background(colorGrayLight2_X, RoundedCornerShape(16.dp))
                                .padding(horizontal = 10.dp, vertical = 2.dp)
                                .weight(1f),
                            textAlign = TextAlign.End
                        )
                    }

                    Text(
                        text = animal.description ?: "",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.cairosemibold)),
                            fontSize = 14.sp,
                            color = if (isDarkTheme) colorWhite else colorBlue
                        ),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_location_pin),
                                contentDescription = "Location Pin",
                                modifier = Modifier.size(15.dp)
                            )

                            Text(
                                text = "${animal.contact?.address?.city} | ${animal.contact?.address?.country}",
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .align(Alignment.CenterVertically),
                                style = TextStyle(
                                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                                    fontSize = 10.sp,
                                    color = if (isDarkTheme) colorWhite else colorBlue
                                ),
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = animal.convertDate(animal.published_at) ?: "",
                            modifier = Modifier.padding(horizontal = 4.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.cairosemibold)),
                                fontSize = 12.sp,
                                color = if (isDarkTheme) colorWhite else colorBlue
                            ),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
}
