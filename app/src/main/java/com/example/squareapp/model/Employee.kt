package com.example.squareapp.model

import com.squareup.moshi.Json

data class Employee(
  val biography: String?,
  @field:Json(name = "email_address")
  val emailAddress: String,
  @field:Json(name = "employee_type")
  val employeeType: String,
  @field:Json(name = "full_name")
  val fullName: String,
  @field:Json(name = "phone_number")
  val phoneNumber: String?,
  @field:Json(name = "photo_url_large")
  val photoUrlLarge: String?,
  @field:Json(name = "photo_url_small")
  val photoUrlSmall: String?,
  val team: String,
  val uuid: String,
  var bioShown : Boolean = false,
  var smallPhotoErrored : Boolean = false,
  var largePhotoErrored : Boolean = false
)