package org.ntic.flights.data

import com.typesafe.config.ConfigFactory

/**
 * This class is used to represent a flight with its information like the date, origin, destination, scheduled departure time,
 * scheduled arrival time, departure delay and arrival delay.
 *
 * @param flDate: String
 * @param origin: Airport
 * @param dest: Airport
 * @param scheduledDepTime: Time
 * @param scheduledArrTime: Time
 * @param depDelay: Double
 * @param arrDelay: Double
 */
case class Flight (flDate: String,
                                          origin: Airport,
                                          dest: Airport,
                                          scheduledDepTime: Time,
                                          scheduledArrTime: Time,
                                          depDelay: Double,
                                          arrDelay: Double) extends Ordered[Flight] {
  lazy val flightDate: FlightDate = FlightDate.fromString(flDate)

  lazy val actualDepTime: Time = Time.fromMinutes(scheduledDepTime.asMinutes + depDelay.toInt)

  lazy val actualArrTime: Time = Time.fromMinutes(scheduledArrTime.asMinutes + arrDelay.toInt)

  val isDelayed: Boolean = if (depDelay != 0 || arrDelay != 0) true else false

  override def compare(that: Flight): Int = actualArrTime - that.actualArrTime
}
