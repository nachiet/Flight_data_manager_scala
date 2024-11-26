package org.ntic.flights

import org.ntic.flights.data.{Flight, FlightsFileReport, Row}

import scala.util.Try

object FlightsLoaderApp extends App {

  val fileLines: Seq[String] = FileUtils.getLinesFromFile(FlightsLoaderConfig.filePath)
  val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(fileLines)
  val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)
  val flights: Seq[Flight] = flightReport.flights

  flights.map(_.origin).distinct.foreach { origin =>
      val filteredFlights: Seq[Flight] = flights.filter(_.origin == origin)
      val delayedFlights: Seq[Flight] = filteredFlights.filter(_.isDelayed).sorted
      val notDelayedFlights: Seq[Flight] = filteredFlights.filterNot(_.isDelayed).sorted

      val delayedFlightsObj: String = s"${FlightsLoaderConfig.outputDir}/${origin.code}_delayed.obj"
      val flightsObj: String = s"${FlightsLoaderConfig.outputDir}/${origin.code}.obj"

      FileUtils.writeFile(delayedFlights, delayedFlightsObj)
      FileUtils.writeFile(notDelayedFlights, flightsObj)
  }
}
