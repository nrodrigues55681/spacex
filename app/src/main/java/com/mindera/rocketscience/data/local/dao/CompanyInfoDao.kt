package com.mindera.rocketscience.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CompanyInfoDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(values: CompanyInfoEntity)

    @Query("SELECT * FROM company_info LIMIT 1")
    abstract suspend fun getCompanyInfo(): CompanyInfoEntity?

    @Query("DELETE FROM company_info")
    abstract fun clearAll()
}