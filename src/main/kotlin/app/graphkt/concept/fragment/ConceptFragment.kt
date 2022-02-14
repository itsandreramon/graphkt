package app.graphkt.concept.fragment

import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.fragment.GraphQlFragment

class FragmentBuilder(
    private val fragment: GraphQlFragment,
    private val onBuiltCallback: (GraphQlFragment) -> Unit,
) {
    fun build(): FragmentBuilder {
        onBuiltCallback(fragment)
        return this
    }
}

fun FragmentDefinitions.Fragment(name: String, fragmentBuilder: FragmentBuilder.() -> Unit) {
    fragment.apply {
        this.name = name
    }

    fragmentBuilder(FragmentBuilder(fragment, onBuiltCallback = {
        schemaDefinition.schema.addFragment(this, it)
    }).build())
}


fun FragmentDefinitions.fields(fields: FragmentFieldDefinitions.() -> Unit) {
    fields(FragmentFieldDefinitions(this))
}

/**
 * State-holder that is used to map a given fragment definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the fragment is being defined on.
 */
class FragmentDefinitions(
    val fragment: GraphQlFragment,
    val schemaDefinition: SchemaDefinition,
)