package app.graphkt

import app.graphkt.concept.Field
import app.graphkt.concept.Type
import app.graphkt.concept.fields
import app.graphkt.concept.types
import app.graphkt.graphql.buildSchema

val schema = buildSchema(name = "MySchema") {
	types {
		Type(name = "Example")
		Type(name = "Directions") {
			generateFragment(true)
			generateInput(true)

			fields {
				Field<String> { name("originLatLng"); }
				Field<String> { name("destinationLatLng"); }
				Field<Pair<Double, Double>> { name("waypoints"); }
			}
		}
	}
}

fun main() {
	println(schema)
}