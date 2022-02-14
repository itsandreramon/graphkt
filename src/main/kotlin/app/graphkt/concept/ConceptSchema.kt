/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.concept.fragment.FragmentDefinitions
import app.graphkt.concept.query.QueryDefinitions
import app.graphkt.concept.type.TypeDefinitions
import app.graphkt.graphql.GraphQlSchema
import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.GraphQlType

/**
 * Function used to define types inside a TypeDefinitions scope.
 *
 * @param types Lambda passed into in order to add types.
 */
fun SchemaDefinition.types(types: TypeDefinitions.() -> Unit) {
    types(TypeDefinitions(GraphQlType(), this))
}

/**
 * Function used to define queries inside a QueryDefinitions scope.
 *
 * @param queries Lambda passed into in order to add queries.
 */
fun SchemaDefinition.queries(queries: QueryDefinitions.() -> Unit) {
    queries(QueryDefinitions(GraphQlQuery(), this))
}

/**
 * Function used to define fragments inside a FragmentDefinitions scope.
 *
 * @param fragments Lambda passed into in order to add fragments.
 */
fun SchemaDefinition.fragments(fragments: FragmentDefinitions.() -> Unit) {
    fragments(FragmentDefinitions(GraphQlFragment(), this))
}

/**
 * State-holder that is used to save the current schema.
 *
 * @param schema The current schema that is going to be defined.
 */
class SchemaDefinition(val schema: GraphQlSchema)