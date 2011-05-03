SodaTest: Spreadsheet-Driven Integration Testing
================================================

SodaTest is a framework for Integration and Acceptance testing.

The input format is CSV files, the output format is pretty HTML, and the programming model in between
for creating [fixtures](http://en.wikipedia.org/wiki/Test_fixture#Software) is kept as simple as possible.


Motivation
----------

SodaTest is intended as an alternative to Ward Cunningham's [Framework for Integration Testing, "FIT"](http://fit.c2.com/).
It is a framework that allows the creation of executable test cases in a format that is easily
readable by non-programmers, with the goal of being easily understood, edited or even authored by the
non-technical Customers of the software under test.

The core design of SodaTest focusses around resolving a number of niggles experienced with FIT over the
years, namely:

* HTML as an input format is annoying for developers to manage
* HTML as an input format prevents Customers from getting involved in test writing
* The API is too flexible, bringing the developer too close to the input format and making poor
  practices possible
* The depth of the ecosystem of FIT, fitlibrary and Fitnesse confuses developers trying to achieve
  something simple
* Mixing classes from across the fractured ecosystem results in unresolvable classpath errors
* Formatting test output based on the input formatting results in ugly and inconsistent test output

In order to resolve the input format problems, spreadsheets were chosen (in the form of CSV files, at
present) due to spreadsheets being "the language of business" (not accounting, as some would have you
think.) The simplicity of creating spreadsheets on any platform sealed the deal. This key
differentiation was the motivation for the name choice:
Spreadsheet-Driven Integration Testing  =>  S-D Test  =>  SodaTest

Good ideas froms FIT which are maintained in SodaTest are:

* The use of tables to format and structure lots of information
* Using reflection to automate a lot of string-conversion boilerplate for the fixture author
* HTML as an excellent format for test output

Other things that SodaTest tries to achieve are:

* [Command-Query Separation](http://en.wikipedia.org/wiki/Command-query_separation) is built into the API by making the distinction between Events, which cause
  side-effects within the System, and Reports, which merely query the state of the System.
* Powerful and flexible (yet simple!) coercion of strings to strong types, including support for
  co-located [PropertyEditor](http://download.oracle.com/javase/6/docs/api/java/beans/PropertyEditor.html) implementations
* Simple and localised control of Report formatting from strong types to strings (**Not done yet**)
* Case-agnosticism when binding input strings to programmattic symbols


Project Sections
----------------

The SodaTest project is made up of the following modules:

* 'SodaTest API' is the only module on which your test code should depend at compile-time.
  The org.sodatest.api package contains the traits to be implemented in order to implement fixtures.

* 'SodaTest Coercion' is a module used by the reflection parts of the API to coerce strings into strong
  types

* 'SodaTest Runtime' contains applications that can be used to execute SodaTests.
  The SodaFolderRunner class in the org.sodatest.runtime.processing.running is currently the main
  entry point for running tests.

* 'SodaTest Examples' contains examples of how to use different features of SodaTest


Tasks on the Roadmap
--------------------

A roadmap for features that need to be implemented to make the framework more complete is listed in Roadmap.txt.
If you think you'd like to try your hand at helping out with some of this stuff, get in touch!


Licence
-------

SodaTest is Copyright (c) 2010-2011 Belmont Technology Pty Ltd.

It is licensed under the Apache License, Version 2.0 (the "License")
You may obtain a copy of the License at

    [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


    Copyright (c) 2011 Belmont Technology Pty Ltd. All rights reserved.
