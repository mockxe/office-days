package io.mockxe.officedays.repository

import io.mockxe.officedays.model.OfficeDay

interface OfficeDayRepository {

    fun get(position: Int): OfficeDay
    fun getAll(): MutableList<OfficeDay>
    fun add(officeDay: OfficeDay)
    fun update(officeDay: OfficeDay)
    fun remove(officeDay: OfficeDay)

}