package org.ntic.flights

import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters.ListHasAsScala

object FlightsLoaderConfig {
  val config: Config = ConfigFactory.load().getConfig("flightsLoader")
  val filePath: String = config.getString("filePath")
  val hasHeaders: Boolean = config.getBoolean("hasHeaders")
  val delimiter: String = config.getString("delimiter")
  val outputDir: String = config.getString("outputDir")
  val useFilteredOrigin: Boolean = config.getBoolean("useFilteredOrigin")
  val headers: List[String] = config.getStringList("headers").asScala.toList
  val headersLength: Int = headers.length
  val columnIndexMap: Map[String, Int] = headers.map(x => (x, headers.indexOf(x))).toMap
  val filteredOrigin: List[String] = config.getStringList("filteredOrigin").asScala.toList

}
