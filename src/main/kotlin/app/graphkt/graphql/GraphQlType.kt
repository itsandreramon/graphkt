package app.graphkt.graphql

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