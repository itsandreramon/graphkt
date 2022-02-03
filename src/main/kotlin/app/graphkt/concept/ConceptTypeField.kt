package app.graphkt.concept

import app.graphkt.graphql.GraphQlTypeField

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