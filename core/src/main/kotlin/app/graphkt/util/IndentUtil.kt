/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.util

fun createIndentOfSize(size: Int, char: String = " "): String {
    return buildString {
        for (i in 1..size) {
            append(char)
        }
    }
}

private fun newLineIfNotLastLine(currLine: Int, lastLine: Int): String {
    assert(currLine <= lastLine) {
        "currLine line cannot be bigger than lastLine ."
    }

    return if (currLine < lastLine) {
        "\n"
    } else {
        ""
    }
}

fun String.applyIndentPerLine(size: Int, indent: String = " "): String {
    val lines = this.lines()
    return lines
        .mapIndexed { i, s -> "${createIndentOfSize(size, indent)}${s}${newLineIfNotLastLine(i, lines.size - 1)}" }
        .reduce { acc, s -> "${acc}${s}" }
}