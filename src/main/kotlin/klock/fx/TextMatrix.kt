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

    private val LOG = LogManager.getLogger()
    private val matrix: Array<String>
    private val klock = TextKlock()

    constructor(width: Double, height: Double) :
            this(Dimension(width.toInt(), height.toInt()))

    init {
        matrix = initMatrix()
    }

    fun getMatrix() : Array<String> {
        return matrix
    }

    fun getTimeMatrix() : Array<String> {
        return getTimeMatrix(klock.getTime())
    }

    fun getTimeMatrix(time: String) : Array<String> {
        val timeMatrix = mutableListOf<String>()
        val words = time.uppercase().split(' ')
        var n = 0
        var i = 0
        for (m in matrix) {
            var line = " ".repeat(m.length)
            while (n < words.size) {
                i = m.indexOf(words[n], i)
                if (i < 0) {
                    break
                }
                val endIndex = i + words[n].length
                line = line.replaceRange(i, endIndex, words[n])
                n++
                i = endIndex
            }
            timeMatrix.add(line)
        }
        return timeMatrix.toTypedArray()
    }

    private fun initMatrix() : Array<String> {
        val ratio: Double = size.width.toDouble() / size.height.toDouble()
        if (ratio > 40) {
            return initMatrix("matrix01x90.txt")
        } else if (ratio > 15) {
            return initMatrix("matrix02x45.txt")
        } else if (ratio > 8) {
            return initMatrix("matrix03x31.txt")
        } else if (ratio > 5) {
            return initMatrix("matrix04x24.txt")
        } else if (ratio > 4) {
            return initMatrix("matrix05x22.txt")
        } else if (ratio > 2.5) {
            return initMatrix("matrix06x16.txt")
        } else if (ratio > 2) {
            return initMatrix("matrix07x16.txt")
        } else if (ratio > 1.5) {
            return initMatrix("matrix08x13.txt")
        } else if (ratio > 1.3) {
            return initMatrix("matrix09x12.txt")
        } else {
            return initMatrix("matrix10x11.txt")
        }
    }

    private fun initMatrix(resource: String) : Array<String> {
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

}