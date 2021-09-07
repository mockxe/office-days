package io.mockxe.officedays.repository.impl

import io.mockxe.officedays.dao.OfficeDayDAO
import io.mockxe.officedays.dao.impl.ListImpl
import io.mockxe.officedays.model.OfficeDay
import io.mockxe.officedays.repository.OfficeDayRepository
import javax.inject.Inject

class OfficeDayRepositoryImpl @Inject constructor(): OfficeDayRepository {
 
    @ListImpl
    @Inject
    lateinit var officeDayDAO: OfficeDayDAO

    private val officeDays: MutableList<OfficeDay> = mutableListOf()

    override fun get(position: Int): OfficeDay {
        if (officeDays.isEmpty()) {
            officeDays.addAll(officeDayDAO.read())
        }

        return officeDays[position]
    }

    override fun getAll(): MutableList<OfficeDay> {
        if (officeDays.isEmpty()) {
            officeDays.addAll(officeDayDAO.read())
        }

        return officeDays
    }

    override fun add(officeDay: OfficeDay) {

        // check if the date already exists
        if (officeDays
                .any { listDay: OfficeDay ->
                    listDay.date == officeDay.date
                }) {

            // throw exception if office day already exists
            throw OfficeDayAlreadyExistsException(officeDay)

        } else {
            officeDays.add(officeDay)
            officeDays.sortByDescending { day: OfficeDay -> day.date }
            officeDayDAO.save(officeDays)
        }

    }

    override fun remove(officeDay: OfficeDay) {
        if (officeDays.remove(officeDay)) {
            officeDayDAO.save(officeDays)
        }
    }
    
}