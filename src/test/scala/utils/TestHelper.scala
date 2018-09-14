package utils

import models.EarthQuakeRecord
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.io.Source


class TestHelper {

  def spark: SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("test")
      .getOrCreate()
  }


  def dataProviderHelper(filePath: String, spark: SparkSession): Dataset[EarthQuakeRecord] = {
    import spark.implicits._
    spark.implicits.newDoubleSeqEncoder

    spark.read.json(s"$filePath.geojson").as[EarthQuakeRecord]
  }

  def readResultOutputFile(): List[String] = Source.fromFile("calculated-location-entropy.txt").getLines.toList

}
