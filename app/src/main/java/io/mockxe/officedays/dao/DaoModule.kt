package io.mockxe.officedays.dao

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockxe.officedays.dao.impl.CouchbaseLiteImpl
import io.mockxe.officedays.dao.impl.MapImpl
import io.mockxe.officedays.dao.impl.OfficeDayDAOCouchbaseLiteImpl
import io.mockxe.officedays.dao.impl.OfficeDayDAOMapImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {

    @MapImpl
    @Binds
    @Singleton
    abstract fun bindsOfficeDayDAOListImpl(officeDayDAOMapImpl: OfficeDayDAOMapImpl): OfficeDayDAO

    @CouchbaseLiteImpl
    @Binds
    @Singleton
    abstract fun bindsOfficeDayDAOCouchbaseImpl(officeDayDAOCouchbaseLiteImpl: OfficeDayDAOCouchbaseLiteImpl): OfficeDayDAO

}