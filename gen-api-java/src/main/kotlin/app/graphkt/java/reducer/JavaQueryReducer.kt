package app.graphkt.java.reducer

import app.graphkt.graphql.query.GraphQlQuery
import app.graphkt.graphql.query.GraphQlQueryInput
import app.graphkt.transformer.reducer.QueryReducer
import app.graphkt.util.createIndentOfSize

interface JavaQueryReducer : QueryReducer {
    fun reduceAsSignature(queries: List<GraphQlQuery>, indent: String = createIndentOfSize(4)): String
}

class JavaQueryReducerImpl : JavaQueryReducer {
    override fun reduce(queries: List<GraphQlQuery>, indent: String): String {
        return buildString {
            queries.onEach { query ->
                append("""
                    |@Override
                    |public ${getQuerySignatureAsString(query)} {
                    |    ApolloQueryCall<${query.capitalizedName}.Data> call = apollo.query(
                    |       ${query.capitalizedName}.builder()
                    |           ${getQueryInputsAsBuilder(query.inputs)}
                    |           .build()
                    |    );
                    |
                    |    return Observable<Result<${query.capitalizedName}.Data>> observable = Rx3Apollo.from(call)
                    |       .subscribeOn(schedulerProvider.io())
                    |       .observeOn(schedulerProvider.main())
                    |       .map(response -> Result.onNullWrapError(response.getData()))
                    |}
                """.trimMargin("|"))
            }
        }
    }

    override fun reduceAsSignature(queries: List<GraphQlQuery>, indent: String): String {
        return buildString {
            queries.onEach { query ->
                append("""
                    |public ${getQuerySignatureAsString(query)}
                """.trimMargin("|"))
            }
        }
    }

    private fun getQueryInputsAsBuilder(inputs: List<GraphQlQueryInput>): String {
        return buildString {
            inputs.onEach { input ->
                append(".${input.name}(${input.name})\n")
            }
        }
    }

    private fun getQuerySignatureAsString(query: GraphQlQuery): String {
        return "Observable<${query.capitalizedName}.Data> ${query.name}(${getQueryInputParameters(query)})"
    }

    private fun getQueryInputParameters(query: GraphQlQuery): String {
        fun getQueryInput(input: GraphQlQueryInput): String {
            return "${input.type} ${input.name}"
        }

        return query.inputs.joinToString(
            separator = ", ",
            transform = ::getQueryInput,
        )
    }
}