package io.mockxe.officedays

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.mockxe.officedays.model.OfficeDay
import io.mockxe.officedays.repository.OfficeDayRepository
import io.mockxe.officedays.repository.impl.OfficeDayAlreadyExistsException
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var officeDayRepository: OfficeDayRepository

    @Inject
    lateinit var adapter: OfficeDayRecyclerViewAdapter

    private val onDateSelectListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            run {
                Snackbar.make(view, "ping", Snackbar.LENGTH_SHORT)
                        .show()

                try {
                    officeDayRepository.add(OfficeDay(LocalDate.of(
                            year,
                            month + 1,
                            dayOfMonth)))

                    adapter.notifyDataSetChanged()

                } catch (e: OfficeDayAlreadyExistsException) {

                    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

                    Snackbar.make(
                            this.consLayoutMain,
                            getString(R.string.snack_add_exists, e.officeDay.date.format(dateFormatter)),
                            Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvOfficeDays.adapter = adapter
        rvOfficeDays.layoutManager = LinearLayoutManager(this)

        fabAddOfficeDay.setOnClickListener { view: View ->
            run {

                try {
                    officeDayRepository.add(OfficeDay(LocalDate.now()))
                    adapter.notifyDataSetChanged()

                } catch (e: OfficeDayAlreadyExistsException) {

                    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

                    Snackbar.make(
                            view,
                            getString(R.string.snack_add_exists, e.officeDay.date.format(dateFormatter)),
                            Snackbar.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menuAddOfficeDay -> {
                val calendar = Calendar.getInstance()

                DatePickerDialog(
                        this,
                        onDateSelectListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            R.id.menuStats -> startActivity(Intent(this, StatsActivity::class.java))

            else -> {}
        }

        return super.onOptionsItemSelected(item)
    }
}
