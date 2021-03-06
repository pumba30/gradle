/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.play.tasks

import org.gradle.test.fixtures.file.TestFile

abstract class AbstractCoffeeScriptCompileIntegrationTest extends AbstractJavaScriptMinifyIntegrationTest {

    TestFile compiled(String fileName) {
        return compiled(getDefaultSourceSet(), fileName)
    }

    TestFile compiled(String sourceSet, String fileName) {
        file("build/playBinary/src/compilePlayBinary${sourceSet}/${fileName}")
    }

    void matchesExpectedRaw(String fileName) {
        matchesExpectedRaw(getDefaultSourceSet(), fileName)
    }

    void matchesExpectedRaw(String sourceSet, String fileName) {
        assert compiled(sourceSet, fileName).exists()
        matchesExpectedRaw(compiled(sourceSet, fileName))
    }

    @Override
    TestFile copied(String fileName) {
        return super.copied("${defaultSourceSet}JavaScript", fileName)
    }

    @Override
    void matchesExpected(String fileName) {
        super.matchesExpected("${defaultSourceSet}JavaScript", fileName)
    }

    @Override
    TestFile minified(String fileName) {
        return super.minified("${defaultSourceSet}JavaScript", fileName)
    }

    def withCoffeeScriptSource(String path) {
        withCoffeeScriptSource(file(path))
    }

    def withCoffeeScriptSource(File file) {
        file << coffeeScriptSource()
    }

    def coffeeScriptSource() {
        return """
# Assignment:
number   = 42
opposite = true

# Conditions:
number = -42 if opposite

# Functions:
square = (x) -> x * x

# Arrays:
list = [1, 2, 3, 4, 5]

# Objects:
math =
  root:   Math.sqrt
  square: square
  cube:   (x) -> x * square x

# Splats:
race = (winner, runners...) ->
  print winner, runners

# Existence:
alert "I knew it!" if elvis?

# Array comprehensions:
cubes = (math.cube num for num in list)"""
    }
}
