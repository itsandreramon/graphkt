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
        addIfSchemaCorrect(typeDefinitions.schemaDefinition) {
            types = types.plus(type)
        }
    }

    override fun addQuery(queryDefinitions: QueryDefinitions, query: GraphQlQuery) {
        addIfSchemaCorrect(queryDefinitions.schemaDefinition) {
            queries = queries.plus(query)
        }
    }

    override fun addFragment(fragmentDefinitions: FragmentDefinitions, fragment: GraphQlFragment) {
        addIfSchemaCorrect(fragmentDefinitions.schemaDefinition) {
            fragments = fragments.plus(fragment)
        }
    }

    override fun addInput(inputDefinitions: InputDefinitions, input: GraphQlInput) {
        addIfSchemaCorrect(inputDefinitions.schemaDefinition) {
            inputs = inputs.plus(input)
        }
    }

    fun build(builder: SchemaDefinition.() -> Unit): GraphQlSchema {
        builder(SchemaDefinition(this))
        return this
    }

    private fun addIfSchemaCorrect(schemaDefinition: SchemaDefinition, onCorrect: () -> Unit) {
        if (schemaDefinition.schema == this) {
            onCorrect()
        } else {
            throw IllegalStateException("Cannot modify another schema.")
        }
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