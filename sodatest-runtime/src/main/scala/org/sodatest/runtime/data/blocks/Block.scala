/*
 * Copyright (c) 2010-2011 Belmont Technology Pty Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sodatest.runtime
package data.blocks

import processing.execution.SodaTestExecutionContext
import data.results.BlockResult
abstract class Block(val source: BlockSource, val name: String, val inline: Boolean) {
  def execute(context: SodaTestExecutionContext): BlockResult[_]

  override def toString = String.format("%-15s [Line %s] %s", getClass.getSimpleName, source.lines(0).lineNumber.toString, name)
}

abstract class ParamterisedBlock(source: BlockSource, name: String, inline: Boolean, val parameterNames: List[String])
extends Block(source, name, inline) {
  def parameterMap(execution: ParameterValuesContainer): Map[String, String] = {
    execution.parameterValues match {
      case Some(line) => {
        if (line.cells.tail.size > parameterNames.size)
          error("There are more parameter values than parameter names. This should be picked up when parsing.")
        else
          (parameterNames.zipAll(line.cells.tail, "", "")) toMap
      }
      case None => Map()
    }
  }
}

