package io.mockxe.officedays.dao.impl

import io.mockxe.officedays.dao.OfficeDayDAO
import io.mockxe.officedays.model.OfficeDay
import java.time.LocalDate
import javax.inject.Inject

class OfficeDayDAOMapImpl @Inject constructor(): OfficeDayDAO {

    private val officeDays: MutableMap<LocalDate, OfficeDay> = mutableMapOf()

    override fun read(): Map<LocalDate, OfficeDay> {
        return officeDays
    }

    override fun save(officeDayMap: Map<LocalDate, OfficeDay>) {
        officeDays.clear()
        officeDays.putAll(officeDayMap)
    }

}