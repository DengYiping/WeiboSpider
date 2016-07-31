package com.sd.spider

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, TestKit}
import com.sd.loader.{WeiboLoader, UserInfoRequest}
import com.sd.test.TestConfig
import org.scalatest.{FlatSpecLike, Matchers}

import scala.util.{Failure, Success}
import scala.collection.JavaConverters._
/**
  * Created by hadoop on 16-7-29.
  */
class WeiboUserInfoParserTest extends TestKit(ActorSystem("test")) with ImplicitSender with FlatSpecLike with Matchers with WeiboUserInfoParser with DecodeWeibo{
  //import system.dispatcher
  //implicit val materializer = ActorMaterializer()
  "A user information loader" should "load a user's information page" in {
    val cookie = TestConfig.cookie
    val this_actorRef = self

    val tester = system.actorOf(Props(new Receiver with HttpHandle{
      def failLoad(m:Any): Unit ={
        this_actorRef ! "failure"
      }

      def load(s: String, m:Any): Unit = {
        import com.sd.model.JsonConvertors._
        import spray.json._
        val parsed_raw = decode(s)
        parse_user_info(parsed_raw).toJson.toString().length should be > 0
        this_actorRef ! "success"
      }
    }))

    val untested = system.actorOf(Props(new WeiboLoader(tester, null, null)))
    untested ! UserInfoRequest("2957464141",cookie)
    expectMsg("success")
  }
}