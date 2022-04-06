/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.concept.AbstractGraphQlBuilder
import app.graphkt.graphql.query.GraphQlQuerySelectionFragment

class SelectionFragmentBuilder(
    private val field: GraphQlQuerySelectionFragment,
    override val onBuiltCallback: (GraphQlQuerySelectionFragment) -> Unit,
) : AbstractGraphQlBuilder<GraphQlQuerySelectionFragment>(field, onBuiltCallback) {
    fun name(name: String) {
        field.name = name
    }
}

fun QueryBuilder.FragmentSelection(builder: SelectionFragmentBuilder.() -> Unit) {
    val fragment = GraphQlQuerySelectionFragment()

    builder(SelectionFragmentBuilder(fragment, onBuiltCallback = {
        this.query.output.fragments.add(it)
    }).build() as SelectionFragmentBuilder)
}