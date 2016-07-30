package com.sd.spider

import akka.actor.{Actor, ActorLogging}

/**
  * Created by hadoop on 16-7-15.
  */
trait ParserActor extends Actor with ActorLogging with DecodeWeibo{
  def receive = {
    case _ =>
  }
}
