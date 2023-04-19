package io.mockxe.officedays.dao

import io.mockxe.officedays.model.OfficeDay
import java.time.LocalDate

interface OfficeDayDAO {

    fun read(): Map<LocalDate, OfficeDay>
    fun save(officeDayMap: Map<LocalDate, OfficeDay>)

}