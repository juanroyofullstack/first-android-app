package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.repository.UserSessionRepository
import com.example.myapplication.session.UserSessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserSessionModule {

    @Provides
    @Singleton
    fun provideUserSessionDataStore(
        @ApplicationContext context: Context
    ): UserSessionDataStore = UserSessionDataStore(context)

    @Provides
    @Singleton
    fun provideUserSessionRepository(
        dataStore: UserSessionDataStore
    ): UserSessionRepository = UserSessionRepository(dataStore)
}