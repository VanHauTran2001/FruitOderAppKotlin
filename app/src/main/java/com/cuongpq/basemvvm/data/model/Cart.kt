package com.cuongpq.basemvvm.data.model

class Cart(private var idSP : Int =0,
           private var nameSP : String=" ",
           private var priceSP :Int=0,
           private var unitSP : Int=0,
           private var imageSP:String="",
           private var number : Int = 0){
    fun getIdSP():Int?{
        return idSP
    }
    fun setIdSP(idSP: Int ){
        this.idSP = idSP
    }
    fun getNameSP():String?{
        return nameSP
    }
    fun setNameSP(nameSP: String){
        this.nameSP = nameSP
    }
    fun getPriceSP():Int?{
        return priceSP
    }
    fun setPriceSP(priceSP: Int){
        this.priceSP = priceSP
    }
    fun getUnitSP():Int?{
        return unitSP
    }
    fun setUnitSP(unitSP: Int){
        this.unitSP = unitSP
    }
    fun getImageSP():String?{
        return imageSP
    }
    fun setImageSP(imageSP: String){
        this.imageSP = imageSP
    }
    fun getNumber():Int?{
        return number
    }
    fun setNumber(number: Int){
        this.number = number
    }
}