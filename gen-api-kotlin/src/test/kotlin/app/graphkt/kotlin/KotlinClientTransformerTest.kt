/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.kotlin

import app.graphkt.concept.queries
import app.graphkt.concept.query.Input
import app.graphkt.concept.query.Query
import app.graphkt.concept.query.inputs
import app.graphkt.graphql.buildSchema
import app.graphkt.kotlin.reducer.KotlinQueryReducer
import app.graphkt.kotlin.reducer.KotlinQueryReducerImpl
import app.graphkt.kotlin.transformer.KotlinClientTransformer
import app.graphkt.kotlin.transformer.KotlinClientTransformerImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KotlinClientTransformerTest {

    private var kotlinClientTransformer: KotlinClientTransformer? = null
    private val kotlinQueryReducer: KotlinQueryReducer = KotlinQueryReducerImpl()

    @BeforeEach
    fun setUp() {
        kotlinClientTransformer = KotlinClientTransformerImpl(kotlinQueryReducer)
    }

    @AfterEach
    fun tearDown() {
        kotlinClientTransformer = null
    }

    @Test fun test_query_reducer() {
        val exampleSchema = buildSchema {
            queries {
                Query(name = "exampleQuery") {
                    inputs { Input { name("exampleInput"); type("ExampleInput") } }
                }
            }
        }

        val actual = kotlinQueryReducer.reduce(exampleSchema.queries)

        val expected = """
            |override suspend fun exampleQuery(exampleInput: ExampleInput): ExampleQuery.Data {
            |    return apollo.query(ExampleQuery()).execute()
            |}
        """.trimMargin("|")

        assertEquals(expected, actual)
    }

    @Test fun test_query_signature_reducer() {
        val exampleSchema = buildSchema {
            queries {
                Query(name = "exampleQuery") {
                    inputs { Input { name("exampleInput"); type("ExampleInput") } }
                }
            }
        }

        val actual = kotlinQueryReducer.reduceAsSignature(exampleSchema.queries)

        val expected = """
            |suspend fun exampleQuery(exampleInput: ExampleInput): ExampleQuery.Data
        """.trimMargin("|")

        assertEquals(expected, actual)
    }
}