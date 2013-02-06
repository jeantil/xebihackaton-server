package controllers

import lib.mvc.AuthenticatedAction
import play.api.mvc._

trait Users extends Controller {
  def update(id:String)= AuthenticatedAction { request =>
    val user=s"""
      {
       "id":"${id}",
       "name":"John Doe",
       "city":{
        "lat":"48.8753",
        "lng":"2.3112"
       },
       "email":"jdoe@xebia.fr"
      }
    """.stripMargin
    Ok(user)
  }
}

object Users extends Users