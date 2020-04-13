package com.j7arsen.data.model.mapper

interface Mapper<T, R> {

    fun map(origin: T): R

    fun map(origin: List<T>): List<R> {
        return origin.map(::map)
    }
}