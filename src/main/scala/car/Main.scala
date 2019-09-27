package car
import io.circe.generic.auto._
import io.circe.parser._
import io.circe._
import io.circe.syntax._


object Main extends App {

  val journeysAsWaypoints: Vector[Either[Error, Vector[JaguarJourneyWaypoint]]] = getJourneyIds("/Users/adav/Documents/jaguar_data/journeyIds.txt")
    .map(id => getJaguarJourneyString(s"/Users/adav/Documents/jaguar_data/${id}.json-XGET"))
    .map(decode[Vector[JaguarJourneyWaypoint]])


  val geojsons = journeysAsWaypoints.map { journeyWaypoints =>
    journeyWaypoints.map { waypoints =>
      val coordinates = waypoints.map(waypoint => Vector(waypoint.position.longitude, waypoint.position.latitude))
      GeoJsonFeature(GeoJsonLineStringGeometry(coordinates))
    }
  }

  val featureCollection = GeoJsonFeatureCollection(geojsons.collect {
    case Right(g: GeoJsonFeature) => g
  })

  val jsonString = featureCollection.asJson.printWith(Printer.noSpaces)
  writeFile("/Users/adav/Documents/jaguar_data/all_journeys.json", jsonString)
}
