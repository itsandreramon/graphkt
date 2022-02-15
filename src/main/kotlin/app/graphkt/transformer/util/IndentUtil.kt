package app.graphkt.transformer.util

fun createIndentOfSize(size: Int, char: String = " "): String {
    return buildString {
        for (i in 1..size) {
            append(char)
        }
    }
}