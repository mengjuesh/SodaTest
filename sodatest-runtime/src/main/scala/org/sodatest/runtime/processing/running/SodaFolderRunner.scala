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
package processing
package running

import data.results.{EventBlockResult, ReportBlockResult, SodaTestResult}
import java.io.File
import collection.immutable.List
import formatting.xhtml.{XhtmlIndexFileSummaryWriter, XhtmlSodaTestResultWriter}
import formatting.console.ConsoleResultSummaryWriter

class InvalidDirectoryException(message: String) extends IllegalArgumentException(message)

class SodaFolderRunner(val resultWriter: SodaTestResultWriter, val resultSummaryWriters: Seq[SodaTestResultSummaryWriter]) {

  @throws(classOf[InvalidDirectoryException])
  def runTestsAndWriteResults(inputRoot: File, outputRoot: File, successCallback: (Boolean) => Unit)(implicit context: SodaTestContext): Unit = {
    checkDirectories(inputRoot, outputRoot)

    val files = PathUtils.collectFilesRecursive(inputRoot, _.getName.toLowerCase.endsWith(".csv"))

    resultWriter.createOutputDirectories(inputRoot, files, outputRoot)

    val filesAndResults = files.map(f => (f, SodaFileRunner.runTest(f)))

    resultWriter.writeResultsFiles(filesAndResults, inputRoot, outputRoot)

    val resultsSummaries = SodaTestResultSummary.summariseList(filesAndResults.map(_._2))

    resultSummaryWriters.map(_.writeSummaries(resultsSummaries, inputRoot, outputRoot))

    val succeeded = !resultsSummaries.map(r => r.mismatchCount == 0 && r.errorCount == 0).contains(false)
    successCallback(succeeded)
  }

  @throws(classOf[InvalidDirectoryException])
  private def checkDirectories(inputDirectory: File, outputDirectory: File): Unit = {
    if (!inputDirectory.exists)
      throw new InvalidDirectoryException("Input directory " + inputDirectory.getAbsolutePath + " does not exist")

    if (!inputDirectory.isDirectory)
      throw new InvalidDirectoryException("Input directory " + inputDirectory.getAbsolutePath + " is not a directory")

    if (!inputDirectory.canRead)
      throw new InvalidDirectoryException("Insufficient permissions to read input directory " + inputDirectory.getAbsolutePath)

    if (!outputDirectory.exists && !outputDirectory.mkdirs)
      throw new InvalidDirectoryException("Failed to create output directory " + outputDirectory.getAbsolutePath)

    if (!outputDirectory.isDirectory)
      throw new InvalidDirectoryException("Output directory " + inputDirectory.getAbsolutePath + " is not a directory")
  }
}

object SodaFolderRunner {

  def main(args: Array[String]): Unit = {
    main(args, succeeded => { exit(if (succeeded) 0 else 1) } );
  }

  def main(args: Array[String], successCallback: (Boolean) => Unit): Unit = {
    if (args.length != 3) {
      usage()
      successCallback(false)
    } else {
      val fixtureRoot = args(0)
      val inputDirectory = new File(args(1))
      val outputDirectory = new File(args(2))
      implicit val context = new SodaTestContext(fixtureRoot)

      try {
        new SodaFolderRunner(XhtmlSodaTestResultWriter, List(ConsoleResultSummaryWriter,  XhtmlIndexFileSummaryWriter))
          .runTestsAndWriteResults(inputDirectory, outputDirectory, successCallback)
      }
      catch {
        case e: InvalidDirectoryException => usage(Some("Error: " + e.getMessage)); successCallback(false)
      }
    }
  }

  private def usage(): Unit = usage(None)

  private def usage(message: Option[String]): Unit = {
    message map {System.err.println(_)}
    System.err.println("usage: SodaFolderRunner <fixture_root_package> <input_directory> <output_directory>")
  }
}