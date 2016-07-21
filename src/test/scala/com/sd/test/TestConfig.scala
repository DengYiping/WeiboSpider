package com.sd.test

import com.typesafe.config.ConfigFactory

/**
  * Created by hadoop on 16-7-15.
  */
object TestConfig {
  private val config = ConfigFactory.load()
  val cookie = config.getString("cookie")
}
