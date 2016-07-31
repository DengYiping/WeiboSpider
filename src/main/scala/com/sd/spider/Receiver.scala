package com.sd.spider

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}

/**
  * Created by hadoop on 16-7-30.
  */
trait Receiver extends Actor with ActorLogging{
  this:HttpHandle =>
  import context.dispatcher
  implicit val materializer:ActorMaterializer  = ActorMaterializer()

  //unmarshall http response and pass it to load

  def receive = {
    case (r: HttpResponse,request:Any)=>
      if (r.status.isSuccess()) {
      Unmarshal(r.entity).to[String].onComplete {
        case Success(x) if x.length > 0 =>
          load(x, request)
        case Failure(_) =>
          failLoad(request)
        case z =>
          failLoad(request)
      }
    }
    case _ => failLoad(null)
  }
}
