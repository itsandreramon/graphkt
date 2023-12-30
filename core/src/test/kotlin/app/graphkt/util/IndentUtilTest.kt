/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IndentUtilTest {

    @Test
    fun test_indent_added_per_line() {
        val expected = """
            |    line one
            |    line two
            |    little bit longer line three
            |    after this line should not be an empty new line
        """.trimMargin("|")

        val actual = """
            |line one
            |line two
            |little bit longer line three
            |after this line should not be an empty new line
        """.trimMargin("|").applyIndentPerLine(4)

        assertEquals(expected, actual)
    }
}