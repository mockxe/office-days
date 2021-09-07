package io.mockxe.officedays.dao.impl

import io.mockxe.officedays.dao.OfficeDayDAO
import io.mockxe.officedays.model.OfficeDay
import javax.inject.Inject

class OfficeDayDAOListImpl @Inject constructor(): OfficeDayDAO {

    private val officeDays: MutableList<OfficeDay> = mutableListOf()

    override fun read(): List<OfficeDay> {
        return officeDays
    }

    override fun save(officeDayList: List<OfficeDay>) {
        officeDays.clear()
        officeDays.addAll(officeDayList)
    }

}