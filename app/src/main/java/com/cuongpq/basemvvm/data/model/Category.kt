package com.cuongpq.basemvvm.data.model

class Category {
    private var id : Int ?=null
    private var name_category : String ?=null
    private var image_category :String?=null

    constructor(id : Int ?,name_category : String ?,image_category :String?){
        this.id = id
        this.name_category = name_category
        this.image_category = image_category
    }
    constructor()
    fun getID():Int?{
        return id
    }
    fun getNameCategory():String?{
        return name_category
    }
    fun getImageCategory():String?{
        return image_category
    }
}