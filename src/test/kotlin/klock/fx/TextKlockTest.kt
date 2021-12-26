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
 * (c)reated 06.12.2020 by Oli B. (ob@aosd.de)
 */

package klock.fx

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalTime

internal class TextKlockTest {

    private val LOG = LogManager.getLogger()

    @Test
    fun elfUhr() {
        checkTime("es ist elf Uhr", 11, 0)
    }

    @Test
    fun zehnNachNeun() {
        checkTime("es ist zehn nach neun", 9, 10)
    }

    @Test
    fun zehnVorZwei() {
        checkTime("es ist zehn vor zwei", 1, 50)
    }

    @Test
    fun fuenfVorZwoelf() {
        checkTime("es ist fünf vor zwölf", 11, 55)
    }

    @Test
    fun zehnNachZwoelf() {
        checkTime("es ist zehn nach zwölf", 12, 10)
    }

    @Test
    fun einUhr() {
        checkTime("es ist ein Uhr", 1, 0)
    }

    @Test
    fun zehnNachEins() {
        checkTime("es ist zehn nach eins", 1, 10)
    }

    @Test
    fun dreiviertelEins() {
        checkTime("es ist dreiviertel eins", 12, 45)
    }

    private fun checkTime(expected: String, hour: Int, minute: Int) {
        val t = LocalTime.of(hour, minute)
        val time = TextKlock().getTime(t)
        assertEquals(expected, time)
        LOG.info("{}: {}", t, time)
    }

    @Test
    fun getTimeNow() {
        val clock = TextKlock()
        val time = clock.getTime()
        LOG.info(time)
    }

    @Test
    fun getAllTimes() {
        val words = TextKlock().getAllTimes()
        assertEquals(144, words.size)
    }

}
