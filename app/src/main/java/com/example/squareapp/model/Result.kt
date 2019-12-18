package com.example.squareapp.model

import android.util.Log

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {
  data class Success<out T>(val data: T) : Result<T>()
  data class Error(val exception: Exception) : Result<Nothing>() {
    init{
      Log.e("EmployeeDirectoryError:", exception.message ?: exception.toString())
    }
  }
  object Loading : Result<Nothing>()
}