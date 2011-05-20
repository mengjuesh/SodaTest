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

package org.sodatest.api { package reflection {

import collection.immutable.Map

/**
 * [[org.sodatest.api.SodaEvent]] base class that supports the automatic binding of parameters to
 * public and strongly-typed vars, fields or setter methods.
 *
 * ReflectiveSodaEvent is probably the easiest way to implement the [[org.sodatest.api.SodaEvent]] trait.
 * It performs the tasks of both coercing and binding the string parameter values supplied in the parameter
 * map and allows the subclass to implement the `apply()` method that simply executes the Event.
 *
 * Parameters are discovered on subclasses by canonizing the names in the parameter map and looking
 * for members of the subclass which, when canonized, match the parameter name, according to the
 * following precedence:
 * <ol>
 *   <li>public Scala assignment methods, e.g. `def parameterName_$eq(_): Unit` (as generated by `var parameterName: T`)</li>
 *   <li>public setter methods, e.g. `public void setParameterName(T parameterName)`</li>
 *   <li>public fields, e.g. `public T parameterName`</li>
 * </ol>
 *
 * Note that when you declare a public `var` in Scala, the compiler automatically generates the
 * public assignment method.
 *
 * Once a parameter target is discovered, the parameter value is then coerced to the type of the
 * parameter using [[org.sodatest.coercion.Coercion$]]. Should the Event need to make custom [[org.sodatest.coercion.Coercion]]s
 * available to the coercion funciton, a [[org.sodatest.coercion.CoercionRegister]] field can be defined
 * within the Event which will be discovered and passed into the coercion.
 *
 * (Names in SodaTest are canonized by removing all non-alpha-numeric characters and
 * converting all alpha characters to lower-case. e.g. canonized("Secret Event  #2") -> "secretevent2")
 *
 * <b>Example</b>
 * {{{
 * class MySodaEvent extends ReflectiveSodaEvent {
 *    var amount: BigDecimal = null
 *
 *   def apply(): Unit = {
 *     ... // Execute the event on the System, making use of 'amount'
 *   }
 * }
 * }}}
 */
trait ReflectiveSodaEvent extends SodaEvent {
  /**
   * Executes this Event on the System under test using the parameters that have been coerced and
   * bound by reflection into the members of this instance.
   */
  protected def apply(): Unit

  /**
   * Coerces and binds the parameters to this Event, then delegates to the [[apply()]] function.
   *
   * You can override this method if you want to perform some operation before the binding
   * or after the execution of the Event, calling super.apply(parameters) at the appropriate point.
   *
   */
  @throws(classOf[ParameterBindingException])
  def apply(parameters: Map[String, String]) = {
    ReflectionUtil.setByReflection(parameters, this)
    apply()
  }
}

}}