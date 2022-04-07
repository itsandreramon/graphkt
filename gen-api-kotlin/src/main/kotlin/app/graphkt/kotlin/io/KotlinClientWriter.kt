/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.io

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.io.FileWriter
import app.graphkt.kotlin.transformer.KotlinClientTransformer

interface KotlinClientWriter {
    fun write(schema: GraphQlSchema)
}

class KotlinClientWriterImpl(
    private val kotlinClientTransformer: KotlinClientTransformer,
    private val fileWriter: FileWriter,
) : KotlinClientWriter {

    override fun write(schema: GraphQlSchema) {
        fileWriter.write(
            text = kotlinClientTransformer.transform(schema),
            path = "./dist/RemoteDataSource.kt"
        )
    }
}