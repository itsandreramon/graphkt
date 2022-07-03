package app.graphkt.java.io

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.io.FileWriter
import app.graphkt.io.SchemaWriter
import app.graphkt.transformer.SchemaTransformer

class JavaClientWriter(
    private val transformer: SchemaTransformer,
    private val fileWriter: FileWriter,
) : SchemaWriter {
    override fun write(schema: GraphQlSchema) {
        fileWriter.write(
            text = transformer.transform(schema),
            path = "./dist/RemoteDataSource.java"
        )
    }
}