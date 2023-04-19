package io.mockxe.officedays.repository.impl

import io.mockxe.officedays.model.OfficeDay

class OfficeDayAlreadyExistsException(val officeDay: OfficeDay): Exception("Office day ${officeDay.date} already exists")