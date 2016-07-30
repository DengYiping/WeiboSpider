package com.sd.model

import spray.json.DefaultJsonProtocol

/**
  * Created by hadoop on 16-7-30.
  */

object JsonConvertors extends DefaultJsonProtocol{
  implicit val userinfo_convertor = jsonFormat17(UserInfo.apply)
}
