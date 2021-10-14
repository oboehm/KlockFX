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
import java.util.Collections.list
import java.util.Collections.swap

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

    fun buildMatrix() : List<List<String>> {
        val allTimes : List<String> = klock.getAllTimes()
        var matrixList = listOf<List<String>>(listOf())
        for (words in allTimes) {
            val newMatrixList = mutableListOf<List<String>>()
            for (matrix in matrixList) {
                val newMatrix = buildVariants(matrix.toTypedArray(), words.split(' ').toTypedArray())
                newMatrixList.addAll(newMatrix)
            }
            matrixList = filterShortestVariants(newMatrixList)
        }
        return matrixList
    }

    private fun filterShortestVariants(matrixList: List<List<String>>): List<List<String>> {
        var min = Int.MAX_VALUE
        for (element in matrixList) {
            if (element.size < min) {
                min = element.size
            }
        }
        val filtered = mutableListOf<List<String>>()
        for (element in matrixList) {
            if (element.size == min) {
                filtered.add(element)
            }
        }
        return filtered
    }

    fun buildVariants(a: Array<String>, b: Array<String>): List<List<String>> {
        val minimized = stripDuplicates(a, b)
        val c: Array<String> = a.plus(minimized)
        //val permutations = permute(c.toList())
        val permutations = c.toList().permutations()
        var filtered = removeWrongRange(permutations, a)
        filtered = removeWrongRange(filtered, b)
        filtered = removeDoubleElements(filtered)
        filtered = removeLongElements(filtered)
        return filtered
    }

    // Elemente aus a, die in b vorkommen, werden enfernt
    private fun stripDuplicates(a: Array<String>, b: Array<String>): Array<String> {
        val stripped = mutableListOf<String>()
        var aIndex = 0
        for (elem in b) {
            var found = false
            for (i in aIndex..a.size-1) {
                if (elem == a[i]) {
                    aIndex = i+1
                    found = true
                    break
                }
            }
            if (!found) {
                stripped.add(elem)
            }
        }
        return stripped.toTypedArray()
    }

    private fun removeLongElements(elements: List<List<String>>): List<List<String>> {
        val shortened = mutableListOf<List<String>>()
        val min = getMinLength(elements)
        for (x in elements) {
            if (x.size == min) {
                shortened.add(x)
            }
        }
        return shortened
    }

    private fun getMinLength(elements: List<List<String>>): Int {
        var min = Integer.MAX_VALUE
        for (x in elements) {
            if (x.size < min) {
                min = x.size
            }
        }
        return min
    }

    private fun removeDoubleElements(variants: List<List<String>>): List<List<String>> {
        val shortened = mutableListOf<List<String>>()
        for (x in variants) {
            val unduplicate = removeDoubleWords(x)
            if (!shortened.contains(unduplicate)) {
                shortened.add(unduplicate)
            }
        }
        return shortened
    }

    private fun removeDoubleWords(x: List<String>): List<String> {
        val unduplicate = mutableListOf<String>()
        var lastWord = ""
        for (word in x) {
            if (word != lastWord) {
                unduplicate.add(word)
            }
            lastWord = word
        }
        return unduplicate
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