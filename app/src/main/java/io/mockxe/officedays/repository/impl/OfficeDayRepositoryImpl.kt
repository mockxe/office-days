package io.mockxe.officedays.repository.impl

import io.mockxe.officedays.dao.OfficeDayDAO
import io.mockxe.officedays.dao.impl.CouchbaseLiteImpl
import io.mockxe.officedays.model.OfficeDay
import io.mockxe.officedays.repository.OfficeDayRepository
import java.time.LocalDate
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class OfficeDayRepositoryImpl @Inject constructor(): OfficeDayRepository {
 
    @CouchbaseLiteImpl
    @Inject
    lateinit var officeDayDAO: OfficeDayDAO

    private val officeDays: MutableMap<LocalDate, OfficeDay> = TreeMap(Comparator.reverseOrder())

    override fun get(position: Int): OfficeDay {
        loadMap()

        return officeDays.values.toList()[position]
    }

    override fun getAll(): MutableList<OfficeDay> {
        loadMap()

        return officeDays.values.toMutableList()
    }

    override fun add(officeDay: OfficeDay) {

        // check if the date already exists
        if (officeDays.containsKey(officeDay.date)) {
            // throw exception if office day already exists
            throw OfficeDayAlreadyExistsException(officeDay)

        } else {
            update(officeDay)
        }

    }

    override fun update(officeDay: OfficeDay) {
        officeDays[officeDay.date] = officeDay
        officeDayDAO.save(officeDays)
    }

    override fun remove(officeDay: OfficeDay) {
        if (officeDays.remove(officeDay.date) != null) {
            officeDayDAO.save(officeDays)
        }
    }

    private fun loadMap() {
        if (officeDays.isEmpty()) {
            officeDays.putAll(officeDayDAO.read())
        }
    }
    
}