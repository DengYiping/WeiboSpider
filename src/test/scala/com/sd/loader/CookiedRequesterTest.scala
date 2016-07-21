package com.sd.loader

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.HttpResponse
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, FlatSpecLike, Matchers}

/**
  * Created by hadoop on 16-7-9.
  */
import akka.testkit.TestActorRef
import scala.concurrent.duration._

class CookiedRequesterTest extends TestKit(ActorSystem()) with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll{
  override def afterAll: Unit ={
    TestKit.shutdownActorSystem(system)
  }

  val uri = "http://www.zhihu.com"















  "A Cookied Requester" should "perform GET request with empty cookie" in{

    val s = self



    val tempRef = system.actorOf(Props(new Actor{
      def receive = {
        case x:HttpResponse => if(x.status.isSuccess()) s ! "Success"
        case y => s ! y
      }
    }))


    val untestedRef = system.actorOf(Props(new CookiedRequester {
      override val parser: ActorRef = tempRef
    }))


    untestedRef ! Get(uri, "")

    expectMsg("Success")
  }













  it should "allow user to insert cookie" in{
    val s = self



    val tempRef = system.actorOf(Props(new Actor{
      def receive = {
        case x:HttpResponse => if(x.status.isSuccess()) s ! "Success"
        case y => s ! y
      }
    }))


    val untestedRef = system.actorOf(Props(new CookiedRequester {
      override val parser: ActorRef = tempRef
    }))


    untestedRef ! Get(uri, "abc=efg;")

    expectMsg("Success")
  }
}
