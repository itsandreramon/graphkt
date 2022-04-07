/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.io

import java.nio.file.Files
import java.nio.file.Paths

interface FileWriter {
    fun write(text: String, path: String)
}

class FileWriterImpl : FileWriter {

    override fun write(text: String, path: String) {
        val file = Paths.get(path)
        Files.createDirectories(file.parent)
        Files.write(file, text.toByteArray())
    }
}