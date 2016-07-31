package com.sd.spider

/**
  * Created by hadoop on 16-7-30.
  */
trait HttpHandle {
  def load(s:String, request:Any):Unit
  def failLoad(request:Any):Unit
}
