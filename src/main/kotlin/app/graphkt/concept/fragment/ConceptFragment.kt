/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.fragment

import app.graphkt.concept.SchemaDefinition
import app.graphkt.graphql.fragment.GraphQlFragment

class FragmentBuilder(
    val fragment: GraphQlFragment,
    private val onBuiltCallback: (GraphQlFragment) -> Unit,
) {
    fun build(): FragmentBuilder {
        onBuiltCallback(fragment)
        return this
    }
}

fun FragmentDefinitions.Fragment(name: String, type: String, fragmentBuilder: FragmentBuilder.() -> Unit) {
    currentFragment = GraphQlFragment().apply {
        this.name = name
        this.type = type
    }

    fragmentBuilder(FragmentBuilder(currentFragment, onBuiltCallback = {
        this.schemaDefinition.schema.addFragment(this, it)
    }).build())
}


fun FragmentBuilder.fields(fields: FragmentFieldDefinitions.() -> Unit) {
    fields(FragmentFieldDefinitions(fragment))
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