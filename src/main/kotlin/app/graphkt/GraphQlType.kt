package app.graphkt

import kotlin.reflect.KClass

data class GraphQlType(
	var name: String = "",
	var fields: MutableList<GraphQlTypeField> = mutableListOf(),
	var generateFragment: Boolean = false,
	var generateInput: Boolean = false,
)

class GraphQlTypeField {
	var name: String = ""
	var type: KClass<Any>? = null
}

class TypeBuilder(private val type: GraphQlType, val onBuiltCallback: (GraphQlType) -> Unit) {
	fun generateFragment(value: Boolean) {
		type.generateFragment = value
	}

	fun generateInput(value: Boolean) {
		type.generateInput = value
	}

	fun build(): TypeBuilder {
		onBuiltCallback(type)
		return this
	}
}

fun TypeBuilder.fields(builder: FieldDefinitions.() -> Unit) {

}

class FieldBuilder(private val field: GraphQlTypeField) {

	fun name(value: String) {
		field.name = value
	}
}

fun <T> FieldDefinitions.Field(builder: FieldBuilder.() -> Unit) {
	val field = GraphQlTypeField()
	builder(FieldBuilder(field))
}

interface FieldDefinitions