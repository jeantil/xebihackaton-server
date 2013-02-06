package model

import play.api.libs.json._
import play.api.libs.json.Json

package object model {
  case class User(id:String,name:String,city:Option[Position],email:String)
  case class Position(lat:BigDecimal, lng:BigDecimal){
    def distance(other:Position){
      def toRad(number:BigDecimal)={
        number * Math.PI / 180
      }
      // default 4 sig figs reflects typical 0.3% accuracy of spherical model

      val R = 6371; //earth radius in km
      val lat1 = toRad(lat)
      val lon1 = toRad(lng)
      val lat2 = toRad(other.lat)
      val lon2 = toRad(other.lng)
      val dLat= (lat2 - lat1).toDouble
      val dLon = (lon2 - lon1).toDouble

      val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(lat1.toDouble) * Math.cos(lat2.toDouble) *
          Math.sin(dLon / 2) * Math.sin(dLon / 2)

      val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

      val d = R * c
      BigDecimal(d)
    }
  }
  case class Artist(id:String,name:String,position:Position){}
  case class Concert(position:Position){}

  object Formats {
    implicit val positionReads=Json.reads[Position]
    implicit val positionWrites=Json.writes[Position]

    implicit val userReads=Json.reads[User]
    implicit val userWrites=Json.writes[User]

    implicit val artistReads=Json.reads[Artist]
    implicit val artistWrites=Json.writes[Artist]

    implicit val concertReads=Json.reads[Concert]
    implicit val concertWrites=Json.writes[Concert]
  }
}
