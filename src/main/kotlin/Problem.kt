package com.geronso

interface Problem {

    val groupDistanceThreshold: Double
    val separationWeight: Double
    val alignmentWeight: Double
    val cohesionWeight: Double
    val foodFactor: Double
    val enemyFactor: Double

    fun separation(target: List<Dragonfly>, a: Int, b: Int): List<Dragonfly>

    fun alignment(target: List<Dragonfly>, a: Int, b: Int): List<Dragonfly>

    fun cohesion(target: List<Dragonfly>, a: Int, b: Int): List<Dragonfly>

    fun foodAttraction(target: List<Dragonfly>, index: Int, best: Dragonfly): List<Dragonfly>

    fun enemyDistraction(target: List<Dragonfly>, index: Int, worst: Dragonfly): List<Dragonfly>

    fun similarity(dragonfly: Dragonfly, other: Dragonfly): Double

    fun groupDistance(group: DragonflyGroup, otherGroup: DragonflyGroup): Double

    fun result(dragonfly: Dragonfly): Double

    fun generateRandomDragonfly(): Dragonfly
}