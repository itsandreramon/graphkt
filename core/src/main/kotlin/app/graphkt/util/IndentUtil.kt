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

fun String.applyIndent(size: Int, indent: String = " "): String {
    return this.lines()
        .map { "${createIndentOfSize(size, indent)}${it}" }
        .reduce { acc, s -> "${acc}${s}" }
}