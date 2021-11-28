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

//    @Test
//    fun getWordsPositioned() {
//        val weighted = matrix.getWordsPositioned()
//        assertFalse(weighted.isEmpty())
//        LOG.info("weighted = {}", weighted)
//    }
//
//    @Test
//    fun getTimeOneElement() {
//        val words: Array<String> = arrayOf("es", "ist", "ein", "Uhr")
//        val times: Set<Array<String>> = setOf(words)
//        val wordMatrix = matrix.getMatrix(times)
//        assertEquals("es ist ein Uhr", wordMatrix)
//    }
//
//    //@Test
//    fun getTimeTwoElements() {
//        val one: Array<String> = arrayOf("es", "ist", "ein", "Uhr")
//        val two: Array<String> = arrayOf("es", "ist", "fuenf", "nach", "eins")
//        val times: Set<Array<String>> = setOf(one, two)
//        val wordMatrix = matrix.getMatrix(times)
//        assertEquals("es ist fuenf nach einsUhr", wordMatrix)
//    }
//
//    @Test
//    fun buildVariants() {
//        val one: Array<String> = arrayOf("es", "ist", "ein", "Uhr")
//        val two: Array<String> = arrayOf("es", "ist", "fuenf", "nach", "eins")
//        val variants = matrix.buildVariants(one, two)
//        LOG.info("{} elements:", variants.size)
//        for (x in variants) {
//            LOG.info("{}", x)
//            assertContains(x, one)
//            assertContains(x, two)
//            assertNotContains(x, "ein")
//        }
//        assertFalse(variants.isEmpty())
//        assertTrue(variants.size > 0)
//    }
//
//    private fun assertContains(list: List<String>, parts: Array<String>) {
//        var i = 0
//        for (word in list) {
//            if ((word == parts[i]) || word.contains(parts[i])) {
//                i++
//            }
//            if (i >= parts.size) {
//                return
//            }
//        }
//        throw AssertionError(parts[i] + " fehlt in " + list)
//    }
//
//    private fun assertNotContains(list: List<String>, word: String) {
//        for (s in list) {
//            if (s == word) {
//                throw AssertionError("'$word' not expected in " + list)
//            }
//        }
//    }
//
//    @Test
//    fun buildMatrix() {
//        val variants = matrix.buildMatrixList()
//        LOG.info("{} elements:", variants.size)
//        for (x in variants) {
//            LOG.info("{}", x)
//        }
//        assertFalse(variants.isEmpty())
//        assertTrue(variants.size > 1)
//    }

}