package com.cuongpq.basemvvm.data.model

import java.io.Serializable

class Recently(private var id : Int =0,
               private var name : String=" ",
               private var description : String ="",
               private var price :Int=0,
               private var unit : Int=0,
               private var image:String="") : Serializable{
    fun getID():Int?{
        return id
    }
    fun getName():String?{
        return name
    }
    fun getDescription():String?{
        return description
    }
    fun getPrice():Int?{
        return price
    }
    fun getUnit():Int?{
        return unit
    }
    fun getImage():String?{
        return image
    }
}