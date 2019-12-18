package com.example.squareapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.squareapp.R
import com.example.squareapp.model.Result
import com.example.squareapp.util.calculateNoOfColumns
import kotlinx.android.synthetic.main.activity_main.alt_state_group
import kotlinx.android.synthetic.main.activity_main.alt_state_text
import kotlinx.android.synthetic.main.activity_main.employee_directory_progress
import kotlinx.android.synthetic.main.activity_main.employee_recycler_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initEmployeeDirectory()
  }

  private fun initEmployeeDirectory() {

    val spanCount = calculateNoOfColumns(this)
    employee_recycler_view.layoutManager = GridLayoutManager(this, spanCount)
    val employeeListAdapter =
      EmployeeRecyclerAdapter()
    employee_recycler_view.adapter = employeeListAdapter

    mainViewModel.employeeDirectory.observe(this, Observer {

      setLoading(it is Result.Loading)

      when(it) {
        is Result.Success -> {
          val employeeDirectory = it.data
          if (employeeDirectory.employees.isNotEmpty()) {
            //Set data to adapter and update
            employeeListAdapter.employeeList = employeeDirectory.employees
            employeeListAdapter.notifyDataSetChanged()
          } else {
            //Empty state
            showAltState(R.string.empty_state_text)
          }
        }

        is Result.Error -> showAltState(R.string.error_state_text)
      }
    })
  }

  private fun showAltState(altTextResId: Int) {
    alt_state_text.text = getString(altTextResId)
    alt_state_group.visibility = View.VISIBLE
  }

  private fun setLoading(loading: Boolean) {
    employee_directory_progress.visibility = if (loading) View.VISIBLE else View.GONE
  }
}
