package io.mockxe.officedays.dao

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockxe.officedays.dao.impl.ListImpl
import io.mockxe.officedays.dao.impl.OfficeDayDAOListImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @ListImpl
    @Binds
    @Singleton
    abstract fun bindsOfficeDayDAOListImpl(officeDayDAOListImpl: OfficeDayDAOListImpl): OfficeDayDAO

}