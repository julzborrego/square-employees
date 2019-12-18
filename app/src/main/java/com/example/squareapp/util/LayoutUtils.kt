package com.example.squareapp.util

import android.content.Context

//Code from stackoverflow to determine how many columns fit on screen
fun calculateNoOfColumns(context: Context, columnWidthPx: Float): Int {
  val displayMetrics= context.resources.displayMetrics
  val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
  val columnWidthDp = columnWidthPx / displayMetrics.density
  return (screenWidthDp / columnWidthDp + 0.5).toInt()
}
