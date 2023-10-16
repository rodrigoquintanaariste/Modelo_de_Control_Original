package utils

import java.util.Properties
import scala.util.Try

object ReadProperties {

  // Function to read properties
  def loadPropertiesFile(): Properties = {
    val properties = new Properties()
    Try {
      val environment = System.getenv("environment")
      properties.load(this.getClass.getResourceAsStream("/" + environment + ".properties"))
      properties.load(this.getClass.getResourceAsStream("/constants.properties"))
      if (environment == "local") {
        properties.load(this.getClass.getResourceAsStream("/" + "sensible.properties"))
      }
    }
    properties
  }

}
