package unit

import controller.Entropy
import org.scalatest.{FlatSpec, Matchers}
import utils.TestHelper

class EntropySpec extends FlatSpec with Matchers {

  val testClass = new Entropy
  val testHelper = new TestHelper

  "Entropy.result" should "use the models provided methods and save the result into a file" in {
    testClass.result()
    val outputFileContent = testHelper.readResultOutputFile()
    assert(outputFileContent.head === "Location entropy of the given dataset with 5 records is 2.321928094887362")

  }

}
