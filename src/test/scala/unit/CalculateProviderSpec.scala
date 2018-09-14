package unit

import utils.TestHelper
import models.{CalculateProvider, EarthQuakeRecord, LocationEntropyResult}
import org.apache.spark.sql.{Dataset, SparkSession}
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks

class CalculateProviderSpec extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  val testClass = new CalculateProvider
  val testHelper = new TestHelper
  val spark: SparkSession = testHelper.spark

  "CalculatorProvider.entropyCalculation" should "provide a correct entropy calculation " in {

    val one: Long = 1
    val five: Long = 5
    val twentyFive: Long = 25
    val hundredTwentryFive: Long = 125

    val onceOccurrenceList = List(one)
    val twoOccurrencesList = List(five, five)
    val threeOccurrencesList = List(five, five, five)
    val fourOccurrencesList = List(twentyFive, twentyFive, twentyFive, twentyFive)
    val eightOccurrencesList = List(hundredTwentryFive, hundredTwentryFive,
      hundredTwentryFive, hundredTwentryFive, hundredTwentryFive,
      hundredTwentryFive, hundredTwentryFive, hundredTwentryFive)

    Table(
      ("total number", "occurrence list", "expected result"),
      (1, onceOccurrenceList, 0),
      (10, twoOccurrencesList, 1.0),
      (15, threeOccurrencesList, 1.5849625007211559),
      (100, fourOccurrencesList, 2.0),
      (1000, eightOccurrencesList, 3.0)

    ) forEvery {
      (totalNumber, occurrenceList, expectedResult) => {
        testClass.entropyCalculation(totalNumber, occurrenceList) shouldEqual expectedResult
      }
    }

  }


  "CalculatorProvider.groupedOccurrences" should "provide a List[Long] based on given dataset " in {

    val one: Long = 1
    val hundred: Long = 100


    val firstDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataOne", spark)
    val secondDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataTwo", spark)

    Table(
      ("data set", "expected list"),
      (firstDataSet, List(one)),
      (secondDataSet, List(hundred))

    ) forEvery {
      (dataset, expectedList) => {
        testClass.groupedOccurrences(testHelper.spark, dataset) shouldEqual expectedList
      }
    }

  }

  "CalculatorProvider.recordsNumber" should "provide the correct total amount of records" in {

    val one: Long = 1
    val hundred: Long = 100

    val firstDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataOne", spark)
    val secondDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataTwo", spark)

    Table(
      ("data set", "expected result"),
      (firstDataSet, one),
      (secondDataSet, hundred)

    ) forEvery {
      (dataset, expectedList) => {
        testClass.recordsNumber(dataset) shouldEqual expectedList
      }
    }
  }

  "CalculatorProvider.location" should "provide the result of type LocationEntropyResult" in {

    val one: Long = 1
    val hundred: Long = 100


    val firstDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataOne", spark)
    val secondDataSet: Dataset[EarthQuakeRecord] = testHelper.dataProviderHelper("testDataTwo", spark)

    Table(
      ("data set", "expected result"),
      (firstDataSet, LocationEntropyResult(one, 0.0)),
      (secondDataSet, LocationEntropyResult(hundred, 0.0))
    ) forEvery {
      (dataset, expectedList) => {
        testClass.location(spark, dataset) shouldEqual expectedList
      }
    }

  }


}
