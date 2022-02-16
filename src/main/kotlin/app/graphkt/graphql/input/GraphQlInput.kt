package app.graphkt.graphql.input

data class GraphQlInput(
    var name: String = "",
    var fields: MutableList<GraphQlInputField> = mutableListOf(),
)