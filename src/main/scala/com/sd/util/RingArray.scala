package com.sd.util

/**
  * Created by hadoop on 16-7-9.
  */
case class Userid(id:String)

class RingArray[+T](val vector: Vector[T]){
  var ptr = 0

  def add[B >: T](a:B):RingArray[B] = new RingArray[B](vector.+:(a))


  def delete(i:Int):RingArray[T] = new RingArray[T](vector.drop(i))

  def get:T =  {
    if(ptr == (vector.length -1)){
      ptr = 0
      vector(ptr)
    }
    else{
      ptr = ptr + 1
      vector(ptr)
    }
  }
}

