package com.sd.spider

import java.nio.file.{Files, Path, Paths}

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by hadoop on 16-7-15.
  */
class DecodeWeiboTest extends FlatSpec with Matchers with DecodeWeibo{
  val raw_html = new String(Files.readAllBytes(Paths.get("src/test/resources/test.html")))
  "A weibo information page parser" should "extract nested html" in {
   parse_jsoup(raw_html).getElementsByAttributeValue("class","li_1 clearfix").html().length should be > (0)
  }
}
