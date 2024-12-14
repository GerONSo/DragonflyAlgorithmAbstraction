package com.geronso

fun <T> List<Pair<Double, T>>.maxDragonfly(): Pair<T?, Double> {
    var mx = Double.NEGATIVE_INFINITY
    var mxValue: T? = null
    forEach { d ->
        if (d.first > mx) {
            mx = d.first
            mxValue = d.second
        }
    }
    return Pair(mxValue, mx)
}

fun <T> List<Pair<Double, T>>.minDragonfly(): Pair<T?, Double> {
    var mn = Double.POSITIVE_INFINITY
    var mnValue: T? = null
    forEach { d ->
        if (d.first < mn) {
            mn = d.first
            mnValue = d.second
        }
    }
    return Pair(mnValue, mn)
}

fun List<Double>.maxIndexed(): Pair<Int, Double> {
    var mx = Double.NEGATIVE_INFINITY
    var mxIndex = -1
    forEachIndexed { index, d ->
        if (mx < d) {
            mx = d
            mxIndex = index
        }
    }
    return Pair(mxIndex, mx)
}

inline fun <T, R> Iterable<T>.allUniqueBy(transform: (T) -> R): Boolean {
    val hashset = hashSetOf<R>()
    return this.all { hashset.add(transform(it)) }
}