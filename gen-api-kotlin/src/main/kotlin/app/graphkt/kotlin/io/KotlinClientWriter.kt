/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.io

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.io.FileWriter
import app.graphkt.io.SchemaWriter
import app.graphkt.transformer.SchemaTransformer

class KotlinClientWriter(
    private val transformer: SchemaTransformer,
    private val fileWriter: FileWriter,
) : SchemaWriter {

    override fun write(schema: GraphQlSchema) {
        fileWriter.write(
            text = transformer.transform(schema),
            path = "./dist/RemoteDataSource.kt"
        )
    }
}