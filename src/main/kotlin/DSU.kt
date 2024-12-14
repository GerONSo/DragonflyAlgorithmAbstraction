package com.geronso


// Class for effective calculating of sets
class DSU(
    groups: List<DragonflyGroup>
) {
    private val parent = IntArray(groups.size)
    private val rank = IntArray(groups.size)

    init {
        groups.forEachIndexed { index, _ ->
            makeSet(index)
        }
    }

    private fun makeSet(index: Int) {
        parent[index] = index
        rank[index] = 0
    }

    fun findSet(index: Int): Int {
        if (index == parent[index]) return index
        parent[index] = findSet(parent[index])
        return parent[index]
    }

    fun unionSets(index1: Int, index2: Int) {
        val s1 = findSet(index1)
        val s2 = findSet(index2)
        if (s1 != s2) {
            if (rank[s1] < rank[s2]) {
                rank[s1] = rank[s2].also { rank[s2] = it }
            }
            parent[s2] = s1
            if (rank[s1] == rank[s2]) {
                rank[s1]++
            }
        }
    }
}