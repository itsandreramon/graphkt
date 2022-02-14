/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt

import app.graphkt.concept.queries
import app.graphkt.concept.query.FieldSelection
import app.graphkt.concept.query.Input
import app.graphkt.concept.query.Query
import app.graphkt.concept.query.inputs
import app.graphkt.concept.query.output
import app.graphkt.concept.type.Field
import app.graphkt.concept.type.Type
import app.graphkt.concept.type.fields
import app.graphkt.concept.types
import app.graphkt.graphql.buildSchema
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.graphql.query.GraphQlQueryOutput
import app.graphkt.graphql.query.GraphQlQuerySelectionField
import app.graphkt.graphql.type.GraphQlType
import app.graphkt.graphql.type.GraphQlTypeField
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AppTests {

    @Nested
    inner class SchemaTests {

        @ParameterizedTest
        @ValueSource(strings = ["MySchema", "MyOtherSchema"])
        fun test_schema_name(name: String) {
            val actual = buildSchema(name).name
            assertEquals(name, actual)
        }
    }

    @Nested
    inner class SchemaQueryTests {

        @Test
        fun test_schema_queries() {
            val actual = buildSchema {
                queries {
                    Query(name = "exampleQuery") {
                        inputs { Input { name("ExampleInput") } }

                        output {
                            FieldSelection { name("fieldSelection1") }
                            FieldSelection { name("fieldSelection2") }
                        }
                    }
                }
            }.queries

            val expected = listOf(
                GraphQlQuery(
                    name = "exampleQuery",
                    inputs = mutableListOf(
                        GraphQlQueryInput("ExampleInput"),
                    ),
                    output = GraphQlQueryOutput(
                        fields = mutableListOf(
                            GraphQlQuerySelectionField("fieldSelection1"),
                            GraphQlQuerySelectionField("fieldSelection2"),
                        )
                    )
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Nested
    inner class SchemaTypeTests {

        @Test
        fun test_schema_type_fields() {
            val actual = buildSchema {
                types {
                    Type("MyType") {
                        fields {
                            Field { name("destinationLatLng"); type("[String!]!") }
                            Field { name("originLatLng"); type("[String!]!") }
                            Field { name("waypoints"); type("[String!]!") }
                        }
                    }
                }
            }.types

            val expected = listOf(
                GraphQlType("MyType", mutableListOf(
                    GraphQlTypeField("destinationLatLng", "[String!]!"),
                    GraphQlTypeField("originLatLng", "[String!]!"),
                    GraphQlTypeField("waypoints", "[String!]!"),
                ))
            )

            assertEquals(expected, actual)
        }

        @ParameterizedTest
        @ValueSource(booleans = [false, true])
        fun test_schema_types(enabled: Boolean) {
            val actual = buildSchema {
                types {
                    Type("MyType") {
                        generateInput(enabled)
                        generateFragment(!enabled)
                    }
                }
            }.types

            val expected = listOf(
                GraphQlType("MyType", generateFragment = !enabled, generateInput = enabled)
            )

            assertEquals(expected, actual)
        }
    }
}