package org.ntic.flights

import org.ntic.flights.data.{Flight, FlightsFileReport, Row}

import scala.util.Try

object FlightsLoaderApp extends App {

  val fileLines: Seq[String] = FileUtils.getLinesFromFile(FlightsLoaderConfig.filePath)
  val rows: Seq[Try[Row]] = FileUtils.loadFromFileLines(fileLines)
  val flightReport: FlightsFileReport = FlightsFileReport.fromRows(rows)
  val flights: Seq[Flight] = flightReport.flights

  val flightsOrigin: Seq[String] = {
    if (FlightsLoaderConfig.useFilteredOrigin)
      FlightsLoaderConfig.filteredOrigin
    else
      flights.map(_.origin.code).distinct
  }

  flightsOrigin.foreach { origin =>
    val filteredFlights: Seq[Flight] = flights.filter(_.origin.code == origin)
    val delayedFlights: Seq[Flight] = filteredFlights.filter(_.isDelayed).sorted
    val notDelayedFlights: Seq[Flight] = filteredFlights.filterNot(_.isDelayed).sorted

    val delayedFlightsObj: String = s"${FlightsLoaderConfig.outputDir}/${origin}_delayed.obj"
    val flightsObj: String = s"${FlightsLoaderConfig.outputDir}/${origin}.obj"

    FileUtils.writeFile(delayedFlights, delayedFlightsObj)
    FileUtils.writeFile(notDelayedFlights, flightsObj)
  }

  println(flightReport)
}
