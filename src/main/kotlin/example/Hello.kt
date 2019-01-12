package example

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onKeyUpFunction
import org.w3c.dom.Node
import kotlin.browser.document
import kotlin.browser.window

private val data = Var("abc")

fun main(args: Array<String>) {
    document.body?.append(createMyDiv())

//    window.setTimeout({ data.set("NEW-VALUE-2000") }, 2000)
//    window.setTimeout({ data.set("NEW-VALUE-4000") }, 4000)
}


fun createMyDiv(): Node {
    return document.create.div {
        div {
            textInput {
                this.bindValue(data)
            }
        }
        div {
            span {
                this.bindContent(data)
            }
        }
    }
}

private fun SPAN.bindContent(data: Var<String>) {
    val nodeId = nextId()
    this.id = nodeId
    data.addListener { newValue ->
        document.getElementById(nodeId)?.let { element ->
            element.asDynamic().innerHTML = newValue
        } ?: this.text(newValue)
    }
}

private fun INPUT.bindValue(data: Var<String>) {
    val nodeId = nextId()
    this.id = nodeId
    data.addListener { newValue ->
        document.getElementById(nodeId)?.run {
            this.asDynamic().value = newValue
        } ?: run {
            this.value = newValue
        }
    }
    this.onKeyUpFunction = { _ ->
        val currentValue = document.getElementById(nodeId).asDynamic().value
        console.log(currentValue)
        data.set(currentValue)
    }
}

private var id = 0
private fun nextId(): String {
    return "var-$id".also {
        id += 1
    }
}