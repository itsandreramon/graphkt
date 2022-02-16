package app.graphkt.transformer.reducer

import app.graphkt.graphql.type.GraphQlTypeField

interface TypeFieldReducer {
    fun reduce(indent: String, fields: List<GraphQlTypeField>): String
}

class TypeFieldReducerImpl : TypeFieldReducer {
    override fun reduce(indent: String, fields: List<GraphQlTypeField>): String {
        return buildString {
            fields.onEachIndexed { index, field ->
                append("${indent}${field.name}: ${field.type}")

                if (index != (fields.size - 1)) {
                    append("\n")
                }
            }
        }
    }
}
