package org.ntic.flights

import org.ntic.flights.data.{Flight, Row}

import java.io.{FileOutputStream, ObjectOutputStream}
import scala.io.Source
import scala.util.Try

object FileUtils {

  /**
   * This function is used to check if the line is valid or not
   * @param s: String
   * @return Boolean: true if the line is invalid, false otherwise
   */
  def isInvalidLine(s: String): Boolean = {
    if (s.isEmpty | s.split(FlightsLoaderConfig.delimiter).length != FlightsLoaderConfig.headersLength)
      true
    else
      false
  }

  /**
   * This function is used to read the file located in the path `filePath` and return a list of lines of the file
   *
   * @param filePath: String
   * @return List[String]
   */
  def getLinesFromFile(filePath: String): List[String] = {
    val src = Source.fromFile(filePath)
    src.getLines().toList
  }

  /**
   * This function is used to load the rows from the file lines
   *
   * @param fileLines: Seq[String]
   * @return Seq[Try[Row]]
   */
  def loadFromFileLines(fileLines: Seq[String]): Seq[Try[Row]] = {
    if (FlightsLoaderConfig.hasHeaders)
      fileLines.tail.map(x => Row.fromStringList(x.split(FlightsLoaderConfig.delimiter).toIndexedSeq))
    else
      fileLines.map(x => Row.fromStringList(x.split(FlightsLoaderConfig.delimiter).toIndexedSeq))
  }

  def writeFile(flights: Seq[Flight], outputFilePath: String): Unit = {
    val out = new ObjectOutputStream(new FileOutputStream(outputFilePath))
    out.writeObject(flights)
    out.close()
  }

}