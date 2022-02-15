/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.graphql.query.GraphQlQuerySelectionFragment

class SelectionFragmentBuilder(
    private val field: GraphQlQuerySelectionFragment,
    private val onBuiltCallback: (GraphQlQuerySelectionFragment) -> Unit,
) {
    fun name(name: String) {
        field.name = name
    }

    fun build(): SelectionFragmentBuilder {
        onBuiltCallback(field)
        return this
    }
}

fun QueryBuilder.FragmentSelection(builder: SelectionFragmentBuilder.() -> Unit) {
    val fragment = GraphQlQuerySelectionFragment()

    builder(SelectionFragmentBuilder(fragment, onBuiltCallback = {
        this.query.output.fragments.add(it)
    }).build())
}