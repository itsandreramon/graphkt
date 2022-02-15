package app.graphkt.concept.fragment

import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.fragment.GraphQlFragment

class FragmentBuilder(
    val fragment: GraphQlFragment,
    private val onBuiltCallback: (GraphQlFragment) -> Unit,
    val fragmentDefinitions: FragmentDefinitions,
) {
    fun build(): FragmentBuilder {
        onBuiltCallback(fragment)
        return this
    }
}

fun FragmentDefinitions.Fragment(name: String, fragmentBuilder: FragmentBuilder.() -> Unit) {
    currentFragment = GraphQlFragment().apply {
        this.name = name
    }

    fragmentBuilder(FragmentBuilder(currentFragment, onBuiltCallback = {
        schemaDefinition.schema.addFragment(this, it)
    }, this).build())
}


fun FragmentBuilder.fields(fields: FragmentFieldDefinitions.() -> Unit) {
    fields(FragmentFieldDefinitions(fragment, fragmentDefinitions))
}

/**
 * State-holder that is used to map a given fragment definition and corresponding schema.
 *
 * @param type The currently defined GraphQL type.
 * @param schemaDefinition The current schema definition the fragment is being defined on.
 */
class FragmentDefinitions(
    var currentFragment: GraphQlFragment = GraphQlFragment(),
    val schemaDefinition: SchemaDefinition,
)