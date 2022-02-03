package app.graphkt

import app.graphkt.concept.Field
import app.graphkt.concept.Type
import app.graphkt.concept.fields
import app.graphkt.concept.types
import app.graphkt.graphql.GraphQlType
import app.graphkt.graphql.GraphQlTypeField
import app.graphkt.graphql.buildSchema
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
	inner class SchemaTypeTests {

		@Test
		fun test_schema_type_fields() {
			val actual = buildSchema("MySchema") {
				types {
					Type("MyType") {
						fields {
							Field<String> { name("destinationLatLng") }
							Field<String> { name("originLatLng") }
							Field<Pair<Double, Double>> { name("waypoints") }
						}
					}
				}
			}.types

			val expected = listOf(
				GraphQlType("MyType", mutableListOf(
					GraphQlTypeField("destinationLatLng", null),
					GraphQlTypeField("originLatLng", null),
					GraphQlTypeField("waypoints", null),
				))
			)

			assertEquals(expected, actual)
		}

		@ParameterizedTest
		@ValueSource(booleans = [false, true])
		fun test_schema_types(enabled: Boolean) {
			val actual = buildSchema("MySchema") {
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