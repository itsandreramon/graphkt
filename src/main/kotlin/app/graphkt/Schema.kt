package app.graphkt

class Schema(val name: String) {

	val queries: List<GraphQlMutation> = mutableListOf()
	val mutations: List<GraphQlMutation> = mutableListOf()
	val fragments: List<GraphQlFragment> = mutableListOf()
	val types: MutableList<GraphQlType> = mutableListOf()

	fun build(builder: SchemaDefinition.() -> Unit): Schema {
		builder(SchemaDefinition(this))
		return this
	}

	override fun toString(): String {
		return """
			name: $name
			types: $types
		""".trimIndent()
	}
}

fun buildSchema(name: String, builder: SchemaDefinition.() -> Unit = {}): Schema {
	return Schema(name).build(builder)
}

fun TypeDefinitions.Type(name: String, typeBuilder: TypeBuilder.() -> Unit = {}) {
	this.type.apply {
		this.name = name
	}

	typeBuilder(TypeBuilder(type, onBuiltCallback = {
		this.schemaDefinition.schema.types.add(it)
	}).build())
}

fun SchemaDefinition.types(types: TypeDefinitions.() -> Unit) {
	types(TypeDefinitions(GraphQlType(), this))
}

class TypeDefinitions(
	val type: GraphQlType,
	val schemaDefinition: SchemaDefinition,
)

class SchemaDefinition(val schema: Schema)