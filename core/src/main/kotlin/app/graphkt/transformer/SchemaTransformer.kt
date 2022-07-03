/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.input.GraphQlInput
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.GraphQlType
import app.graphkt.graphql.type.toFragment
import app.graphkt.graphql.type.toInput
import app.graphkt.transformer.reducer.FragmentReducer
import app.graphkt.transformer.reducer.InputReducer
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.transformer.reducer.TypeReducer

interface SchemaTransformer {
    fun transform(schema: GraphQlSchema): List<Pair<String, String>>
}

class SchemaTransformerImpl(
    private val queryReducer: QueryReducer,
    private val typeReducer: TypeReducer,
    private val inputReducer: InputReducer,
    private val fragmentReducer: FragmentReducer,
) : SchemaTransformer {

    override fun transform(schema: GraphQlSchema): List<Pair<String, String>> {
        val queries = schema.queries
        val inputs = schema.inputs
        val types = schema.types
        val fragments = schema.fragments

        val typesWithFragment = schema.types
            .filter { it.generateFragment }
            .map { it.toFragment() }

        val typesWithInput = schema.types
            .filter { it.generateInput }
            .map { it.toInput() }

        val schemaString = buildString {
            append(
                """
                |schema {
                |    query: Query
                |}
                |
                """.trimMargin("|") + "\n"
            )

            if (queries.isNotEmpty()) {
                append(transformQueries(queries) + "\n")
            }

            if (types.isNotEmpty()) {
                append(transformTypes(types) + "\n")
            }

            if (typesWithInput.isNotEmpty()) {
                append(transformTypesWithInputs(typesWithInput) + "\n")
            }

            if (inputs.isNotEmpty()) {
                append(transformInputs(inputs) + "\n")
            }

            if (typesWithFragment.isNotEmpty()) {
                append(transformTypesWithFragments(typesWithFragment) + "\n")
            }

            if (fragments.isNotEmpty()) {
                append(transformFragments(fragments) + "\n")
            }
        }

        return listOf(
            Pair(schema.name, schemaString)
        )
    }

    private fun transformTypes(types: List<GraphQlType>): String {
        return if (types.isNotEmpty()) {
            typeReducer.reduce(types)
        } else ""
    }

    private fun transformTypesWithInputs(typesWithInput: List<GraphQlInput>): String {
        return if (typesWithInput.isNotEmpty()) {
            inputReducer.reduce(typesWithInput, postfix = "Input")
        } else ""
    }

    private fun transformTypesWithFragments(typesWithFragment: List<GraphQlFragment>): String {
        return if (typesWithFragment.isNotEmpty()) {
            fragmentReducer.reduce(typesWithFragment, postfix = "Fragment")
        } else ""
    }

    private fun transformInputs(inputs: List<GraphQlInput>): String {
        return if (inputs.isNotEmpty()) {
            inputReducer.reduce(inputs)
        } else ""
    }

    private fun transformFragments(fragments: List<GraphQlFragment>): String {
        return if (fragments.isNotEmpty()) {
            fragmentReducer.reduce(fragments)
        } else ""
    }

    private fun transformQueries(queries: List<GraphQlQuery>): String {
        return if (queries.isNotEmpty()) {
            """
            |type Query {
            |${queryReducer.reduce(queries)}
            |}
            |
        """.trimMargin("|")
        } else ""
    }
}