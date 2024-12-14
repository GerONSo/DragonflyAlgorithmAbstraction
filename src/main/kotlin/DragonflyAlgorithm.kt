package com.geronso

class DragonflyAlgorithm(
    private val problem: Problem,
    dragonfliesCount: Int,
) {

    private var groups: MutableList<DragonflyGroup> = mutableListOf()

    init {
        for (i in 1..dragonfliesCount) {
            groups.add(DragonflyGroup(listOf(problem.generateRandomDragonfly())))
        }
    }

    fun solve(
        maxIterations: Int
    ): Pair<Dragonfly, Double> {
        var iteration = maxIterations
        while (iteration != 0) {

            groups = updateGroups(
                radius = problem.groupDistanceThreshold * (iteration.toDouble() / maxIterations.toDouble())
            )
            val worst = findWorst()
            val best = findBest()

            for (group in groups) {
                group.separation(
                    problem.separationWeight,
                    problem::separation,
                    problem::similarity
                )
                group.alignment(
                    problem.alignmentWeight,
                    problem::alignment,
                    problem::result,
                    problem::similarity
                )
                group.cohesion(
                    problem.cohesionWeight,
                    problem::cohesion,
                    problem::similarity
                )
                group.foodAttraction(
                    problem.foodFactor,
                    best,
                    problem::foodAttraction,
                    problem::similarity
                )
                group.enemyDistraction(
                    problem.enemyFactor,
                    worst,
                    best,
                    problem::enemyDistraction,
                    problem::similarity
                )
                assert(findBest() == best)
            }

            iteration--
        }
        return Pair(findBest(), problem.result(findBest()))
    }

    private fun updateGroups(radius: Double): MutableList<DragonflyGroup> {
        groups = groups.map { group ->
            group.dragonflies
        }.flatten().map {
            DragonflyGroup(listOf(it))
        }.toMutableList()

        val dsu = DSU(groups)

        // Find all the groups
        for (i in groups.indices) {
            for (j in groups.indices) {
                if (i == j) continue
                if (problem.groupDistance(groups[i], groups[j]) <= radius) {
                    dsu.unionSets(i, j)
                }
            }
        }

        val newGroupsMap: HashMap<Int, MutableList<DragonflyGroup>> = hashMapOf()
        for (i in groups.indices) {
            val setNumber = dsu.findSet(i)
            if (newGroupsMap.containsKey(setNumber)) {
                newGroupsMap[setNumber]?.add(groups[i])
            } else {
                newGroupsMap[setNumber] = mutableListOf(groups[i])
            }
        }
        return newGroupsMap.map {
            DragonflyGroup.create(it.value)
        }.toMutableList()
    }

    private fun findBest(): Dragonfly {
        val (dragonfly, _) = groups
            .map { group ->
                group.dragonflies.map { dragonfly ->
                    Pair(problem.result(dragonfly), dragonfly)
                }
            }
            .flatten()
            .maxDragonfly()
        return dragonfly!!
    }

    private fun findWorst(): Dragonfly {
        val (dragonfly, _) = groups
            .map { group ->
                group.dragonflies.map { dragonfly ->
                    Pair(problem.result(dragonfly), dragonfly)
                }
            }
            .flatten()
            .minDragonfly()
        return dragonfly!!
    }
}