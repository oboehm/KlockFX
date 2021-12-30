/*
 * Copyright (c) 2021 by Oliver Boehm
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
 * (c)reated 30.12.21 by oliver (ob@oasd.de)
 */
package klock.fx

/**
 * Diese Klasse fungiert als Starter fuer die eigentliche JavaFX-Application.
 * Laut https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
 * muessen Main-Klasse und JavaFX-Application wegen eines Fehlers im
 * sun.launcher.LauncherHelper getrennt sein.
 */
class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            System.out.println("Starting KlockFX...")
            KlockFX.main(args)
        }
    }

}