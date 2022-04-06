/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.io

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.transformer.QueryTransformer
import app.graphkt.transformer.SchemaTransformer

interface SchemaWriter {
    fun write(schema: GraphQlSchema)
}

class SchemaWriterImpl(
    private val schemaTransformer: SchemaTransformer,
    private val queryTransformer: QueryTransformer,
    private val fileWriter: FileWriter,
) : SchemaWriter {

    override fun write(schema: GraphQlSchema) {
        fileWriter.write(
            text = schemaTransformer.transform(schema),
            path = "./dist/${schema.name}.graphql"
        )

        schema.queries
            .distinctBy { it.name }
            .onEach { query ->
                val name = "Query${query.name.replaceFirstChar { it.uppercase() }}"

                fileWriter.write(
                    text = queryTransformer.transform(query),
                    path = "./dist/$name.graphql"
                )
            }
    }
}