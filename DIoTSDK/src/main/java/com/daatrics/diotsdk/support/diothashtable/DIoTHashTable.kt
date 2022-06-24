package com.daatrics.diotdemoapp.diotsdk.support.diothashtable

import java.util.*

class DIoTHashTable {

    var values = Collections.synchronizedList(ArrayList<Any>())

    fun isEmpty(): Boolean {
        var result: Boolean = false

        synchronized (values) {
            result =  values.isEmpty()
        }

        return result
    }

    fun contains(value: Any): Boolean{
        var result: Boolean = false

        synchronized (values) {
            result =  values.contains(value)
        }

        return result
    }

    fun forEach(body: (Any) -> (Unit)){
        synchronized (values) {
            for (value in values) {
                value?.let { body(it) }
            }
        }
    }

    fun <Element> forEachTyped(body: (Element) -> (Unit)){
        synchronized (values) {
            for (value in values) {
                val element: Element? = value as? Element
                element?.let { body(it) }
            }
        }
    }

    fun add(value: Any){
        synchronized (values) {
            if (!values.contains(value))
                values.add(value)
        }
    }

    fun remove(value: Any){
        synchronized (values) {
            if (values.contains(value)) {
                values.remove(value)
            }
        }
    }
}