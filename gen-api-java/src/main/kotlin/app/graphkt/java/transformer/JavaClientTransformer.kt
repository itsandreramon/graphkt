package app.graphkt.java.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.java.reducer.JavaQueryReducer
import app.graphkt.transformer.SchemaTransformer
import app.graphkt.util.applyIndentPerLine
import app.graphkt.util.createIndentOfSize

class JavaClientTransformer(
    private val queryReducer: JavaQueryReducer,
) : SchemaTransformer {
    override fun transform(schema: GraphQlSchema): List<Pair<String, String>> {
        val protocol = buildString {
            append("""
                |interface RemoteDataSource {
                |${queryReducer.reduceAsSignature(schema.queries).applyIndentPerLine(size = 4)}
                |}
            """.trimMargin("|"))
        }

        val client = buildString {
            append("""
                |import com.apollographql.apollo3.ApolloClient;
                |import io.reactivex.rxjava3.core.Observable;
                |
                |class RemoteDataSourceImpl implements RemoteDataSource {
                |
                |    private final ApolloClient apollo;
                |    private final SchedulerProvider schedulerProvider;
                |
                |    public RemoteDataSourceImpl(ApolloClient apollo, SchedulerProvider schedulerProvider) {
                |        this.apollo = apollo;
                |        this.schedulerProvider = schedulerProvider;
                |    }
                |
                |${queryReducer.reduce(schema.queries).applyIndentPerLine(size = 4)}
                |}
            """.trimMargin("|"))
        }

        return listOf(
            Pair("RemoteDataSource", protocol),
            Pair("RemoteDataSourceImpl", client),
        )
    }
}