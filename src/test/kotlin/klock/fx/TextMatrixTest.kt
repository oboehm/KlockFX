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
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TextMatrixTest {

    val LOG = LogManager.getLogger()
    val matrix = TextMatrix()

    @Test
    fun getKlock() {
        val words = matrix.getWords()
        assertTrue(words.size > 12);
        LOG.info("words = {}", words)
    }

    @Test
    fun getWordsWeighted() {
        val weighted = matrix.getWordsWeighted();
        assertFalse(weighted.isEmpty());
        LOG.info("weighted = {}", weighted);
    }

}