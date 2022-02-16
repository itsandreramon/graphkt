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
import app.graphkt.transformer.reducer.InputFieldReducerImpl
import app.graphkt.transformer.reducer.InputReducerImpl
import app.graphkt.transformer.reducer.QueryReducerImpl
import app.graphkt.transformer.reducer.TypeFieldReducerImpl
import app.graphkt.transformer.reducer.TypeReducerImpl

val schema = buildSchema(name = "MySchema") {
    queries {
        Query(name = "optimizeDirections") {
            inputs {
                Input { name("directions"); type("DirectionsInput!") }
            }

            // singular entity defined directly on QueryBuilder
            Output(type = "Directions!") {
                FieldSelection { name("originLatLng") }
                FieldSelection { name("destinationLatLng") }
                FieldSelection { name("waypoints") }
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
        val typeFieldReducer = TypeFieldReducerImpl()
        val typeReducer = TypeReducerImpl(typeFieldReducer)
        val inputFieldReducer = InputFieldReducerImpl()
        val inputReducer = InputReducerImpl(inputFieldReducer)
        val schemaTransformer = SchemaTransformerImpl(queryReducer, typeReducer, inputReducer)
        val fileWriter = FileWriterImpl()
        SchemaWriterImpl(schemaTransformer, fileWriter)
    }
}