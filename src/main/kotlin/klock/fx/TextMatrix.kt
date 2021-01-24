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

class TextMatrix {

    private val klock = TextKlock

    fun getWords() : Set<String> {
        val words = mutableSetOf<String>()
        for (s in klock.getAllTimes()) {
            words.addAll(s.split(' '));
        }
        return words
    }

    fun getWordsWeighted() : Map<Int, Set<String>> {
        val weighted = mutableMapOf<Int, Set<String>>()
        val splitted = getTimesAsWordLists()
        for (s in klock.getAllTimes()) {
            val words = (s.split(' '));
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
            val words = s.split(' ');
            val i = words.size
            val set = splitted.getOrDefault(i, mutableSetOf())
            splitted.put(i, set.plus(listOf(words)))
        }
        return splitted
    }

    fun getMaxTimeLengthInWords() : Int {
        var max = 1
        for (s in klock.getAllTimes()) {
            val n = s.split(' ').size;
            if (n > max) {
                max = n
            }
        }
        return max;
    }

}