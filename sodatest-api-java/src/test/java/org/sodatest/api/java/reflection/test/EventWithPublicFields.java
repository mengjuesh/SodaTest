/*
 * Copyright (c) 2011 Belmont Technology Pty Ltd. All rights reserved.
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

package org.sodatest.api.java.reflection.test;

import org.sodatest.coercion.Coercion;
import org.sodatest.coercion.java.CoercionRegisterForJava;
import scala.Option;
import scala.reflect.Manifest;

import java.math.BigDecimal;

public class EventWithPublicFields extends EventWithPublicFieldsSuperclass {

    private final CoercionRegisterForJava coercionRegister = new CoercionRegisterForJava(new CustomStringCoercion());

    public Amount amount;
    public Amount anotherAmount;
    public BigDecimal bigDecimal;
    public Option<String> stringOptionOne;
    public Option<String> stringOptionTwo;
    public CustomString stringNeedingCoercion;

    @Override
    protected void executeEvent() {
    }
}
