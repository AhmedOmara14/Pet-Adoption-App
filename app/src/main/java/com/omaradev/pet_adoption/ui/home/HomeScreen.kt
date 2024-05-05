package com.omaradev.pet_adoption.ui.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.ui.Pet
import com.omaradev.pet_adoption.ui.home.component.NearByResultListItem
import com.omaradev.pet_adoption.ui.theme.PetAdoptionTheme
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorWhite

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    pets: List<Pet>,
    navController: NavHostController,
    boundsTransform: (Rect, Rect) -> TweenSpec<Rect>,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    changeAppTheme: (systemInDarkTheme: Boolean) -> Unit
) {
    var isDarkTheme_ = isDarkTheme

    sharedTransitionScope.apply {
        Column(
            modifier = Modifier
                .background(if (isDarkTheme_) colorBlue else Color.White)
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
                        color = (if (isDarkTheme_) colorWhite else colorBlue)
                    )
                )

                Image(
                    painter = painterResource(if (isDarkTheme_) R.drawable.ic_dark_mode else R.drawable.ic_light_mode),
                    contentDescription = if (isDarkTheme_) "Dark Mode" else "Light Mode",
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .clickable {
                            isDarkTheme_ = !isDarkTheme_
                            changeAppTheme(isDarkTheme_)
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
                    color = (if (isDarkTheme_) colorWhite else colorBlue)
                )
            )

            Text(
                text = "Nearby Results",
                modifier = Modifier.padding(top = 28.dp, start = 8.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                    fontSize = 16.sp,
                    color = (if (isDarkTheme_) colorWhite else colorBlue)
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
                        isDarkTheme = isDarkTheme_,
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