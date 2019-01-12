package example

typealias Listener<T> = (T) -> Unit

class Var<T>(initValue: T) {
    private var _value = initValue
    private val listeners = mutableListOf<Listener<T>>()

    fun addListener(listener: Listener<T>) {
        this.listeners.add(listener)
        listener(_value)
    }

    fun get() = _value

    fun set(value: T) {
        val changed = _value != value
        println("changed: $changed")
        _value = value
        if (changed) {
            listeners.forEach { it(value) }
        }
    }

    fun <K> map(fn: (T) -> K): Var<K> {
        val k = Var(fn(get()))
        this.addListener { v ->
            k.set(fn(v))
        }
        return k
    }

}