/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt

import app.graphkt.concept.fragment.Field
import app.graphkt.concept.fragment.Fragment
import app.graphkt.concept.fragment.fields
import app.graphkt.concept.fragments
import app.graphkt.concept.queries
import app.graphkt.concept.query.FieldSelection
import app.graphkt.concept.query.FragmentSelection
import app.graphkt.concept.query.Input
import app.graphkt.concept.query.Output
import app.graphkt.concept.query.Query
import app.graphkt.concept.query.inputs
import app.graphkt.concept.type.Field
import app.graphkt.concept.type.Type
import app.graphkt.concept.type.fields
import app.graphkt.concept.types
import app.graphkt.graphql.buildSchema
import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.fragment.GraphQlFragmentField
import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.graphql.query.GraphQlQueryOutput
import app.graphkt.graphql.query.GraphQlQuerySelectionField
import app.graphkt.graphql.query.GraphQlQuerySelectionFragment
import app.graphkt.graphql.type.GraphQlType
import app.graphkt.graphql.type.GraphQlTypeField
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AppTests {

    @Nested inner class SchemaTests {

        @ParameterizedTest
        @ValueSource(strings = ["MySchema", "MyOtherSchema"])
        fun test_schema_name(name: String) {
            val actual = buildSchema(name).name
            assertEquals(name, actual)
        }
    }

    @Nested inner class SchemaFragmentTests {

        @Test fun test_schema_fragments() {
            val actual = buildSchema {
                fragments {
                    Fragment(name = "exampleFragment") {
                        fields {
                            Field { name("exampleField"); type("[String!]!") }
                        }
                    }
                }
            }.fragments

            val expected = listOf(
                GraphQlFragment(
                    name = "exampleFragment",
                    fields = mutableListOf(
                        GraphQlFragmentField(
                            name = "exampleField",
                            type = "[String!]!",
                        )
                    )
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Nested inner class SchemaQueryTests {

        @Test fun test_schema_queries() {
            val actual = buildSchema {
                queries {
                    Query(name = "exampleQuery") {
                        inputs { Input { name("ExampleInput") } }

                        Output(type = "Example!") {
                            FieldSelection { name("fieldSelection1") }
                            FieldSelection { name("fieldSelection2") }
                            FragmentSelection { name("fragmentSelection1") }
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
                        type = "Example!",
                        fields = mutableListOf(
                            GraphQlQuerySelectionField("fieldSelection1"),
                            GraphQlQuerySelectionField("fieldSelection2"),
                        ),
                        fragments = mutableListOf(
                            GraphQlQuerySelectionFragment("fragmentSelection1"),
                        ),
                    )
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Nested inner class SchemaTypeTests {

        @Test fun test_schema_type_fields() {
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

        @ValueSource(booleans = [false, true])
        @ParameterizedTest
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