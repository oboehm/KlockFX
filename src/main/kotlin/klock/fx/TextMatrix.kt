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
import java.util.Collections.swap
import javax.management.Query.plus

class TextMatrix {

    val LOG = LogManager.getLogger()
    private val klock = TextKlock
    private var matrix = ""

    fun getWords() : Set<String> {
        val words = mutableSetOf<String>()
        for (s in klock.getAllTimes()) {
            words.addAll(s.split(' '))
        }
        return words
    }

    fun getWordsPositioned() : Map<Int, Set<String>> {
        val weighted = mutableMapOf<Int, Set<String>>()
        for (s in klock.getAllTimes()) {
            val words = s.split(' ')
            for (i in 0 until words.size) {
                val set = weighted.getOrDefault(i, mutableSetOf<String>())
                weighted.put(i, set.plus(words[i]))
            }
        }
        return weighted
    }

    private fun getTimesAsWordLists() :Map<Int, Set<List<String>>> {
        val splitted = mutableMapOf<Int, Set<List<String>>>()
        for (s in klock.getAllTimes()) {
            val words = s.split(' ')
            val i = words.size
            val set = splitted.getOrDefault(i, mutableSetOf())
            splitted.put(i, set.plus(listOf(words)))
        }
        return splitted
    }

    fun getMatrix(timeSpecs: Set<Array<String>>) : String {
        for (strings in timeSpecs) {
            insertIntoMatrix(strings)
        }
        return matrix.trim()
    }

    private fun insertIntoMatrix(strings: Array<String>) {
        for (s in strings) {
            insertIntoMatrix(s)
        }
    }

    private fun insertIntoMatrix(word: String) {
        if (matrix.contains(word)) {
            return
        }
        matrix = matrix + " " + word
    }


    // Hier werden 2 Arrays verknuepft und alle Varianten gebildet.
    // Aus (a, b, c) und (d, e) enstehen dann die Varianten
    // (a, b, c, d, e)
    // (a, b, d, c, e)
    // (a, b, d, e, c)
    // ...
    // (d, e, a, b, c)
    fun buildVariants(a: Array<String>, b: Array<String>): List<List<String>> {
        val c: Array<String> = a.plus(b)
        //val permutations = permute(c.toList())
        val permutations = c.toList().permutations()
        var filtered = removeWrongRange(permutations, a)
        filtered = removeWrongRange(filtered, b)
        LOG.info("{} elements:", filtered.size)
        for (x in filtered) {
            LOG.info("{}", x)
        }
        return filtered
    }

    private fun removeWrongRange(allPerms: List<List<String>>, a: Array<String>): List<List<String>> {
        val perms = mutableListOf<List<String>>()
        for (x in allPerms) {
            if (inCorrectRange(x, a)) {
                perms.add(x)
            }
        }
        return perms
    }

    private fun inCorrectRange(permutated: List<String>, words: Array<String>): Boolean {
        var found = 0
        for (i in 0 .. words.size-1) {
            val index = permutated.indexOf(words[i])
            if (index < found) {
                return false
            }
            found = index
        }
        return true
    }

    // https://gist.github.com/dmdrummond/4b1d8a4f024183375f334a5f0a984718
    // Usage: listOf(1, 2, 3).permutations()
    // Output: [[1, 2, 3], [2, 1, 3], [3, 1, 2], [1, 3, 2], [2, 3, 1], [3, 2, 1]]
    fun <V> List<V>.permutations(): List<List<V>> {
        val retVal: MutableList<List<V>> = mutableListOf()

        fun generate(k: Int, list: List<V>) {
            // If only 1 element, just output the array
            if (k == 1) {
                retVal.add(list.toList())
            } else {
                for (i in 0 until k) {
                    generate(k - 1, list)
                    if (k % 2 == 0) {
                        swap(list, i, k - 1)
                    } else {
                        swap(list, 0, k - 1)
                    }
                }
            }
        }

        generate(this.count(), this.toList())
        return retVal
    }

}