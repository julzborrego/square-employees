package com.example.squareapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.squareapp.model.EmployeeDirectory
import com.example.squareapp.model.Result
import com.example.squareapp.repo.SquareRepository
import com.example.squareapp.service.EmployeeService
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.lang.Exception

class SquareRepoTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  lateinit var squareRepository: SquareRepository

  val serviceMock = mock<EmployeeService>()

  @Before
  fun setup() {
    squareRepository = SquareRepository(serviceMock)
  }

  @ExperimentalCoroutinesApi
  @Test
  fun testFetchEmployeeDirectoryService() = runBlockingTest {

    val employeeDirectory = EmployeeDirectory(emptyList())

    doReturn(employeeDirectory).`when`(serviceMock).fetchEmployeeDirectory()

    assertEquals(employeeDirectory, squareRepository.fetchEmployeeDirectory())
  }
}
