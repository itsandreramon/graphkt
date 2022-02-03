package app.graphkt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppTests {

	@Test
	fun test_schema_type_definition() {
		val actual = buildSchema("MySchema") {
			types {
				Type("MyType") {
					generateInput(true)
					generateFragment(true)
				}
			}
		}.types

		val expected = listOf(
			GraphQlType("MyType", generateFragment = true, generateInput = true)
		)

		assertEquals(expected, actual)
	}
}