package com.sd.spider

/**
  * Created by hadoop on 16-7-29.
  */
import org.jsoup.nodes.Document
import com.sd.model.UserInfo
import scala.collection.JavaConverters._
trait WeiboUserInfoParser {
  /**
    *
    * @param string The String that need to remove /n and /nr
    * @return clean text
    */
  private def cleanup(string:String) = string.replace("t", "").replace("rn", "").trim

  def parse_user_info(html:Document):UserInfo = {
    val infos = html.getElementsByAttributeValue("class", "li_1 clearfix").asScala
    val infoBuilder = UserInfo.builder()
    infos.foreach{ raw_info =>
      if(raw_info.getElementsByAttributeValueContaining("href", "loc=infblog").size() == 0){
        val data = raw_info.getElementsByAttributeValue("class", "pt_detail").first().text().trim
        raw_info.getElementsByAttributeValue("class", "pt_title S_txt2").first().text().trim match{
          case "Nickname：" => infoBuilder.setNickname(data)
          case "Location：" => infoBuilder.setLocation(data)
          case "Gender：" => infoBuilder.setGender(data)
          case "Registration Time：" => infoBuilder.setRegistrationTime(cleanup(data))
          case "大学 ：" => infoBuilder.setCollege(cleanup(data))
          case "高中 ：" => infoBuilder.setHighschool(cleanup(data))
          case "Tags：" => infoBuilder.setTag(cleanup(data))
          case "Slogan：" => infoBuilder.setSlogan(data)
          case "Company ：" => infoBuilder.setCompany(cleanup(data))
          case "Domain Name：" if raw_info.getElementsByAttributeValueContaining("href", "loc=infdomain").size() != 0 =>
            infoBuilder.setDomain(raw_info.select("a").text())
          case "Blood type：" => infoBuilder.setBloodType(data)
          case "Status：" => infoBuilder.setRelationship(cleanup(data))
          case "Mail：" => infoBuilder.setEmail(data)
          case "QQ：" => infoBuilder.setQQ(data)
          case "Interested In：" => infoBuilder.setSexTendency(cleanup(data))
          case _ =>
        }
      }else{
        infoBuilder.setBlog(raw_info.select("a").text())
      }
    }
    infoBuilder.build()
  }

}
