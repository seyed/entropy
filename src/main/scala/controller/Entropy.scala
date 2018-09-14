package controller

import models.{CalculateProvider, DataProvider, WriterProvider}
import org.apache.spark.sql.SparkSession

class Entropy {

  def result(): Unit = {
    val dataProvider: DataProvider = new DataProvider
    val calculateProvider: CalculateProvider = new CalculateProvider
    val writerProvider: WriterProvider = new WriterProvider
    val filePath = "earthquake-record.geojson"

    val spark: SparkSession = SparkSession.builder
      .appName("Entropy Calculator")
      .master("local")
      .getOrCreate()

    writerProvider.create(
      calculateProvider.location(
        spark, dataProvider.dataset(filePath, spark)
      )
    )
  }

}
