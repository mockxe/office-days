package io.mockxe.officedays

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.mockxe.officedays.model.OfficeDay
import io.mockxe.officedays.repository.OfficeDayRepository
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

@ActivityScoped
class OfficeDayRecyclerViewAdapter @Inject constructor(@ActivityContext val context: Context): RecyclerView.Adapter<OfficeDayRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val parent: OfficeDayRecyclerViewAdapter): RecyclerView.ViewHolder(itemView) {

        lateinit var officeDay: OfficeDay

        val txtOfficeDayWeekday: TextView = itemView.findViewById(R.id.txtOfficeDayWeekday)
        val txtOfficeDayDate: TextView = itemView.findViewById(R.id.txtOfficeDayDate)

        init {
            itemView.findViewById<ConstraintLayout>(R.id.parentOfficeDay)
                .setOnClickListener { v: View ->
                    run {
                        AlertDialog.Builder(itemView.context)
                            .setTitle(R.string.dialog_delete_title)
                            .setMessage(R.string.dialog_delete_message)
                            .setNegativeButton(R.string.dialog_delete_opt_cancel) { _, _ -> }
                            .setPositiveButton(R.string.dialog_delete_opt_delete) { _, _ ->
                                run {
                                    parent.removeOfficeDay(officeDay, v)
                                }
                            }
                            .create()
                            .show()
                    }
                }
        }
    }

    @Inject
    lateinit var officeDayRepository: OfficeDayRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // inflate view from parent context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_office_day,
            parent,
            false
        )

        return ViewHolder(view, this)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val officeDay = officeDayRepository.get(position)

        holder.officeDay = officeDay
        holder.txtOfficeDayWeekday.text = officeDay.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        holder.txtOfficeDayDate.text = officeDay.date.format(dateFormatter)
    }

    override fun getItemCount(): Int {
        return officeDayRepository.getAll().count()
    }

    private fun removeOfficeDay(officeDay: OfficeDay, view: View) {
        officeDayRepository.remove(officeDay)
        notifyDataSetChanged()

        Snackbar.make(view, R.string.snack_delete_message, Snackbar.LENGTH_LONG)
            .setAction(R.string.snack_delete_undo) {
                run {
                    officeDayRepository.add(officeDay)
                    notifyDataSetChanged()
                }
            }
            .show()
    }

}