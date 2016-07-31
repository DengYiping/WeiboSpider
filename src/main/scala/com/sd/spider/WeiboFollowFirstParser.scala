package com.sd.spider

import com.sd.loader.UserFollowRequest

/**
  * Created by hadoop on 16-7-31.
  */
import scala.collection.JavaConverters._
trait WeiboFollowFirstParser extends DecodeWeibo with HttpHandle{
  def parse(s:String, userid:String, cookie:String):List[UserFollowRequest] = {
    val soup = decode(s)
    val W_pages = soup.getElementsByAttributeValue("class", "W_pages").first()
    if(W_pages != null){
      val max_page = W_pages.getElementsByAttributeValue("class", "page S_txt1").asScala.last.text().toInt
      Range(1, max_page + 1).map(UserFollowRequest(userid, cookie, _)).toList
    }
    else
      Nil
  }
}
