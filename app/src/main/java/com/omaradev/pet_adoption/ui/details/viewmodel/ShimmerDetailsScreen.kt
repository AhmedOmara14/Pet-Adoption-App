package com.omaradev.pet_adoption.ui.details.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omaradev.pet_adoption.R
import com.omaradev.pet_adoption.ui.theme.colorBlack
import com.omaradev.pet_adoption.ui.theme.colorBlue
import com.omaradev.pet_adoption.ui.theme.colorGrayLight2_X

@Preview
@Composable
fun ShimmerDetailsScreen(
    systemInDarkTheme: Boolean,
) {
    Column(
        modifier = Modifier
            .background(if (systemInDarkTheme) colorBlue else Color.White)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    colorGrayLight2_X, RoundedCornerShape(16.dp)
                )
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    text = "",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.cairobold)),
                        fontSize = 18.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        )
                        .width(100.dp)
                )

                Text(
                    text = "",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.cairolight)),
                        fontSize = 12.sp,
                        color = colorBlack
                    ),
                    modifier = Modifier
                        .width(80.dp)
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        ),
                    textAlign = TextAlign.End
                )
            }


            Text(
                text = "\n\n",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairosemibold)),
                    fontSize = 16.sp
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                    .background(
                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .padding(top = 6.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Box(
                        Modifier
                            .size(25.dp)
                            .background(
                                colorGrayLight2_X, RoundedCornerShape(8.dp)
                            )
                    )

                    Text(
                        text = "",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(Alignment.CenterVertically)
                            .background(
                                colorGrayLight2_X, RoundedCornerShape(16.dp)
                            )
                            .width(100.dp),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.cairosemibold)),
                            fontSize = 12.sp,
                            color = colorGrayLight2_X
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "",
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 4.dp, end = 4.dp)
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        ),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.cairosemibold)),
                        fontSize = 12.sp,
                        color = colorGrayLight2_X
                    ),
                    textAlign = TextAlign.End
                )
            }

            Text(
                text = "",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .background(
                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                    )
                    .width(100.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairobold)),
                    fontSize = 18.sp,
                    color = colorGrayLight2_X
                ),
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "\n\n",
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .background(
                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                    )
                    .fillMaxWidth(),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairoregular)),
                    fontSize = 16.sp,
                    color = colorGrayLight2_X
                ),
            )

            Text(
                text = "",
                modifier = Modifier
                    .width(120.dp)
                    .padding(start = 16.dp, top = 16.dp)
                    .background(
                        colorGrayLight2_X, RoundedCornerShape(16.dp)
                    ),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.cairobold)),
                    fontSize = 18.sp,
                    color = colorGrayLight2_X
                ),
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(end = 8.dp, start = 16.dp)
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        )
                        .weight(1f)
                        .padding(8.dp)
                        .align(alignment = Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {}


                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(end = 8.dp, start = 16.dp)
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        )
                        .weight(1f)
                        .padding(8.dp)
                        .align(alignment = Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {}

                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(end = 8.dp, start = 16.dp)
                        .background(
                            colorGrayLight2_X, RoundedCornerShape(16.dp)
                        )
                        .weight(1f)
                        .padding(8.dp)
                        .align(alignment = Alignment.CenterVertically),
                    verticalArrangement = Arrangement.SpaceAround
                ) {}

            }

        }

    }
}