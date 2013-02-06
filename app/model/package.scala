package model

import play.api.libs.json._
import play.api.libs.json.Json

package object model {
  case class User(id:String,name:String,city:Option[Position],email:String)
  case class Position(lat:BigDecimal, lng:BigDecimal){}
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
