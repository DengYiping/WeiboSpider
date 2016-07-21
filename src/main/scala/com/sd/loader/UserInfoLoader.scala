package com.sd.loader

import akka.actor.ActorRef
import akka.pattern.pipe
/**
  * Created by hadoop on 16-7-9.
  */
case class UserInfoRequest(userid:String, cookie:String)


class UserInfoLoader(val parser:ActorRef) extends CookiedRequester{
  import context.dispatcher


  def buildURL(userid:String) = {
    s"http://weibo.com/p/100505$userid/info?mod=pedit_more"
  }

  override def receive = {
    case UserInfoRequest(userid, cookie) =>
      val uri = buildURL(userid)
      http.singleRequest(buildRequest(uri,cookie)) pipeTo parser
  }
}
