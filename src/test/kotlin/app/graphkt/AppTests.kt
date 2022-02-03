package app.graphkt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AppTests {

	@Nested
	inner class SchemaTests {

		@ParameterizedTest
		@ValueSource(strings = ["MySchema", "MyOtherSchema"])
		fun test_schema_name(name: String) {
			val actual = buildSchema(name) {}.name
			assertEquals(name, actual)
		}
	}

	@Nested
	inner class SchemaTypeTests {

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