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

import scala.concurrent.Future

abstract class GetRequest{
  def uri:String
  def cookie:Option[String]
}

case class Request(getRequest: GetRequest, receiver:ActorRef)

case class Get(uri:String, _cookie:String) extends GetRequest{
  def cookie = if( _cookie == "") None else Some(_cookie)
}

trait CookiedRequester extends Actor with ActorLogging{
  import context.dispatcher
  import akka.pattern.pipe
  implicit val materializer = ActorMaterializer(ActorMaterializerSettings(context.system))
  val http = Http(context.system)


  def buildRequest(uri:String, cookie:String):HttpRequest = {
    RequestBuilding.addHeader("Cookie", cookie)(RequestBuilding.Get(uri))
  }

  def pipedRequest(parser:ActorRef, url:String, cookie:Option[String]) = {
    cookie match {
      case Some(x) => http.singleRequest(buildRequest(url, x)) pipeTo parser
      case None => http.singleRequest(RequestBuilding Get url) pipeTo parser
    }
  }

  def pipedRequest(parser:ActorRef, get:GetRequest): Unit ={
    get.cookie match {
      case Some(x) => http.singleRequest(buildRequest(get.uri, x)) zip Future{get} pipeTo parser
      case None => http.singleRequest(RequestBuilding Get get.uri) zip Future{get} pipeTo parser
    }
  }

  def pipedRequest(request:Request): Unit ={
    pipedRequest(request.receiver, request.getRequest)
  }

  def receive = {
    case x:Get => pipedRequest(sender(), x)
    case y:Request => pipedRequest(y)
  }
}
