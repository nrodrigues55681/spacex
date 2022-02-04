package com.mindera.rocketscience.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaunchesDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(values: List<LaunchesEntity>)

    @Query("SELECT * FROM launches ORDER BY missionName DESC")
    abstract fun getLaunchesPagerDesc(): PagingSource<Int, LaunchesEntity>

    @Query("SELECT * FROM launches ORDER BY missionName ASC")
    abstract fun getLaunchesPagerAsc(): PagingSource<Int, LaunchesEntity>

    @Query("SELECT * FROM launches WHERE missionName LIKE '%' || :searchTerm || '%'")
    abstract fun searchLaunches(searchTerm: String): Flow<List<LaunchesEntity>>

    @Query("DELETE FROM launches")
    abstract fun clearAll()
}