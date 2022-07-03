/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.kotlin.reducer.KotlinQueryReducer
import app.graphkt.transformer.SchemaTransformer
import app.graphkt.util.applyIndentPerLine

class KotlinClientTransformer(
    private val queryReducer: KotlinQueryReducer,
) : SchemaTransformer {

    override fun transform(schema: GraphQlSchema): List<Pair<String, String>> {
        val client = buildString {
            append("""
                |import com.apollographql.apollo3.ApolloClient
                |import com.apollographql.apollo3.exception.ApolloException
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

        return listOf(
            Pair("RemoteDataSource", client)
        )
    }
}