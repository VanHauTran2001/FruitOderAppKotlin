package com.cuongpq.basemvvm.data.model

class Notifi(private var idTB : Int = 0,
            private var textTB : String = "",
            private var hour : String = "",
            private var day : String = "") {


    fun getIdTB():Int?{
        return idTB
    }
    fun getTextTB():String?{
        return textTB
    }
    fun getHour():String?{
        return hour
    }
    fun getDay():String?{
        return day
    }
}