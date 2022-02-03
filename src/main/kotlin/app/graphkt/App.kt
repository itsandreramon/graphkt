package app.graphkt

val schema = buildSchema(name = "MySchema") {
	types {
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