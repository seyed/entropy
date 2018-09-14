package models

import javax.inject.Singleton
import org.apache.spark.sql.{Dataset, SparkSession}

case class InvalidFormatException(msg: String) extends Exception


case class Property(mag: Double, place: String, time: Long, updated: Long, tz: Long, url: String,
                    detail: String, felt: String, cdi: String, mmi: String, alert: String,
                    status: String, tsunami: Long, sig: Long, net: String, code: String,
                    ids: String, sources: String, types: String, nst: String, dmin: String,
                    rms: String, gap: String, magType: String, `type`: String)

case class Geometry(`type`: String = "Point", coordinates: List[Double])

case class EarthQuakeRecord(`type`: String = "Feature", properties: Property, geometry: Geometry, id: String)


@Singleton
class DataProvider {

  def dataset(filePath: String, spark: SparkSession): Dataset[EarthQuakeRecord] = {
    import spark.implicits._
    spark.implicits.newDoubleSeqEncoder
    try {
      spark.read.json(filePath).as[EarthQuakeRecord]
    } catch {
      case _: Throwable => throw InvalidFormatException("The provided record(s) is/are incorrect")
    }
  }
}