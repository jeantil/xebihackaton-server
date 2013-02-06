import play.api.libs.json
import play.api.libs.json._
import play.api.libs.json.Json
import play.modules.reactivemongo.PlayBsonImplicits.JsValueReader
import reactivemongo.bson.BSONObjectID

package object model {
  case class User(oid:Option[BSONObjectID],id:String,name:String,city:Option[Position],email:String)

  case class Position(lat:BigDecimal, lng:BigDecimal){
    def distance(other:Position)={
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
  case class Artist(oid:Option[BSONObjectID],id:String,name:String,position:Position){
  }
  case class Concert(position:Position){}

  object Formats {
    import play.modules.reactivemongo.PlayBsonImplicits._
    import play.api.libs.json.Format

    implicit object BSONObjectIDFormat extends Format[BSONObjectID] {
      implicit def reads(json: JsValue): JsSuccess[BSONObjectID] = {
        JsSuccess(BSONObjectID((json \ "id" \ "oid").as[String]))
      }
      implicit def writes(id: BSONObjectID): JsValue =Json.obj(
        "_id" -> Json.obj("oid"->id.stringify)
      )
    }

    implicit val positionReads=Json.reads[Position]
    implicit val positionWrites=Json.writes[Position]

    implicit object userReads extends Reads[User]{
      def reads(json: JsValue) = {
        JsSuccess(User(
          (json \ "_id"\"oid").asOpt[String].map(BSONObjectID(_) ),
          (json \ "id").as[String],
          (json \ "name").as[String],
          (json \ "city").asOpt[Position],
          (json \ "email").as[String]
        ))
      }
    }

    implicit val userWrites=Json.writes[User]

//    implicit val artistReads=Json.reads[Artist]
    implicit object artistReads extends Reads[Artist]{
      def reads(json: JsValue) = {
        JsSuccess(Artist(
          (json \ "_id"\"oid").asOpt[String].map(BSONObjectID(_) ),
          (json \ "id").as[String],
          (json \ "name").as[String],
          (json \ "position").as[Position]
        ))
      }
    }
    implicit val artistWrites=Json.writes[Artist]

    implicit val concertReads=Json.reads[Concert]
    implicit val concertWrites=Json.writes[Concert]
  }

  object User {
    import scala.concurrent._
    import play.Logger
    import reactivemongo.bson._
    import reactivemongo.core.commands.Count
    import reactivemongo.bson.handlers._
    import reactivemongo.bson.handlers.DefaultBSONHandlers.DefaultBSONDocumentWriter
    import reactivemongo.bson.handlers.DefaultBSONHandlers.DefaultBSONReaderHandler
    import reactivemongo.api._
    import play.modules.reactivemongo._
    import play.api.Play.current
    import ExecutionContext.Implicits.global

    object BsonFormats{
      implicit object UserWriter extends RawBSONWriter[User] {
        import play.modules.reactivemongo.PlayBsonImplicits._
        import Formats.userWrites
        def write(user: User) = {
          val jsUser=Json.toJson(user)
          jsUser match {
            case o: JsObject => JsObjectWriter.write(o)
            case a: JsArray => JsArrayWriter.write(a)
            case _ => throw new RuntimeException("JsValue can only JsObject/JsArray")
          }
        }
      }

      implicit object UserBSONReader extends BSONReader[User] {
        import play.api.libs.json.Json.fromJson
        import Formats.userReads
        def fromBSON(doc: BSONDocument):User= {
          fromJson(JsValueReader.fromBSON(doc)).get
        }
      }
    }

    def db = { ReactiveMongoPlugin.db }
    def collection= { db("users") }

    def list(): Future[List[User]] = {
      import BsonFormats.UserBSONReader
      collection.find[BSONDocument,User](BSONDocument()).toList()
    }

    def findByEmail(email: String): Future[Option[User]] = {
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.UserBSONReader
      val qb = QueryBuilder().query(Json.obj( "email" -> email ))
      collection.find[User](qb).headOption()
    }
    def findById(id: String): Future[Option[User]] = {
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.UserBSONReader
      val qb = QueryBuilder().query(Json.obj( "id" -> id ))
      collection.find[User](qb).headOption()
    }

    def orCreate(u:User):Future[User]={
      def newUser=User(Some(BSONObjectID.generate), u.id,u.name, u.city,u.email)
      def toUser(maybeUser:Option[User]):Future[User]=maybeUser.map(Future.successful).getOrElse(create(newUser))
      findByEmail(u.email).flatMap(toUser)
    }

//    def orCreate(id:String,name: String, email: String,pos:Option[Position])(maybeUser:Option[User]):Future[User]={
//      maybeUser.fold(create(User(BSONObjectID.generate, id,name, pos,email))) (Future.successful)
//    }
//    def authenticate(name: String, email: String, verifiedId: String): Future[User] = {
//      Logger.info(s"Authenticate( ${name}, ${email}, ${verifiedId} )")
//      findByEmail(email).flatMap(orCreate(name: String, email: String, verifiedId: String) _)
//    }
    def save(user:User):Future[User]={
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.UserWriter
      val selector = BSONDocument("_id" -> user.oid)
      collection.update(selector,user,upsert=true).map(_=>user)
    }

//    def create(id:String,name: String, email: String, verifiedId: String): Future[User] = {
    def create(user:User): Future[User] = {
      save(user)
    }

    def count():Future[Int] = {
      ReactiveMongoPlugin.db.command(Count("users"))
    }

  }
  object Artist {
    import scala.concurrent._
    import play.Logger
    import reactivemongo.bson._
    import reactivemongo.core.commands.Count
    import reactivemongo.bson.handlers._
    import reactivemongo.bson.handlers.DefaultBSONHandlers.DefaultBSONDocumentWriter
    import reactivemongo.bson.handlers.DefaultBSONHandlers.DefaultBSONReaderHandler
    import reactivemongo.api._
    import play.modules.reactivemongo._
    import play.api.Play.current
    import ExecutionContext.Implicits.global

    object BsonFormats{
      implicit object ArtistWriter extends RawBSONWriter[Artist] {
        import play.modules.reactivemongo.PlayBsonImplicits._
        import Formats.artistWrites
        def write(user: Artist) = {
          val jsUser=Json.toJson(user)
          jsUser match {
            case o: JsObject => JsObjectWriter.write(o)
            case a: JsArray => JsArrayWriter.write(a)
            case _ => throw new RuntimeException("JsValue can only JsObject/JsArray")
          }
        }
      }

      implicit object ArtistBSONReader extends BSONReader[Artist] {
        import play.api.libs.json.Json.fromJson
        import Formats.artistReads
        def fromBSON(doc: BSONDocument):Artist= {
          fromJson(JsValueReader.fromBSON(doc)).get
        }
      }
    }

    def db = { ReactiveMongoPlugin.db }
    def collection= { db("users") }

    def list(): Future[List[Artist]] = {
      import BsonFormats.ArtistBSONReader
      collection.find[BSONDocument,Artist](BSONDocument()).toList()
    }

    def findByName(name: String): Future[Seq[Artist]] = {
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.ArtistBSONReader
      val qb = QueryBuilder().query(BSONDocument( "name" -> BSONRegex(s"${name}.*","i") ))
      collection.find[Artist](qb).toList()
    }

    def findById(id: String): Future[Option[Artist]] = {
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.ArtistBSONReader
      val qb = QueryBuilder().query(Json.obj( "id" -> id ))
      collection.find[Artist](qb).headOption()
    }

    //    def orCreate(id:String,name: String, email: String,pos:Option[Position])(maybeUser:Option[User]):Future[User]={
    //      maybeUser.fold(create(User(BSONObjectID.generate, id,name, pos,email))) (Future.successful)
    //    }
    //    def authenticate(name: String, email: String, verifiedId: String): Future[User] = {
    //      Logger.info(s"Authenticate( ${name}, ${email}, ${verifiedId} )")
    //      findByEmail(email).flatMap(orCreate(name: String, email: String, verifiedId: String) _)
    //    }
    def save(user:Artist):Future[Artist]={
      import play.modules.reactivemongo.PlayBsonImplicits._
      import BsonFormats.ArtistWriter
      val selector = BSONDocument("_id" -> user.oid)
      collection.update(selector,user,upsert=true).map(_=>user)
    }

    //    def create(id:String,name: String, email: String, verifiedId: String): Future[User] = {
    def create(user:Artist): Future[Artist] = {
      save(user)
    }

    def count():Future[Int] = {
      ReactiveMongoPlugin.db.command(Count("users"))
    }

  }
}
