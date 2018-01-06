import common.Configuration
import core.DataFrameGenerator


object Main {
  def main(args: Array[String]) = {
    val dataFile = "src/main/resources/data.txt"
    val configFile = "src/main/resources/config.json"

    val parameters = Configuration.getConfig(configFile)

    val df = DataFrameGenerator.generateDataFrame(parameters,dataFile)

    df.show()
    df.printSchema()
  }
}
