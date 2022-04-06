/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.transformer.util.createIndentOfSize

interface FragmentReducer {
    fun reduce(
        fragments: List<GraphQlFragment>,
        indent: String = createIndentOfSize(4),
        postfix: String = "",
    ): String
}

class FragmentReducerImpl(
    private val fragmentFieldReducer: FragmentFieldReducer,
) : FragmentReducer {

    override fun reduce(
        fragments: List<GraphQlFragment>,
        indent: String,
        postfix: String,
    ): String {
        return buildString {
            fragments.onEach { fragment ->
                val name = fragment.name
                    .replaceFirstChar { it.lowercase() }

                append("""
                    |fragment ${name}${postfix} on ${fragment.type} {
                    |${fragmentFieldReducer.reduce(fragment.fields, indent)}
                    |}
                """.trimMargin("|"))
            }
        }
    }
}