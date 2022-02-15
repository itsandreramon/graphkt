package app.graphkt.concept.fragment

import app.graphkt.graphql.fragment.GraphQlFragment
import app.graphkt.graphql.fragment.GraphQlFragmentField

/**
 * Builder that is used to modify a field of a GraphQL type.
 *
 * @param field The field of a GraphQL type that is being modified.
 * @param onBuiltCallback Lambda that is getting executed when all modifications are done.
 */
class FragmentFieldBuilder(
    private val field: GraphQlFragmentField,
    private val onBuiltCallback: (GraphQlFragmentField) -> Unit,
) {
    fun name(value: String) {
        field.name = value
    }

    fun type(value: String) {
        field.type = value
    }

    fun build(): FragmentFieldBuilder {
        onBuiltCallback(field)
        return this
    }
}

fun FragmentFieldDefinitions.Field(builder: FragmentFieldBuilder.() -> Unit) {
    val field = GraphQlFragmentField()

    builder(FragmentFieldBuilder(field, onBuiltCallback = {
        this.fragment.fields.add(it)
    }).build())
}

data class FragmentFieldDefinitions(
    val fragment: GraphQlFragment,
    val fragmentDefinitions: FragmentDefinitions,
)