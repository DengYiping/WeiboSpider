package com.sd.spider

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, TestKit}
import com.sd.loader.{UserInfoLoader, UserInfoRequest}
import com.sd.test.TestConfig
import org.scalatest.{FlatSpecLike, Matchers}

import scala.util.{Failure, Success}
import scala.collection.JavaConverters._
/**
  * Created by hadoop on 16-7-29.
  */
class UserInfoParserTest extends TestKit(ActorSystem("test")) with ImplicitSender with FlatSpecLike with Matchers with UserInfoParser with DecodeWeibo{
  import system.dispatcher
  implicit val materializer = ActorMaterializer()
  "A user information loader" should "load a user's information page" in {
    val cookie = TestConfig.cookie
    val this_actorRef = self
    val tester = system.actorOf(Props(new Actor with ActorLogging{
      def receive = {
        case r:HttpResponse => if(r.status.isSuccess()){
          Unmarshal(r.entity).to[String].onComplete{
            case Success(x) =>
              val parsed_raw = parse_jsoup(x)
              import com.sd.model.JsonConvertors._
              import spray.json._
              //println(parsed_raw)
              //println(parsed_raw.getElementsByAttributeValue("class","li_1 clearfix").asScala.foreach(info => println(info.getElementsByAttributeValue("class", "pt_title S_txt2").first().text().trim)))
              println(parse_user_info(parsed_raw).toJson)
              x.length should be > (0)
              this_actorRef ! "success"
            case Failure(_) =>
              this_actorRef ! "failure"
          }
        }
        case _ => this_actorRef ! "failure"
      }
    }))

    val untested = system.actorOf(Props(new UserInfoLoader(tester)))
    untested ! UserInfoRequest("2957464141",cookie)
    expectMsg("success")
  }
}