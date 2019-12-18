package com.example.squareapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.squareapp.main.MainViewModel
import com.example.squareapp.model.EmployeeDirectory
import com.example.squareapp.model.Result
import com.example.squareapp.repo.Repository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class MainViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  lateinit var mainViewModel: MainViewModel

  val repositoryMock = mock<Repository>()

  @Before
  fun setup() {
    mainViewModel = MainViewModel(repositoryMock, mainCoroutineRule.testDispatcher)
  }

  @Test
  fun testFetchEmployeeDirectory() = mainCoroutineRule.runBlockingTest {
    //Given
    val employeeDirectory = EmployeeDirectory(emptyList())
    doReturn(employeeDirectory).`when`(repositoryMock).fetchEmployeeDirectory()

    //When
    val result = mainViewModel.employeeDirectory
    result.observeForTesting {
      //Then
      assertEquals(employeeDirectory, (result.value as? Result.Success)?.data)
    }

  }

  @Test
  fun testFetchEmployeeDirectoryError() = mainCoroutineRule.runBlockingTest {
    //Given
    val exception = RuntimeException()
    doThrow(exception).`when`(repositoryMock).fetchEmployeeDirectory()
    val result = mainViewModel.employeeDirectory

    //When
    result.observeForTesting {
      //Then
      assertEquals(exception,(result.value as Result.Error).exception)
    }
  }

  @Test
  fun testFetchSortedEmployeeDirectory() = mainCoroutineRule.runBlockingTest {
    //Given
    val emp0 = "emp A"
    val emp1 = "emp B"
    val emp2 = "emp C"

    val employeeList = listOf(
      buildEmployee(emp2),
      buildEmployee(emp0),
      buildEmployee(emp1)
    )
    val employeeDirectory = EmployeeDirectory(employeeList)

    doReturn(employeeDirectory).`when`(repositoryMock).fetchEmployeeDirectory()
    val result = mainViewModel.employeeDirectory

    //When
    result.observeForTesting {
      //Then
      assertEquals(emp0, (result.value as Result.Success).data.employees.get(0).fullName)
      assertEquals(emp1, (result.value as Result.Success).data.employees.get(1).fullName)
      assertEquals(emp2, (result.value as Result.Success).data.employees.get(2).fullName)
    }
  }

}
