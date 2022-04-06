/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.fragment.GraphQlFragmentField
import app.graphkt.transformer.util.createIndentOfSize

interface FragmentFieldReducer {
    fun reduce(fields: List<GraphQlFragmentField>, indent: String = createIndentOfSize(4)): String
}

class FragmentFieldReducerImpl : FragmentFieldReducer {
    override fun reduce(fields: List<GraphQlFragmentField>, indent: String): String {
        return buildString {
            fields.onEachIndexed { index, field ->
                append("${indent}${field.name}")

                if (index != (fields.size - 1)) {
                    append("\n")
                }
            }
        }
    }
}
