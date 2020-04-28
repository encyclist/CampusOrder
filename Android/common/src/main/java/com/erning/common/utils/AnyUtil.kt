package com.erning.common.utils

import com.alibaba.fastjson.annotation.JSONField
import com.google.gson.annotations.SerializedName

/**
 * 对象转成HashMap<String,String>
 * 支持 {@link com.alibaba.fastjson.annotation.JSONField} 和 {@link com.google.gson.annotations.SerializedName} 注解
 */
fun Any.toStringMap(): HashMap<String, String> {
    val map = HashMap<String,String>()
    val clazz = this::class.java
    val fields = clazz.declaredFields
    try {
        for (field in fields) {
            field.isAccessible = true
            val jsonField = field.getAnnotation(JSONField::class.java)
            val serializedName = field.getAnnotation(SerializedName::class.java)
            if(jsonField != null){
                map[jsonField.name] = field.get(this).toString()
            }else if(serializedName != null){
                map[serializedName.value] = field.get(this).toString()
            }else{
                map[field.name] = field.get(this).toString()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return map
}