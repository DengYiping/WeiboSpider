package com.sd.loader

import akka.actor.ActorRef
import akka.pattern.pipe
/**
  * Created by hadoop on 16-7-9.
  */
case class UserInfoRequest(userid:String, _cookie:String) extends GetRequest{
  def uri = s"http://weibo.com/p/100505$userid/info?mod=pedit_more"
  def cookie = Some(_cookie)
}

case class UserFollowRequest(userid:String, _cookie:String, page:Int) extends GetRequest{
  def uri = s"http://weibo.com/p/100505$userid/follow?page=$page#Pl_Official_HisRelation__64"
  def cookie = Some(_cookie)
}

case class UserFollowFirstRequest(userid:String, _cookie:String) extends GetRequest{
  def uri = s"http://weibo.com/p/100505$userid/follow?page=1#Pl_Official_HisRelation__64"
  def cookie = Some(_cookie)
}

class WeiboLoader(val user_info_parser:ActorRef, val user_follow_parser:ActorRef, val user_firstPage_parser:ActorRef) extends CookiedRequester {
  import context.dispatcher

  override def receive = {
    case x:UserInfoRequest => pipedRequest(user_info_parser, x)
    case y:UserFollowRequest => pipedRequest(user_follow_parser, y)
    case z:UserFollowFirstRequest => pipedRequest(user_firstPage_parser,z)
  }
}
