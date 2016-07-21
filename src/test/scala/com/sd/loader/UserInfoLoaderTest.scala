package com.sd.loader

/**
  * Created by hadoop on 16-7-15.
  */
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.testkit._
import com.sd.test.TestConfig
import org.scalatest.concurrent.Futures

import scala.concurrent.duration._
import org.scalatest.{FlatSpecLike, Matchers}

import scala.util.{Success,Failure}
class UserInfoLoaderTest extends TestKit(ActorSystem("test")) with ImplicitSender with FlatSpecLike with Matchers{
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
              //println(x)
              this_actorRef ! "success"
            case Failure(_) =>
              this_actorRef ! "failure"
          }
        }
        case _ => this_actorRef ! "failure"
      }
    }))

    val untested = system.actorOf(Props(new UserInfoLoader(tester)))
    untested ! UserInfoRequest("1839060271",cookie)
    expectMsg("success")
  }
}
