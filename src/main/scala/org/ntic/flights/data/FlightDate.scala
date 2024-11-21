package org.ntic.flights.data

import com.sun.media.sound.InvalidFormatException

/**
 * This class is used to represent a date of a flight
 * @param day: Int
 * @param month: Int
 * @param year: Int
 */
case class FlightDate(day: Int,
                      month: Int,
                      year: Int) {

  override lazy val toString = f"$day%02d/$month%02d/$year"
}

object FlightDate {
  /**
   * This function is used to convert a string to a FlightDate
   * @param date: String
   * @return FlightDate
   */
  def fromString(date: String): FlightDate = {
    date.split(" ").head.split("/").map(x => x.toInt).toList match {
      case List(month, day, year) =>  if (year >= 1987
        && month <= 12 && month >= 0
        && day <= 31 && day >= 0 )
          FlightDate(day, month, year)
      else
        throw new AssertionError(s"Fecha inválida: día, mes o año no son correctos. Entrada: $date.")

      case _ => throw new InvalidFormatException(s"$date tiene un formato inválido")
    }
  }
}
