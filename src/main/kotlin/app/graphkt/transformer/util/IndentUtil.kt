/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.transformer.util

fun createIndentOfSize(size: Int, char: String = " "): String {
    return buildString {
        for (i in 1..size) {
            append(char)
        }
    }
}