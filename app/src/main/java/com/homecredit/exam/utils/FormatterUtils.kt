package com.homecredit.exam.utils

import java.math.RoundingMode
import java.text.DecimalFormat


object FormatterUtils {
    const val CURRENT_TEMPERATURE_FORMAT = "#0.0"
    const val HI_TEMPERATURE_FORMAT = "#0.00"
    const val LOW_TEMPERATURE_FORMAT = "#0.00"

    fun getTemperature(format: String, value: String): String {
        val df = DecimalFormat(format)
        df.roundingMode = RoundingMode.HALF_UP
        var formattedValue: String = df.format(value.toDouble())
        //Remove the [-] in -0.0
        formattedValue = formattedValue.replace("^-(?=0(\\.0*)?$)".toRegex(), "")
        return formattedValue
    }
}