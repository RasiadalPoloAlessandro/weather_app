package com.weater_app.weater_app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScreenPager(pages: Int){

    val pagerState = rememberPagerState (pageCount = {pages})

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        page ->
        when(page){

        }
    }

}