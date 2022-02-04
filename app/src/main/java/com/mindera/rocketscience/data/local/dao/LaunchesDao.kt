package com.mindera.rocketscience.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LaunchesDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(values: List<LaunchesEntity>)

    @RawQuery(observedEntities = [LaunchesEntity::class])
    abstract fun getLaunchesPager(query: SupportSQLiteQuery): PagingSource<Int, LaunchesEntity>

    @Query("DELETE FROM launches")
    abstract fun clearAll()
}