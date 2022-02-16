/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql

import app.graphkt.concept.SchemaDefinition
import app.graphkt.concept.fragment.FragmentDefinitions
import app.graphkt.concept.query.InputDefinitions
import app.graphkt.concept.query.QueryDefinitions
import app.graphkt.concept.type.TypeDefinitions
import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.input.GraphQlInput
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.type.GraphQlType

interface GraphQlSchema {
    val name: String
    val types: List<GraphQlType>
    val queries: List<GraphQlQuery>
    val fragments: List<GraphQlFragment>
    var inputs: List<GraphQlInput>

    fun addType(typeDefinitions: TypeDefinitions, type: GraphQlType)
    fun addQuery(queryDefinitions: QueryDefinitions, query: GraphQlQuery)
    fun addFragment(fragmentDefinitions: FragmentDefinitions, fragment: GraphQlFragment)
    fun addInput(inputDefinitions: InputDefinitions, input: GraphQlInput)
}

/**
 * State-holder that represents the current transformation model.
 *
 * @param name Defines the name of generated schema.
 */
class GraphQlSchemaImpl(override val name: String) : GraphQlSchema {

    override var types = listOf<GraphQlType>()
    override var queries = listOf<GraphQlQuery>()
    override var fragments = listOf<GraphQlFragment>()
    override var inputs = listOf<GraphQlInput>()

    override fun addType(typeDefinitions: TypeDefinitions, type: GraphQlType) {
        if (typeDefinitions.schemaDefinition.schema == this) {
            types = types.plus(type)
        } else {
            throw IllegalStateException("Cannot modify types of another schema.")
        }
    }

    override fun addQuery(queryDefinitions: QueryDefinitions, query: GraphQlQuery) {
        if (queryDefinitions.schemaDefinition.schema == this) {
            queries = queries.plus(query)
        } else {
            throw IllegalStateException("Cannot modify queries of another schema.")
        }
    }

    override fun addFragment(fragmentDefinitions: FragmentDefinitions, fragment: GraphQlFragment) {
        if (fragmentDefinitions.schemaDefinition.schema == this) {
            fragments = fragments.plus(fragment)
        } else {
            throw IllegalStateException("Cannot modify fragments of another schema.")
        }
    }

    override fun addInput(inputDefinitions: InputDefinitions, input: GraphQlInput) {
        if (inputDefinitions.schemaDefinition.schema == this) {
            inputs = inputs.plus(input)
        } else {
            throw IllegalStateException("Cannot modify inputs of another schema.")
        }
    }

    fun build(builder: SchemaDefinition.() -> Unit): GraphQlSchema {
        builder(SchemaDefinition(this))
        return this
    }

    override fun toString(): String {
        return """
			name: $name
			types: $types
			queries: $queries
			inputs: $inputs
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