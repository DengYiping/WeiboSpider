package com.sd.spider

import com.sd.model.Seed
import com.sd.model.UserInfo
import com.sun.istack.internal.Nullable
import org.jsoup.Jsoup

import scala.collection.JavaConverters._
import org.jsoup.nodes.Element
import org.mongodb.scala.Document

import scala.collection.mutable.ListBuffer
/**
  * Created by hadoop on 16-7-15.
  */
trait DecodeWeibo {
  /**
    * Sina weibo's page is nested in javascript.
    * In order to make it less nasty, we will de-nest the html and get it
    */
  @Nullable private def getInlineHtml(script:String):String = {

    /**
      *   Regex : .*?"html":(.+)"}\)
      *   Test on String:
      *
      *   FM.view({"ns":"","domid":"plc_main","css":[],"html":"\t\t<div class=\"WB_frame_b\" fixed-box=\"true\">\r\n\t\t\t\t\t<div id=\"Pl_Core_T8CustomTriColumn__58\" anchor=\"-50\"><\/div>\r\n\t\t\t\t\t<div id=\"Pl_Core_PicText__59\" anchor=\"-50\"><\/div>\r\n\t\t\t\t\t<div id=\"Pl_Official_RightGrowNew__60\" anchor=\"-50\"><\/div>\r\n\t\t\t\t\t<div id=\"Pl_Third_Inline__61\" anchor=\"-50\"><\/div>\r\n\t\t\t<\/div>\r\n\t<div class=\"WB_frame_c\" fixed-box=\"true\">\r\n\t\t\t\t\t<div id=\"Pl_Official_PersonalInfo__62\" anchor=\"-50\"><\/div>\r\n\t\t\t<\/div>\r\n\t"})
      **/
    val pttrn = ".*?\"html\":(.+)\"}\\)".r
    pttrn.findFirstIn(script) match{
      case Some(html) => html
      case None => null
    }
  }






  def parse_jsoup(s:String):org.jsoup.nodes.Document = {
    val level1 = Jsoup.parse(s.replace("\\",""))

    val temp = level1.getElementsByTag("script").iterator()
    val html = new StringBuilder()


    while(temp.hasNext){
      getInlineHtml(temp.next().html()) match{
        case s:String => html append s
        case _ =>
      }
    }
    Jsoup parse html.toString()
  }
}
