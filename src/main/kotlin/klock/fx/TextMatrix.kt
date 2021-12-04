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
 * (c)reated 10.12.2020 by Oli B. (ob@aosd.de)
 */
package klock.fx

import org.apache.logging.log4j.LogManager
import java.awt.Dimension
import java.util.*

class TextMatrix(val size: Dimension = Dimension(10, 10)) {

    val LOG = LogManager.getLogger()
    private val klock = TextKlock

    fun getMatrix() : Array<String> {
        val ratio: Int = size.width / size.height
        if (ratio > 40) {
            return getMatrix("matrix01x89.txt")
        } else if (ratio > 15) {
            return getMatrix("matrix02x45.txt")
        } else {
            return getMatrix("matrix03x30.txt")
        }
    }

    private fun getMatrix(resource: String) : Array<String> {
        LOG.debug("Matrix wird aus Resource '{}' ausgelesen.", resource)
        val matrix = readResource(resource)
        return fillBlanks(matrix)
    }

    private fun fillBlanks(matrix: Array<String>): Array<String> {
        val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()
        for (i in 0 until matrix.size) {
            while (matrix[i].contains(' ')) {
                val li = random.nextInt(26)
                matrix[i] = matrix[i].replaceFirst(' ', letters[li])
            }
        }
        return matrix
    }

    private fun readResource(resource: String) : Array<String> {
        TextMatrix::class.java.getResource(resource).readText(Charsets.UTF_8).apply {
            return split('\n').toTypedArray()
        }
    }

    fun getWords() : Set<String> {
        val words = mutableSetOf<String>()
        for (s in klock.getAllTimes()) {
            words.addAll(s.split(' '))
        }
        return words
    }

}