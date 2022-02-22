/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.transformer.util.createIndentOfSize

interface QuerySelectionReducer {
    fun reduce(query: GraphQlQuery, indent: String = createIndentOfSize(8)): String
}

class QuerySelectionReducerImpl : QuerySelectionReducer {

    override fun reduce(
        query: GraphQlQuery,
        indent: String,
    ): String {
        return buildString {
            query.output.fragments.onEach { fragment ->
                append("$indent...${fragment.name}")
            }

            query.output.fields.onEach { field ->
                append(indent + field.name)
            }
        }
    }
}