/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin

import app.graphkt.graphql.GraphQlSchema
import app.graphkt.kotlin.reducer.KotlinQueryReducer
import com.squareup.kotlinpoet.FunSpec

interface KotlinClientTransformer {
    fun transform(schema: GraphQlSchema): KotlinClient
}

class KotlinClientTransformerImpl(
    private val queryReducer: KotlinQueryReducer,
) : KotlinClientTransformer {

    override fun transform(schema: GraphQlSchema): KotlinClient {
        return KotlinClient(buildString {
            append("""
                |import com.apollographql.apollo3.ApolloClient
                |import com.apollographql.apollo3.exception.ApolloException
                |import kotlinx.coroutines.withContext
                |import javax.inject.Inject
                |
                |${queryReducer.reduce(schema.queries)}
            """.trimMargin("|"))
        })
    }
}