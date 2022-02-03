package app.graphkt

class Schema(private val name: String) {

	val queries: List<GraphQlMutation> = mutableListOf()
	val mutations: List<GraphQlMutation> = mutableListOf()
	val fragments: List<GraphQlFragment> = mutableListOf()
	private val types: MutableList<GraphQlType> = mutableListOf()

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

fun buildSchema(name: String, builder: SchemaDefinition.() -> Unit): Schema {
	return Schema(name).build(builder)
}

fun TypeDefinitions.Type(name: String, typeBuilder: TypeBuilder.() -> Unit): GraphQlType {
	val type = GraphQlType().apply {
		this.name = name
	}

	typeBuilder(TypeBuilder(type))
	return type
}

fun SchemaDefinition.types(types: TypeDefinitions.() -> Unit) {
	types(TypeDefinitions(this))
}

class TypeDefinitions(private val schemaDefinition: SchemaDefinition)

class SchemaDefinition(private val schema: Schema)