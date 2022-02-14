/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql

import app.graphkt.concept.SchemaDefinition
import app.graphkt.concept.fragment.FragmentDefinitions
import app.graphkt.concept.query.QueryDefinitions
import app.graphkt.concept.type.TypeDefinitions
import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.GraphQlType

interface GraphQlSchema {
    val name: String
    val types: List<GraphQlType>
    val queries: List<GraphQlQuery>
    val fragments: List<GraphQlFragment>

    fun addType(typeDefinitions: TypeDefinitions, type: GraphQlType)
    fun addQuery(queryDefinitions: QueryDefinitions, query: GraphQlQuery)
    fun addFragment(fragmentDefinitions: FragmentDefinitions, fragment: GraphQlFragment)
}

/**
 * State-holder that represents the current transformation model.
 *
 * @param name Defines the name of generated schema.
 */
class GraphQlSchemaImpl(override val name: String) : GraphQlSchema {

    override val types = mutableListOf<GraphQlType>()
    override val queries = mutableListOf<GraphQlQuery>()
    override val fragments = mutableListOf<GraphQlFragment>()

    override fun addType(typeDefinitions: TypeDefinitions, type: GraphQlType) {
        if (typeDefinitions.schemaDefinition.schema == this) {
            types.add(type)
        } else {
            throw IllegalStateException("Cannot modify types of another schema.")
        }
    }

    override fun addQuery(queryDefinitions: QueryDefinitions, query: GraphQlQuery) {
        if (queryDefinitions.schemaDefinition.schema == this) {
            queries.add(query)
        } else {
            throw IllegalStateException("Cannot modify queries of another schema.")
        }
    }

    override fun addFragment(fragmentDefinitions: FragmentDefinitions, fragment: GraphQlFragment) {
        if (fragmentDefinitions.schemaDefinition.schema == this) {
            fragments.add(fragment)
        } else {
            throw IllegalStateException("Cannot modify queries of another schema.")
        }
    }

    fun build(builder: SchemaDefinition.() -> Unit): GraphQlSchema {
        builder(SchemaDefinition(this))
        return this
    }

    fun toGraphQl(): String {
        // TODO
        return toString()
    }

    override fun toString(): String {
        return """
			name: $name
			types: $types
			queries: $queries
		""".trimIndent()
    }
}

/**
 * Top-level function used to build a GraphQL schema.
 *
 * @param name The name of the created Schema file.
 * @param builder Lambda passed into in order to add schema properties.
 */
fun buildSchema(name: String = "", builder: SchemaDefinition.() -> Unit = {}): GraphQlSchema {
    return GraphQlSchemaImpl(name).build(builder)
}