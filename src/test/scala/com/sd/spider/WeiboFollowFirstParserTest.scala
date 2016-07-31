package com.sd.spider

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.sd.loader.{UserFollowFirstRequest, UserInfoRequest, WeiboLoader}
import com.sd.test.TestConfig
import org.scalatest.{FlatSpecLike, Matchers}

/**
  * Created by hadoop on 16-7-31.
  */
class WeiboFollowFirstParserTest extends TestKit(ActorSystem("test")) with ImplicitSender with FlatSpecLike with Matchers{
  import system.dispatcher
  "A Weibo Follow First Page Parser" should "parse info page" in{
    val cookie = TestConfig.cookie
    val this_actorRef = self

    val tester = system.actorOf(Props(new Receiver with HttpHandle with WeiboFollowFirstParser{
      def failLoad(m:Any): Unit ={
        this_actorRef ! "failure"
      }

      def load(s: String, m:Any): Unit = {
        val req = m.asInstanceOf[UserFollowFirstRequest]
        parse(s, req.userid,cookie).length should be > 0
        this_actorRef ! "success"
      }
    }))

    val untested = system.actorOf(Props(new WeiboLoader(null, null, tester)))
    untested ! UserFollowFirstRequest("1839060271",cookie)
    Thread.sleep(3000)
    expectMsg("success")
  }
}
