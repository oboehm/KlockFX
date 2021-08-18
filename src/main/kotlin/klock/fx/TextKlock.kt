/*
 * Copyright (c) 2020 by Oli B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 02.12.2020 by Oli B. (ob@aosd.de)
 */
package klock.fx

import java.time.LocalTime

object TextKlock {

    fun getTime(): String {
        return getTime(LocalTime.now())
    }

    fun getTime(time: LocalTime): String {
        val hour = (time.hour-1) % 12 + 1
        return when (time.minute / 5) {
            1 -> "es ist f\u00fcnf nach " + toString(hour)
            2 -> "es ist zehn nach " + toString(hour)
            3 -> "es ist viertel " + toString(hour + 1)
            4 -> "es ist zehn vor halb " + toString(hour + 1)
            5 -> "es ist f\u00fcnf vor halb " + toString(hour + 1)
            6 -> "es ist halb " + toString(hour + 1)
            7 -> "es ist f\u00fcnf nach halb " + toString(hour + 1)
            8 -> "es ist zehn nach halb " + toString(hour + 1)
            9 -> "es ist dreiviertel " + toString(hour + 1)
            10 -> "es ist zehn vor " + toString(hour + 1)
            11 -> "es ist f\u00fcnf vor " + toString(hour + 1)
            else -> String.format(
                "es ist %s Uhr",
                (if (hour == 1) "ein" else toString(hour)))
        }
    }

    private fun toString(hour: Int): String {
        val numbers: Array<String> = arrayOf(
            "zw\u00f6lf", "eins", "zwei", "drei", "vier", "f\u00fcnf", "sechs",
            "sieben", "acht", "neun", "zehn", "elf", "zw\u00f6lf", "eins"
        )
        return numbers[hour]
    }

    fun getAllTimes(): List<String> {
        val allTimes = mutableSetOf<String>()
        var t = LocalTime.of(0, 5)
        while (t.compareTo(LocalTime.of(12, 5)) < 0) {
            allTimes.add(getTime(t))
            t = t.plusMinutes(5L)
        }
        return allTimes.toList()
    }

}