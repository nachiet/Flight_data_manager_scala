package org.ntic.flights.data

import scala.util.Try

/**
 * This class is used to represent a report of the flights file with the valid rows, invalid rows and the flights
 * extracted from the valid rows.
 * @param validRows: Seq[Row]
 * @param invalidRows: Seq[String]
 * @param flights: Seq[Flight]
 */
case class FlightsFileReport(validRows: Seq[Row],
                             invalidRows: Seq[String],
                             flights: Seq[Flight]
                            ) {

  override val toString: String = {
    // TODO: Se puede mejorar
    val valid_rows = validRows.size
    val invalid_rows = invalidRows.size

    val error_summary = invalidRows
      .groupBy(_.getClass.getSimpleName)
      .map(x => s"<${x._1}>: ${x._2.size}" ).mkString("\n")

    s"Flights report:" +
      s"  - $validRows valid rows" +
      s"  - $invalidRows invalid rows" +
      s"Error summary:" +
      s"$error_summary"
  }
}

object FlightsFileReport {
  /**
   * This function is used to create a FlightsFileReport from a list of Try[Row] objects where each Try[Row] represents a row
   * loaded from the file. If the row is valid, it is added to the validRows list, otherwise the error message is added to
   * the invalidRows list. Finally, the valid rows are converted to Flight objects and added to the flights list.
   *
   * @param rows: Seq[Try[Row]]
   * @return FlightsFileReport
   */
  def fromRows(rows: Seq[Try[Row]]): FlightsFileReport = {
    val valid_rows = rows.filter(_.isSuccess).map(_.get)
    val invalid_rows = rows.filter(_.isFailure).map(_.failed.get.getMessage)
    val flights = valid_rows.map(Flight.fromRow)

    FlightsFileReport(valid_rows, invalid_rows, flights)
  }
}
