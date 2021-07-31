package id.pahlevikun.cookingtimer.common.extension


fun Map<String, String>.toList(): MutableList<Map<String, String>> {
    return this.map { (key, value) ->
        mapOf(key to value)
    }.toMutableList()
}

inline fun <T> MutableList<T>.mapInPlace(mutator: (T) -> T) {
    val iterate = this.listIterator()
    while (iterate.hasNext()) {
        val oldValue = iterate.next()
        val newValue = mutator(oldValue)
        if (newValue !== oldValue) {
            iterate.set(newValue)
        }
    }
}