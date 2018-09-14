package models

import java.io.PrintWriter

class WriterProvider {

  def create(result: LocationEntropyResult): Unit = {
    new PrintWriter("calculated-location-entropy.txt") {
      write(s"Location entropy of the given dataset with ${result.numOfRecords} records is ${result.locationEntropy}")
      close()
    }
  }

}
