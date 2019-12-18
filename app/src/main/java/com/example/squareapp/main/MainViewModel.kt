package com.example.squareapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.squareapp.model.Employee
import com.example.squareapp.model.EmployeeDirectory
import com.example.squareapp.model.Result
import com.example.squareapp.repo.Repository
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.Exception

class MainViewModel(private val repo: Repository, private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

  val employeeDirectory: LiveData<Result<EmployeeDirectory>> = liveData(ioDispatcher) {
    emit(Result.Loading)
    try {
      val result = repo.fetchEmployeeDirectory()
      result.employees = sortEmployeesByLastName(result.employees)
      emit(Result.Success(result))
    } catch (e: Exception) {
      //Handle any errors fetching the data
      emit(Result.Error(e))
    }
  }

  private fun sortEmployeesByLastName(employeeList: List<Employee>) =
    employeeList.sortedBy { it.fullName.substringAfterLast(" ") }

}