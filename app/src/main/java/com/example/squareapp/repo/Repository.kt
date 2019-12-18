package com.example.squareapp.repo

import com.example.squareapp.model.EmployeeDirectory

interface Repository {
  suspend fun fetchEmployeeDirectory() : EmployeeDirectory
}