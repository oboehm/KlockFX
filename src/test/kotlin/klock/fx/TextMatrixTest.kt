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
 * (c)reated 11.12.2020 by Oli B. (ob@aosd.de)
 */
package klock.fx

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.awt.Dimension

internal class TextMatrixTest {

    val LOG = LogManager.getLogger(TextMatrixTest::javaClass)

    @Test
    fun getMatrix() {
        val matrix = TextMatrix(Dimension(89, 1))
        val m = matrix.getMatrix()
        assertEquals(1, m.size)
    }

    @Test
    fun getMatrixTwoDim() {
        val matrix = TextMatrix(Dimension(45, 2))
        val m = matrix.getMatrix()
        assertEquals(2, m.size)
    }

    @Test
    fun noBlanksInMatrix() {
        val matrix = TextMatrix(Dimension(3440, 1440))
        val m = matrix.getMatrix()
        for (line in m) {
            assertFalse(line.contains(' '), "blank in '$line'")
            LOG.info(line)
        }
    }

    @Test
    fun testMatrix01() {
        assertMatrix(90, 1)
    }

    @Test
    fun testMatrix02() {
        assertMatrix(45, 2)
    }

    @Test
    fun testMatrix03() {
        assertMatrix(31, 3)
    }

    @Test
    fun testMatrix04() {
        assertMatrix(24, 4)
    }

    @Test
    fun testMatrix05() {
        assertMatrix(22, 5)
    }

    @Test
    fun testMatrix06() {
        assertMatrix(16,6)
    }

    @Test
    fun testMatrix07() {
        assertMatrix(16, 7)
    }

    @Test
    fun testMatrix08() {
        assertMatrix(13, 8)
    }

    @Test
    fun testMatrix09() {
        assertMatrix(12, 9)
    }

    @Test
    fun testMatrix10() {
        assertMatrix(11, 10, TextKlock("viertel vor"))
    }

    @Test
    fun testMatrix11() {
        assertMatrix(11, 11, TextKlock("viertel vor"))
    }

    private fun assertMatrix(width: Int, height: Int) {
        assertMatrix(width, height, TextKlock())
    }

    private fun assertMatrix(width: Int, height: Int, klock: TextKlock) {
        val matrix = TextMatrix(Dimension(width, height)).getMatrix()
        assertEquals(height, matrix.size)
        assertAllTimesIn(matrix, klock)
    }

    private fun assertAllTimesIn(lines: Array<String>, klock: TextKlock) {
        val longline = lines.joinToString(" ")
        for (time in klock.getAllTimes()) {
            assertTimeIn(time.uppercase(), longline)
        }
    }

    private fun assertTimeIn(time: String, line: String) {
        val words = time.split(' ')
        var i = 0
        for (w in words) {
            i = line.indexOf(w, i)
            assertTrue(i >= 0, "'$w' in '$time' not part of '$line'")
            i = i + w.length + 1
        }
    }

    @Test
    fun getTimeMatrix() {
        val klock = TextKlock()
        val matrix = TextMatrix(Dimension(90, 1))
        val t0 = klock.getTime()
        val m = matrix.getTimeMatrix()
        if (t0 == klock.getTime()) {
            assertTimeIn(t0.uppercase(), m[0])
        }
        LOG.info(m[0])
    }

    @Test
    fun testZehnNachAcht() {
        val matrix = TextMatrix(Dimension(90, 1))
        val t0 = "ES IST ZEHN NACH ACHT"
        val m = matrix.getTimeMatrix(t0)
        assertTimeIn(t0.uppercase(), m[0])
        LOG.info(m[0])
    }

    @Test
    fun getAllTimeMatrix() {
        val klock = TextKlock()
        val t0 = klock.getTime()
        for (y in 2 .. 10) {
            val matrix = TextMatrix(Dimension(100 / y, y))
            val m = matrix.getTimeMatrix()
            if (t0 == klock.getTime()) {
                assertTimeIn(t0.uppercase(), m.joinToString(" "))
            }
            for (line in m) {
                LOG.debug(line)
            }
        }
    }

}