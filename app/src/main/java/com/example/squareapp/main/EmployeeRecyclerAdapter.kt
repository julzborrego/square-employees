package com.example.squareapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.squareapp.R
import com.example.squareapp.model.Employee
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_employee.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class EmployeeRecyclerAdapter() :
  RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder>(), KoinComponent {

  class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name = itemView.employee_name
    val team = itemView.employee_team
    val photo = itemView.employee_photo
    val bio = itemView.employee_bio
    val progressBar = itemView.employee_photo_progress
    lateinit var employee: Employee

    init {
      itemView.setOnLongClickListener {
        onItemLongClick()
        true
      }
    }

    /**
     * Alternate bio and image visibility on long clicks.
     */
    fun onItemLongClick() {
      if (employee.bioShown) {
        bio.visibility = View.GONE
        photo.visibility = View.VISIBLE
        employee.bioShown = false
      } else {
        photo.visibility = View.GONE
        bio.visibility = View.VISIBLE
        employee.bioShown = true
      }
    }
  }

  private val picasso: Picasso by inject()
  var employeeList: List<Employee> = emptyList()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): EmployeeViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_employee, parent, false)

    return EmployeeViewHolder(
      itemView
    )
  }

  override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
    val employee = employeeList[position]

    holder.name.text = employee.fullName
    holder.team.text = employee.team
    holder.bio.text = employee.biography
    if (employee.bioShown) {
      holder.bio.visibility = View.VISIBLE
      holder.photo.visibility = View.GONE
    }
    holder.employee = employee
    bindImage(holder, employee)
  }

  private fun bindImage(holder: EmployeeViewHolder, employee: Employee) {
    if (employee.photoUrlSmall.isNullOrEmpty()) {
      if (employee.photoUrlLarge.isNullOrEmpty()) {
        //Load placeholder if no image urls are provided
        picasso.load(R.drawable.ic_face).into(holder.photo)
      } else if (employee.largePhotoErrored) {
        //if large photo previously errored display error ic
        picasso.load(R.drawable.ic_error).into(holder.photo)
      } else {
        bindLargePhoto(holder, employee)
      }
    } else {
      if (employee.smallPhotoErrored) {
        //if small photo previously errored display error ic
        picasso.load(R.drawable.ic_error).into(holder.photo)
      } else {
        //load small photo
        holder.progressBar.visibility = View.VISIBLE
        picasso.load(employee.photoUrlSmall)
          .error(R.drawable.ic_error)
          .into(holder.photo, object : Callback {
            override fun onSuccess() {
              holder.progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
              holder.progressBar.visibility = View.GONE
              employee.smallPhotoErrored = true
            }
          })
      }
    }
  }

  private fun bindLargePhoto(holder: EmployeeViewHolder, employee: Employee) {
    //If large photo is in memory load it otherwise
    picasso.load(employee.photoUrlLarge)
      .networkPolicy(NetworkPolicy.OFFLINE)
      .error(R.drawable.ic_download)
      .into(holder.photo, object : Callback {
        override fun onSuccess() {}

        override fun onError(e: Exception?) {
          holder.photo.setOnLongClickListener {
            //keep longclick to view bio enabled
            holder.onItemLongClick()
            true
          }
          //click to download photo
          holder.photo.setOnClickListener {
            holder.progressBar.visibility = View.VISIBLE
            picasso.load(employee.photoUrlLarge)
              .error(R.drawable.ic_error)
              .into(holder.photo, object : Callback {
                override fun onSuccess() {
                  holder.progressBar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                  holder.progressBar.visibility = View.GONE
                  employee.largePhotoErrored = true
                }
              })
            //remove download onclick after attempt
            holder.photo.setOnClickListener(null)
          }
        }
      })
  }

  override fun getItemCount() = employeeList.size
}