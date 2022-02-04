package com.mindera.rocketscience.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mindera.rocketscience.data.local.dao.CompanyInfoDao
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.utils.DATABASE_NAME
import com.mindera.rocketscience.data.local.dao.LaunchesDao
import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [LaunchesEntity::class, CompanyInfoEntity::class],
    version = 1, exportSchema = false)
abstract class SpaceXDb : RoomDatabase() {
    abstract fun companyInfoDao(): CompanyInfoDao
    abstract fun launchesDao(): LaunchesDao

    companion object {
        @Volatile private var instance: SpaceXDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, SpaceXDb::class.java, DATABASE_NAME)
                .build()
    }
}