/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin.transformer

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.kotlin.reducer.KotlinQueryReducer
import app.graphkt.util.applyIndent

interface KotlinClientTransformer {
    fun transform(schema: GraphQlSchema): String
}

class KotlinClientTransformerImpl(
    private val queryReducer: KotlinQueryReducer,
) : KotlinClientTransformer {

    override fun transform(schema: GraphQlSchema): String {
        return buildString {
            append("""
                |import com.apollographql.apollo3.ApolloClient
                |import com.apollographql.apollo3.exception.ApolloException
                |import kotlinx.coroutines.withContext
                |import javax.inject.Inject
                |
                |interface RemoteDataSource {
                |${queryReducer.reduceAsSignature(schema.queries).applyIndent(size = 4)}
                |}
                |
                |class RemoteDataSourceImpl(
                |    private val apollo: ApolloClient
                |) : RemoteDataSource {
                |
                |${queryReducer.reduce(schema.queries).applyIndent(size = 4)}
                |}
            """.trimMargin("|"))
        }
    }
}