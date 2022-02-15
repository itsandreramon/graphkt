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
import app.graphkt.io.FileWriterImpl
import app.graphkt.io.SchemaWriter
import app.graphkt.io.SchemaWriterImpl
import app.graphkt.transformer.SchemaTransformerImpl
import app.graphkt.transformer.reducer.QueryReducerImpl

val schema = buildSchema(name = "MySchema") {
    queries {
        Query(name = "optimizeDirections") {
            inputs {
                Input { name("directions"); type("DirectionsInput!") }
                Input { name("example"); type("ExampleInput!") }
            }

            // singular entity defined directly on QueryBuilder
            Output(type = "Directions!") {
                FieldSelection { name("originLatLng") }
                FieldSelection { name("destinationLatLng") }
                FieldSelection { name("waypoints") }

                FragmentSelection { name("exampleFragment") }
            }
        }

        Query(name = "exampleQuery") {
            inputs {
                Input { name("example1"); type("ExampleInput!") }
                Input { name("example2"); type("ExampleInput!") }
            }

            // singular entity defined directly on QueryBuilder
            Output(type = "Example!") {
                FieldSelection { name("example1") }
                FieldSelection { name("example2") }
                FragmentSelection { name("example3") }
            }
        }
    }

    fragments {
        Fragment(name = "exampleFragment") {
            fields {
                Field { name("exampleField"); type("[String!]!") }
            }
        }
    }

    types {
        Type(name = "Directions") {
            generateFragment(true)
            generateInput(true)

            fields {
                Field { name("originLatLng"); type("[String!]!") }
                Field { name("destinationLatLng"); type("[String!]!") }
                Field { name("waypoints"); type("[String!]!") }
            }
        }
    }
}

fun main() {
    App.schemaWriter.write(schema)
}

object App {

    val schemaWriter: SchemaWriter by lazy {
        val queryReducer = QueryReducerImpl()
        val schemaTransformer = SchemaTransformerImpl(queryReducer)
        val fileWriter = FileWriterImpl()
        SchemaWriterImpl(schemaTransformer, fileWriter)
    }
}