package io.mockxe.officedays.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockxe.officedays.repository.impl.OfficeDayRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsOfficeDayRepositoryImpl(officeDayRepositoryImpl: OfficeDayRepositoryImpl): OfficeDayRepository

}