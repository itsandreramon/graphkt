package app.graphkt

data class GraphQlType(
	var name: String = "",
	var fields: MutableList<GraphQlTypeField<Any>> = mutableListOf(),
	var generateFragment: Boolean = false,
	var generateInput: Boolean = false,
)

data class GraphQlTypeField<T>(
	var name: String = "",
	var type: T? = null,
)

class TypeBuilder(
	private val type: GraphQlType,
	val onBuiltCallback: (GraphQlType) -> Unit,
) {
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

fun TypeDefinitions.fields(fields: FieldDefinitions.() -> Unit) {
	fields(FieldDefinitions(this))
}

class FieldBuilder<T : Any>(
	private val field: GraphQlTypeField<T>,
	private val onBuiltCallback: (GraphQlTypeField<T>) -> Unit,
) {
	fun name(value: String) {
		field.name = value
	}

	fun build(): FieldBuilder<T> {
		onBuiltCallback(field)
		return this
	}
}

fun <T : Any> FieldDefinitions.Field(builder: FieldBuilder<T>.() -> Unit) {
	val field = GraphQlTypeField<T>()

	builder(FieldBuilder(field, onBuiltCallback = {
		this.typeDefinitions.type.fields.add(it as GraphQlTypeField<Any>)
	}).build())
}

class FieldDefinitions(
	val typeDefinitions: TypeDefinitions,
)