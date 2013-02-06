package model

import play.api.libs.json.Json

package object model {
  case class Position(lat:BigDecimal, lng:BigDecimal){}
  case class Artist(val id:String, val name:String, val position:Position){}
  case class Concert(val position:Position){}

  object Formats {
    implicit val positionReads=Json.reads[Position]
    implicit val positionWrites=Json.writes[Position]
    implicit val artistReads=Json.reads[Artist]
    implicit val artistWrites=Json.writes[Artist]
    implicit val concertReads=Json.reads[Concert]
    implicit val concertWrites=Json.writes[Concert]
  }
}
