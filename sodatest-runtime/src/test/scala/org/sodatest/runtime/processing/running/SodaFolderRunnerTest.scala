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

package org.sodatest.runtime.processing.running

import java.io.File
import xml.factory.XMLLoader
import xml.Elem
import org.sodatest.runtime.processing.SodaTestProperties
import org.sodatest.runtime.ConsoleLog
import org.junit.{After, Test}

class SodaFolderRunnerTest extends XMLLoader[Elem] {

  val systemTempDir = new File(System.getProperty("java.io.tmpdir"))
  val tempFile = File.createTempFile("SodaFolderRunnerTest.", ".tmp", systemTempDir)
  tempFile.deleteOnExit()

  implicit val properties = new SodaTestProperties()
  implicit val log = ConsoleLog()

  @Test(expected = classOf[InvalidDirectoryException])
  def nonExistentInputDirectory() {
    new SodaFolderRunner(null, null).run(new File(systemTempDir, "DOESNOTEXIST.sodatest"), systemTempDir, (b) => {})
  }

  @Test(expected = classOf[InvalidDirectoryException])
  def nonDirectoryInputDirectory() {
    new SodaFolderRunner(null, null).run(tempFile, systemTempDir, (b) => {})
  }

    // TODO: Does this work on Unix?
//  @Test(expected = classOf[InvalidDirectoryException])
//  def unreadableInputDirectory() {
//    changeTempFileToDirectory()
//    tempFile.setReadable(false)
//    Assert.assertFalse(tempFile.canRead)
//    new SodaFolderRunner(null, null).run(tempFile, systemTempDir, (b) => {})
//  }

  @Test(expected = classOf[InvalidDirectoryException])
  def nonDirectoryOutputFolder() {
    new SodaFolderRunner(null, null).run(systemTempDir, tempFile, (b) => {})
  }

    // TODO: Does this work on Unix?
//  @Test(expected = classOf[InvalidDirectoryException])
//  def nonCreatableOutputFolder() {
//    changeTempFileToDirectory()
//    Assert.assertTrue(tempFile.setWritable(false))
//    Assert.assertFalse(tempFile.canWrite)
//    new SodaFolderRunner(null, null).run(systemTempDir, new File(tempFile, "subdir"), (b) => {})
//  }

  private def changeTempFileToDirectory(): Unit = {
    tempFile.delete
    tempFile.mkdir
  }

  @After
  def removeTempFile(): Unit = {
    tempFile.setWritable(true);
    tempFile.delete
  }
}

