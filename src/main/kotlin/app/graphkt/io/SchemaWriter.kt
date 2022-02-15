/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.io

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.transformer.SchemaTransformer

interface SchemaWriter {
    fun write(schema: GraphQlSchema)
}

class SchemaWriterImpl(
    private val schemaTransformer: SchemaTransformer,
    private val fileWriter: FileWriter,
) : SchemaWriter {

    override fun write(schema: GraphQlSchema) {
        val transformedSchema = schemaTransformer.transform(schema)
        fileWriter.write(transformedSchema, "./dist/${schema.name}.graphqls")
    }
}