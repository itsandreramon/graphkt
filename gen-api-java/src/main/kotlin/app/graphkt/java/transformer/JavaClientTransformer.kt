package app.graphkt.java.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.java.reducer.JavaQueryReducer
import app.graphkt.transformer.SchemaTransformer
import app.graphkt.util.applyIndentPerLine

class JavaClientTransformerImpl(
    private val queryReducer: JavaQueryReducer,
) : SchemaTransformer {
    override fun transform(schema: GraphQlSchema): String {
        return buildString {
            append("""
                |import com.apollographql.apollo3.ApolloClient
                |import com.apollographql.apollo3.exception.ApolloException
                |import 
                |import kotlinx.coroutines.withContext
                |import javax.inject.Inject
                |
                |interface RemoteDataSource {
                |${queryReducer.reduceAsSignature(schema.queries).applyIndentPerLine(size = 4)}
                |}
                |
                |class RemoteDataSourceImpl(
                |    private val apollo: ApolloClient,
                |) : RemoteDataSource {
                |
                |${queryReducer.reduce(schema.queries).applyIndentPerLine(size = 4)}
                |}
            """.trimMargin("|"))
        }
    }
}