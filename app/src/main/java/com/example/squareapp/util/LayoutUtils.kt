package com.example.squareapp.util

import android.content.Context
import com.example.squareapp.R

//Code from stackoverflow to determine how many columns fit on screen
fun calculateNoOfColumns(context: Context): Int {
  val displayMetrics= context.resources.displayMetrics
  val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
  //multiplied by 1.4 to provide buffer
  val columnWidthPx = context.resources.getDimension(R.dimen.employee_card_width) * 1.4f
  val columnWidthDp = columnWidthPx / displayMetrics.density
  return (screenWidthDp / columnWidthDp + 0.5).toInt()
}
