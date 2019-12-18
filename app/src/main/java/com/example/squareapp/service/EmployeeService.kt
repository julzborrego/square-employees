package com.example.squareapp.service

import com.example.squareapp.model.EmployeeDirectory
import com.example.squareapp.util.endPoint
import retrofit2.http.GET

interface EmployeeService {
  @GET(endPoint)
  suspend fun fetchEmployeeDirectory() : EmployeeDirectory
}