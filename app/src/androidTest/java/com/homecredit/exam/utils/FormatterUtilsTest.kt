package com.homecredit.exam.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormatterUtilsTest {

    @Test
    fun getTemperatureTwoDecimalPoint() {
        assertEquals("-0.10",  FormatterUtils.getTemperature(FormatterUtils.HI_TEMPERATURE_FORMAT, "-0.1"))
        assertEquals("0.00",  FormatterUtils.getTemperature(FormatterUtils.HI_TEMPERATURE_FORMAT, "-0.0"))
        assertEquals("0.00",  FormatterUtils.getTemperature(FormatterUtils.HI_TEMPERATURE_FORMAT, "0"))
        assertEquals("1.00",  FormatterUtils.getTemperature(FormatterUtils.HI_TEMPERATURE_FORMAT, "1"))
        assertEquals("1.89",  FormatterUtils.getTemperature(FormatterUtils.HI_TEMPERATURE_FORMAT, "1.888"))
    }

    @Test
    fun getTemperatureOneDecimalPoint() {
        assertEquals("-0.1",  FormatterUtils.getTemperature(FormatterUtils.CURRENT_TEMPERATURE_FORMAT, "-0.1"))
        assertEquals("0.0",  FormatterUtils.getTemperature(FormatterUtils.CURRENT_TEMPERATURE_FORMAT, "-0.0"))
        assertEquals("0.0",  FormatterUtils.getTemperature(FormatterUtils.CURRENT_TEMPERATURE_FORMAT, "0"))
        assertEquals("1.0",  FormatterUtils.getTemperature(FormatterUtils.CURRENT_TEMPERATURE_FORMAT, "1"))
        assertEquals("1.9",  FormatterUtils.getTemperature(FormatterUtils.CURRENT_TEMPERATURE_FORMAT, "1.888"))
    }
}