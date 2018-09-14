package unit

import models.{DataProvider, EarthQuakeRecord, InvalidFormatException}
import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest.{FlatSpec, Matchers}
import utils.TestHelper

class DataProviderSpec extends FlatSpec with Matchers {

  val testClass = new DataProvider
  val testHelper = new TestHelper
  val spark: SparkSession = testHelper.spark

  "CalculatorProvider.entropyCalculation" should "provide a correct entropy calculation or throw an exception" in {
    val result = testClass.dataset("testDataOne.geojson", spark)
    result shouldBe a[Dataset[EarthQuakeRecord]]
  }
  "CalculatorProvider.entropyCalculation" should "throw InvalidFormatException with invalid format" in {
    val domainSpecificException = intercept[InvalidFormatException] {
      testClass.dataset("invalid.geojson", spark)
    }
    assert(domainSpecificException.msg === "The provided record(s) is/are incorrect")
  }

}
