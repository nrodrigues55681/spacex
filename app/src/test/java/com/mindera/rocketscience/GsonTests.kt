package com.mindera.rocketscience

import com.google.common.truth.Truth
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.ui.utils.LinksData
import com.mindera.rocketscience.ui.utils.fromJson
import com.mindera.rocketscience.ui.utils.toJson
import org.junit.Test

class GsonTests {
    @Test
    fun filterDataToJson() {
        val expected = "{\"sorting\":\"ASC\",\"launchSuccessFilter\":\"ALL\",\"filterByDate\":false,\"minYear\":0,\"maxYear\":0}"
        val filterData = FilterData()
        val filterDataJson = filterData.toJson()
        Truth.assertThat(filterDataJson).isEqualTo(expected)
    }

    @Test
    fun filterDataFromJson() {
        val json = "{\"sorting\":\"ASC\",\"launchSuccessFilter\":\"ALL\",\"filterByDate\":false,\"minYear\":0,\"maxYear\":0}"
        val expected = FilterData()
        val filterData = json.fromJson<FilterData>()
        Truth.assertThat(filterData).isEqualTo(expected)
    }

    @Test
    fun linksDataToJson() {
        val expected = "{\"articleLink\":\"\",\"wikipediaLink\":\"\",\"videoLink\":\"\"}"
        val linksData = LinksData()
        val linksDataJson = linksData.toJson()
        Truth.assertThat(linksDataJson).isEqualTo(expected)
    }

    @Test
    fun linksDataFromJson() {
        val json = "{\"articleLink\":\"\",\"wikipediaLink\":\"\",\"videoLink\":\"\"}"
        val expected = LinksData()
        val filterData = json.fromJson<LinksData>()
        Truth.assertThat(filterData).isEqualTo(expected)
    }
}