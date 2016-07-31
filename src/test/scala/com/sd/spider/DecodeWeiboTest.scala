package com.sd.spider

import java.nio.file.{Files, Path, Paths}

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by hadoop on 16-7-15.
  */
import scala.collection.JavaConverters._
class DecodeWeiboTest extends FlatSpec with Matchers with DecodeWeibo{
  val raw_html = new String(Files.readAllBytes(Paths.get("src/test/resources/test.html")))
  "A weibo information page parser" should "extract nested html" in {
   val parsed = decode(raw_html).getElementsByAttributeValue("class","li_1 clearfix").asScala
    parsed.length should be > (0)
    //parsed.foreach(info => println(info.getElementsByAttributeValue("class", "pt_title S_txt2").first().text().trim))
  }
}
