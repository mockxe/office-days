package io.mockxe.officedays.dao

import io.mockxe.officedays.model.OfficeDay

interface OfficeDayDAO {

    fun read(): List<OfficeDay>
    fun save(officeDayList: List<OfficeDay>)

}