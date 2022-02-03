package app.graphkt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppTests {

	@Test
	fun test_schema_name() {
		val actual = buildSchema("MySchema1") {}.name
		val expected = "MySchema"
		assertEquals(expected, actual)
	}

	@Test
	fun test_schema_types() {
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