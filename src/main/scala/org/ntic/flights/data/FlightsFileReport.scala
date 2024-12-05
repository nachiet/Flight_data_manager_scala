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

    val validRowsCount: Int = validRows.length
    val invalidRowsCount: Int = invalidRows.length

    val errorSummary: String = invalidRows
      .groupBy(x => x)
      .map(x => s"<${x._1}>: ${x._2.length}" )
      .mkString("\n")

    s"""|FlightsFileReport:
        |  - $validRowsCount valid rows.
        |  - $invalidRowsCount invalid rows.
        |Error summary:
        |$errorSummary
    """.stripMargin
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
    val valid_rows: Seq[Row] = rows.filter(_.isSuccess).map(_.get)
    val invalid_rows: Seq[String] = rows.filter(_.isFailure).map(_.failed.get.toString)
    val flights: Seq[Flight] = valid_rows.map(Flight.fromRow)

    FlightsFileReport(valid_rows, invalid_rows, flights)
  }
}
