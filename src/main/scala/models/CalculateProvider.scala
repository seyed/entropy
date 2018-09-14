package models

import org.apache.spark.sql.{Dataset, SparkSession}
import scala.math.log10

case class GroupedRecords(coordinates: Array[Double], count: Long)

case class LocationEntropyResult(numOfRecords: Long, locationEntropy: Double)

class CalculateProvider {

  def location(spark: SparkSession, ds: Dataset[EarthQuakeRecord]): LocationEntropyResult = {
    val totalNumber = recordsNumber(ds)
    val occurrenceList = groupedOccurrences(spark, ds)
    val locationEntropy = entropyCalculation(totalNumber, occurrenceList)

    LocationEntropyResult(totalNumber, locationEntropy)
  }

  def recordsNumber(ds: Dataset[EarthQuakeRecord]): Long = ds.count()

  def groupedOccurrences(spark: SparkSession, ds: Dataset[EarthQuakeRecord]): List[Long] = {
    import spark.implicits._

    ds.groupBy("geometry.coordinates").count().as[GroupedRecords]
      .toDF().select("count").map(row => row.getLong(0)).collect.toList
  }

  def entropyCalculation(totalNumber: Long, occurrenceList: List[Long]): Double =
    occurrenceList.map {
      occurrenceNumber =>
        val pi = occurrenceNumber / totalNumber.toDouble
        pi * log10(pi) / log10(2.0)
    }.foldLeft(0.0)(_ + _) * -1

}
