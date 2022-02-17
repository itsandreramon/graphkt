/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept.query

import app.graphkt.concept.AbstractGraphQlBuilder
import app.graphkt.graphql.query.GraphQlQuerySelectionField

class SelectionFieldBuilder(
    private val field: GraphQlQuerySelectionField,
    override val onBuiltCallback: (GraphQlQuerySelectionField) -> Unit,
) : AbstractGraphQlBuilder<GraphQlQuerySelectionField>(field, onBuiltCallback) {
    fun name(name: String) {
        field.name = name
    }
}

fun QueryBuilder.FieldSelection(builder: SelectionFieldBuilder.() -> Unit) {
    val field = GraphQlQuerySelectionField()

    builder(SelectionFieldBuilder(field, onBuiltCallback = {
        this.query.output.fields.add(it)
    }).build() as SelectionFieldBuilder)
}