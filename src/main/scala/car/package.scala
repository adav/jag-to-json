import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source

package object car {

  case class LatLng(longitude: Double, latitude: Double)
  case class JaguarJourneyWaypoint(position: LatLng)

  case class GeoJsonFeatureCollection(features: Vector[GeoJsonFeature], `type`: String = "FeatureCollection")
  case class GeoJsonFeature(geometry: GeoJsonLineStringGeometry, `type`: String = "Feature")
  case class GeoJsonLineStringGeometry(coordinates: Vector[Vector[Double]], `type`: String = "LineString")

  def getJourneyIds(filename: String): Vector[String] = {
    val bufferedIds = Source.fromFile(filename)
    try {
      bufferedIds.getLines().toVector
    } finally {
      bufferedIds.close()
    }
  }

  def getJaguarJourneyString(filename: String): String = {
    val bufferedIds = Source.fromFile(filename)
    try {
      bufferedIds.getLines().mkString
    } finally {
      bufferedIds.close()
    }
  }

  def writeFile(filename: String, s: String): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(s)
    bw.close()
  }
}
