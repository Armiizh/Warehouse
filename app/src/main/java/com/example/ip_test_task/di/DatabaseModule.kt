package com.example.ip_test_task.di

import android.content.Context
import androidx.room.Room
import com.example.ip_test_task.data.AppDatabase
import com.example.ip_test_task.data.dao.DaoItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "users")
            .build()
    }

    @Provides
    fun provideDaoPos(database: AppDatabase): DaoItem {
        return database.daoItem()
    }
}