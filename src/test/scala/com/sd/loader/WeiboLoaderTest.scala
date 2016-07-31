package com.sd.loader

/**
  * Created by hadoop on 16-7-15.
  */
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.testkit._
import com.sd.spider.{HttpHandle, Receiver}
import com.sd.test.TestConfig
import org.scalatest.concurrent.Futures

import scala.concurrent.duration._
import org.scalatest.{FlatSpecLike, Matchers}

import scala.util.{Failure, Success}
class WeiboLoaderTest extends TestKit(ActorSystem("test")) with ImplicitSender with FlatSpecLike with Matchers{
  import system.dispatcher
  "A user information loader" should "load a user's information page" in {
    val cookie = TestConfig.cookie
    val this_actorRef = self

    val tester = system.actorOf(Props(new Receiver with HttpHandle{

      def failLoad(m:Any): Unit ={
        this_actorRef ! "failure"
      }

      def load(s: String, m:Any): Unit = {
        this_actorRef ! "success"
      }

    }))

    val untested = system.actorOf(Props(new WeiboLoader(tester, null, null)))
    untested ! UserInfoRequest("1839060271",cookie)
    Thread.sleep(3000)
    expectMsg("success")
  }
}
