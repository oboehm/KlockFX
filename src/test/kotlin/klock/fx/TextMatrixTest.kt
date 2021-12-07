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

    val LOG = LogManager.getLogger()

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
        assertAllTimesIn(TextMatrix(Dimension(90, 1)).getMatrix())
    }

    @Test
    fun testMatrix02() {
        assertAllTimesIn(TextMatrix(Dimension(45, 2)).getMatrix())
    }

    @Test
    fun testMatrix03() {
        assertAllTimesIn(TextMatrix(Dimension(30, 3)).getMatrix())
    }

    @Test
    fun testMatrix04() {
        assertAllTimesIn(TextMatrix(Dimension(24, 4)).getMatrix())
    }

    @Test
    fun testMatrix05() {
        assertAllTimesIn(TextMatrix(Dimension(22, 5)).getMatrix())
    }

    private fun assertAllTimesIn(lines: Array<String>) {
        val longline = lines.joinToString(" ")
        val klock = TextKlock
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
        val matrix = TextMatrix(Dimension(90, 1))
        val m = matrix.getTimeMatrix()
        assertEquals(1, m.size)
        LOG.info(m[0])
    }

}