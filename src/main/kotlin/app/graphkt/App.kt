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
import app.graphkt.concept.query.Query
import app.graphkt.concept.query.inputs
import app.graphkt.concept.query.output
import app.graphkt.concept.type.Field
import app.graphkt.concept.type.Type
import app.graphkt.concept.type.fields
import app.graphkt.concept.types
import app.graphkt.graphql.buildSchema

val schema = buildSchema(name = "MySchema") {
    queries {
        Query(name = "optimizeDirections") {
            inputs {
                Input { name("Directions") }
            }

            output {
                FieldSelection { name("originLatLng") }
                FieldSelection { name("destinationLatLng") }
                FieldSelection { name("waypoints") }

                FragmentSelection { name("exampleFragment") }
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
    println(schema)
}