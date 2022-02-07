package com.mindera.rocketscience

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.local.dao.CompanyInfoDao
import com.mindera.rocketscience.data.local.dao.LaunchesDao
import com.mindera.rocketscience.data.local.entity.CompanyInfoEntity
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.utils.getData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpaceXDbTest {

    lateinit var db: SpaceXDb
    lateinit var companyInfoDao: CompanyInfoDao
    lateinit var launchesDao: LaunchesDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, SpaceXDb::class.java).build()
        companyInfoDao = db.companyInfoDao()
        launchesDao = db.launchesDao()
    }

    @Test
    fun companyInfoDaoTest() = runBlocking {
        val expected = CompanyInfoEntity(name = "SpaceX", founder = "",
            founded = 2002, employees = 20, launchSites = 1, valuation = 5)
        companyInfoDao.insert(expected)
        val companyInfo = companyInfoDao.getCompanyInfo()
        Truth.assertThat(companyInfo).isNotNull()
        companyInfo?.let {
            Truth.assertThat(it).isEqualTo(expected)
        }
        companyInfoDao.clearAll()
        val companyInfo2 = companyInfoDao.getCompanyInfo()
        Truth.assertThat(companyInfo2).isNull()
    }

    @Test
    fun launchesDaoTest() = runBlocking {
        val expectedMaxYear = 2005
        val expectedMinYear = 2000
        val temp = listOf(
            LaunchesEntity(missionName = "Test1", videoLink = "", articleLink = "", rocketType = "", rocketName = "",
                launchSuccess = true, missionPatchSmall = "", launchDateUnix = 1234, flightNumber = 1,
                launchYear = expectedMaxYear, wikipedia = ""),
            LaunchesEntity(missionName = "Test2", videoLink = "", articleLink = "", rocketType = "", rocketName = "",
                launchSuccess = false, missionPatchSmall = "", launchDateUnix = 1234, flightNumber = 2,
                launchYear = expectedMaxYear, wikipedia = ""),
            LaunchesEntity(missionName = "Test3", videoLink = "", articleLink = "", rocketType = "", rocketName = "",
                launchSuccess = true, missionPatchSmall = "", launchDateUnix = 1234, flightNumber = 3,
                launchYear = expectedMinYear, wikipedia = "")
        )
        launchesDao.insertAll(temp)

        var query = SimpleSQLiteQuery("SELECT * FROM launches ORDER BY missionName ASC")
        var lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(3)
        Truth.assertThat(lstInserted[0].missionName).isEqualTo("Test1")

        query = SimpleSQLiteQuery("SELECT * FROM launches ORDER BY missionName DESC")
        lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(3)
        Truth.assertThat(lstInserted[0].missionName).isEqualTo("Test3")

        query = SimpleSQLiteQuery("SELECT * FROM launches " +
                "WHERE launchSuccess = 1 ORDER BY missionName DESC")
        lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(2)

        query = SimpleSQLiteQuery("SELECT * FROM launches WHERE" +
                " launchSuccess = 0 ORDER BY missionName DESC")
        lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(1)
        Truth.assertThat(lstInserted[0].missionName).isEqualTo("Test2")

        query = SimpleSQLiteQuery("SELECT * FROM launches WHERE launchSuccess = 1 AND " +
                "launchYear >= 2000 AND launchYear <= 2004 ORDER BY missionName DESC")
        lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(1)
        Truth.assertThat(lstInserted[0].missionName).isEqualTo("Test3")

        val maxMin = launchesDao.getMaxMinLaunchYear()
        Truth.assertThat(maxMin.launchYearMax).isEqualTo(expectedMaxYear)
        Truth.assertThat(maxMin.launchYearMin).isEqualTo(expectedMinYear)

        launchesDao.clearAll()
        lstInserted = launchesDao.getLaunchesPager(query).getData()
        Truth.assertThat(lstInserted.size).isEqualTo(0)
    }

    @After
    fun closeDb() {
        db.close()
    }
}