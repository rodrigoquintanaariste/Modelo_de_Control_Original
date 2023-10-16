package com.santander.cloud.sdh.proyecto.repositorio
import org.apache.spark.sql.SparkSession
import utils.ReadProperties.loadPropertiesFile

object App {

  def main(args: Array[String]): Unit = {


    //#################### Testing load properties ####################
    val properties = loadPropertiesFile()
    val database_name = properties.getProperty("database_name") // read environment variable
    val snow_user = properties.getProperty("snowflake_user") // read sensible variable
    val schema_business = properties.getProperty("SCHEMA_BUSINESS") //read constants variable

    println("---- Leyendo variables de mis ficheros sensible/constants/environment ----")
    println(database_name)
    println(snow_user)
    println(schema_business)





    //#################### Read input parameters ####################
    var argumento1="No introducido parámetro"
    if (args.length != 0) {
      argumento1=args(0)
    }
    println("---- Leyendo parámetros de entrada si existen  ----")
    println(argumento1)





    //#################### Configure and testing Spark ####################

    val sparkSession = (System.getenv("environment")) match {
      case "local" =>
        SparkSession
          .builder()
          .master("local[1]")
          .appName("repositorio")
          .config("fs.azure", "org.apache.hadoop.fs.azure.NativeAzureFileSystem") //necesary for local sta
          .config("spark.cosmos.useGatewayMode", "true") //necesary for local cosmosdb
          .config("spark.cosmos.disableTcpConnectionEndpointRediscovery", "true") //necesary for local cosmosdb
          .getOrCreate()
      case _ =>
        SparkSession
          .builder()
          .appName("repositorio")
          .getOrCreate()
    }
    import sparkSession.implicits._
    val columns = Seq("Id","Pais")
    val data = Seq(("1", "España"), ("2", "Portugal"), ("3", "Argentina"))
    val dfFromData1 = data.toDF(columns:_*)
    println("---- Probando a configurar spark y crear un Dataframe  ----")
    dfFromData1.show()





  }
}
