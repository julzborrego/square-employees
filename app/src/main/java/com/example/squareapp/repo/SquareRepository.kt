package com.example.squareapp.repo

import com.example.squareapp.service.EmployeeService

class SquareRepository(private val employeeService: EmployeeService) :
  Repository {

  override suspend fun fetchEmployeeDirectory() = employeeService.fetchEmployeeDirectory()
}