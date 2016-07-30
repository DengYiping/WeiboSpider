package com.sd.model

/**
  * Created by hadoop on 16-7-15.
  */
case class UserInfo(
                      location:Option[String],
                      nickname:Option[String],
                      gender:Option[String],
                      sex_tendency:Option[String],
                      relationship:Option[String],
                      birthday: Option[String],
                      blood_type:Option[String],
                      domain:Option[String],
                      registration_t:Option[String],
                      e_mail:Option[String],
                      qq:Option[String],
                      college:Option[String],
                      tag:Option[String],
                      blog:Option[String],
                      highschool:Option[String],
                      slogan:Option[String],
                      company:Option[String]
                   )

object UserInfo{

  /**
    * Builder for userinfo
    */
  class UserInfoBuilder{

    private var location:Option[String] = None
    def setLocation(that:String) = {
      location = Some(that)
      this
    }

    private var nickname:Option[String] = None
    def setNickname(that:String)= {
      nickname = Some(that)
      this
    }

    private var gender:Option[String] = None
    def setGender(that:String) = {
      gender = Some(that)
      this
    }

    private var sex_tendency:Option[String] = None
    def setSexTendency(that:String) = {
      sex_tendency = Some(that)
      this
    }

    private var relationship:Option[String] = None
    def setRelationship(that:String) = {
      relationship = Some(that)
      this
    }

    private var birthday: Option[String] = None
    def setBirthday(that:String)= {
      birthday = Some(that)
      this
    }

    private var blood_type:Option[String] = None
    def setBloodType(that:String)= {
      blood_type = Some(that)
      this
    }

    private var domain:Option[String] = None
    def setDomain(that:String) = {
      domain = Some(that)
      this
    }

    private var registration_t:Option[String] = None
    def setRegistrationTime(that:String)=  {
      registration_t = Some(that)
      this
    }

    private var e_mail:Option[String] = None
    def setEmail(that:String)= {
      e_mail = Some(that)
      this
    }

    private var qq:Option[String] = None
    def setQQ(that:String) = {
      qq = Some(that)
      this
    }

    private var college:Option[String] = None
    def setCollege(that:String) = {
      college = Some(that)
      this
    }

    private var tag:Option[String] = None
    def setTag(that:String) = {
      tag = Some(that)
      this
    }

    private var blog:Option[String] = None
    def setBlog(that:String) = {
      blog = Some(that)
      this
    }

    private var highschool:Option[String] = None
    def setHighschool(that:String) = {
      highschool = Some(that)
      this
    }

    private var slogan:Option[String] = None
    def setSlogan(that:String) = {
      slogan = Some(that)
      this
    }

    private var company:Option[String] = None
    def setCompany(that:String) = {
      company = Some(that)
      this
    }
    def build():UserInfo = UserInfo( location, nickname, gender, sex_tendency, relationship, birthday, blood_type, domain,
      registration_t, e_mail, qq, college, tag, blog, highschool, slogan, company)
  }

  def builder() = new UserInfoBuilder
}
