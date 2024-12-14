package com.geronso

import kotlin.math.min

class DragonflyGroup(
    var dragonflies: List<Dragonfly> = mutableListOf()
) {

    fun separation(
        separationWeight: Double,
        transform: (target: List<Dragonfly>, a: Int, b: Int) -> List<Dragonfly>,
        similarity: (dragonfly: Dragonfly, other: Dragonfly) -> Double,
    ) {
        for (i in dragonflies.indices) {
            for (j in dragonflies.indices) {
                if (i <= j) continue
                val sim = similarity(dragonflies[i], dragonflies[j])
                if (sim > separationWeight) {
                    dragonflies = transform(dragonflies, i, j)
                }
            }
        }
    }

    fun alignment(
        alignmentWeight: Double,
        transform: (target: List<Dragonfly>, a: Int, b: Int) -> List<Dragonfly>,
        result: (Dragonfly) -> Double,
        similarity: (dragonfly: Dragonfly, other: Dragonfly) -> Double,
    ) {
        for (i in dragonflies.indices) {
            val best = findBest(result)
            if (i == best) continue
            if (similarity(dragonflies[i], dragonflies[best]) < alignmentWeight) {
                dragonflies = transform(dragonflies, i, best)
            }
        }
    }

    fun cohesion(
        cohesionWeight: Double,
        transform: (target: List<Dragonfly>, a: Int, b: Int) -> List<Dragonfly>,
        similarity: (dragonfly: Dragonfly, other: Dragonfly) -> Double,
    ) {
        for (i in dragonflies.indices) {
            for (j in dragonflies.indices) {
                if (i <= j) continue
                if (similarity(dragonflies[i], dragonflies[j]) < cohesionWeight) {
                    dragonflies = transform(dragonflies, i, j)
                }
            }
        }
    }

    fun foodAttraction(
        foodFactor: Double,
        best: Dragonfly,
        transform: (target: List<Dragonfly>, index: Int, best: Dragonfly) -> List<Dragonfly>,
        similarity: (dragonfly: Dragonfly, other: Dragonfly) -> Double,
    ) {
        for (i in dragonflies.indices) {
            if (similarity(dragonflies[i], best) <= foodFactor) {
                dragonflies = transform(dragonflies, i, best)
            }
        }
    }

    fun enemyDistraction(
        enemyFactor: Double,
        worst: Dragonfly,
        best: Dragonfly,
        transform: (target: List<Dragonfly>, index: Int, worst: Dragonfly) -> List<Dragonfly>,
        similarity: (dragonfly: Dragonfly, other: Dragonfly) -> Double,
    ) {
        for (i in dragonflies.indices) {
            if (similarity(dragonflies[i], worst) < enemyFactor && dragonflies[i] != best) {
                dragonflies = transform(dragonflies, i, worst)
            }
        }
    }

    private fun findBest(result: (Dragonfly) -> Double): Int {
        val (index, _) = dragonflies.map { dragonfly ->
            result(dragonfly)
        }.maxIndexed()
        return index
    }

    companion object {
        fun create(groups: List<DragonflyGroup>): DragonflyGroup {
            return DragonflyGroup(
                dragonflies = groups.map {
                    it.dragonflies
                }.flatten()
            )
        }
    }
}