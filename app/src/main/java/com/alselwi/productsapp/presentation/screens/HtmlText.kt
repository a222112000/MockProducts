package com.alselwi.productsapp.presentation.screens

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier
){
    val formattedTest = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
    AndroidView(modifier = modifier,
        factory = {context->
            TextView(context)
        }, update = {textView ->
            textView.text = formattedTest
        })
}