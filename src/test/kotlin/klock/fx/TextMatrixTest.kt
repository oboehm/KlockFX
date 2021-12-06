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
    fun getKlock() {
        val matrix = TextMatrix()
        val words = matrix.getWords()
        assertTrue(words.size > 12)
        LOG.info("words = {}", words)
    }

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
        val lines = TextMatrix(Dimension(90, 1)).getMatrix()
        assertEquals(1, lines.size)
        val klock = TextKlock
        LOG.info(lines[0])
        for (time in klock.getAllTimes()) {
            LOG.info(time)
            assertTimeIn(time.uppercase(), lines[0])
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

}