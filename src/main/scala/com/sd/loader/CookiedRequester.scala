package com.sd.loader

/**
  * Created by hadoop on 16-7-9.
  */
import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings

case class Get(uri:String, cookie:String)

trait CookiedRequester extends Actor with ActorLogging{

  val parser:ActorRef




  import context.dispatcher
  import akka.pattern.pipe
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  val http = Http(context.system)


  def buildRequest(uri:String, cookie:String):HttpRequest = {
    RequestBuilding.addHeader("Cookie", cookie)(RequestBuilding.Get(uri))
  }




  def receive = {
    case Get(uri, "") => http.singleRequest(RequestBuilding Get uri) pipeTo parser
    case Get(uri, cookie) => http.singleRequest(buildRequest(uri, cookie)) pipeTo parser
  }


}
