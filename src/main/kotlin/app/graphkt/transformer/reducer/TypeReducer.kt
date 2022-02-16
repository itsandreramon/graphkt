package app.graphkt.transformer.reducer

import app.graphkt.graphql.type.GraphQlType

interface TypeReducer {
    fun reduce(indent: String, types: List<GraphQlType>): String
}

class TypeReducerImpl(
    private val typeFieldReducer: TypeFieldReducer,
) : TypeReducer {

    override fun reduce(indent: String, types: List<GraphQlType>): String {
        return buildString {
            types.onEach { type ->
                append("""
                    |type ${type.name} {
                    |${typeFieldReducer.reduce(indent, type.fields)}
                    |}
                """.trimMargin("|"))
            }
        }
    }
}