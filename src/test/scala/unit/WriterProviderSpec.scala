package unit

import models.{LocationEntropyResult, WriterProvider}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Matchers}
import utils.TestHelper

import scala.io.Source

class WriterProviderSpec extends FlatSpec with Matchers {

  val testClass = new WriterProvider
  val testHelper = new TestHelper

  val firstEntResult: LocationEntropyResult = LocationEntropyResult(50, 1.2)
  val secondEntResult: LocationEntropyResult = LocationEntropyResult(1, 0.0)

  val spark: SparkSession = testHelper.spark

  "WriterProvider.create" should "get a result of type LocationEntropyResult " +
    "and create a file with the results given" in {
    testClass.create(firstEntResult)
    val createdFileContent = Source.fromFile("calculated-location-entropy.txt").getLines.toList.head
    assert(createdFileContent == "Location entropy of the given dataset with 50 records is 1.2")

    testClass.create(secondEntResult)
    val repeatedFileContent = Source.fromFile("calculated-location-entropy.txt").getLines.toList.head
    assert(repeatedFileContent === "Location entropy of the given dataset with 1 records is 0.0")
  }

}
