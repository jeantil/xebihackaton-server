package controllers

import lib.mvc.AuthenticatedAction
import play.api.mvc._
import play.api.libs.json.Json._

import model.{Artist, Position}
import model.Formats._


trait Artists extends Controller {
  def listmap(lat:Double, long:Double, radius:Double)= AuthenticatedAction { request =>
    val latitude=BigDecimal(lat)
    val longitude=BigDecimal(long)
    val rad=BigDecimal(radius)

    val ref=Position(latitude, longitude)
    val artists=Seq(
     Artist("1", "Justin Bieber" ,Position(48.8753,2.3112))
    ,Artist("2", "Marylin Manson" ,Position(48.8760,2.3134))
    ,Artist("3", "France Gall" ,Position(48.8804,2.3344))
    ,Artist("4", "Shakira" ,Position(48.8560,2.3321))
    ,Artist("5", "Lady GaGa" ,Position(48.8760,2.3134))
    ,Artist("6", "Psy" ,Position(48.866,2.3111))
    ,Artist("7", "Amadeus Mozart" ,Position(49.8760,2.3224))
    ).filter { artist =>  ref.distance(artist.position) <= rad }
    Ok(toJson(artists))
  }

  def startingWith(query:String) = AuthenticatedAction{ request =>

    val artists=Seq(
      Artist("1", "Justin Bieber" ,Position(48.8753,2.3112))
      ,Artist("2", "Marylin Manson" ,Position(48.8760,2.3134))
      ,Artist("3", "France Gall" ,Position(48.8804,2.3344))
      ,Artist("4", "Shakira" ,Position(48.8560,2.3321))
      ,Artist("5", "Lady GaGa" ,Position(48.8760,2.3134))
      ,Artist("6", "Psy" ,Position(48.866,2.3111))
      ,Artist("7", "Amadeus Mozart" ,Position(49.8760,2.3224))
    )
    Ok(toJson(artists))
  }
}

object Artists extends Artists