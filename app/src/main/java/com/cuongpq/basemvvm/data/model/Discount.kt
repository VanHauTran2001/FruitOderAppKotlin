package com.cuongpq.basemvvm.data.model

class Discount {
    private var id : Int ?=null
    private var name_item : String ?=null
    private var sale : String ?=null
    private var image_item :String?=null

    constructor(id : Int ?,name_item : String ?,sale : String ?,image_item :String?){
        this.id = id
        this.name_item = name_item
        this.sale = sale
        this.image_item = image_item
    }
    constructor()
    fun getID():Int?{
        return id
    }
    fun getNameItem():String?{
        return name_item
    }
    fun getSale():String?{
        return sale
    }
    fun getImageItem():String?{
        return image_item
    }
}