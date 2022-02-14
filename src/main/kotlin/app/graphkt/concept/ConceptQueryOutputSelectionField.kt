/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

import app.graphkt.graphql.GraphQlQuerySelectionField

class SelectionFieldBuilder(
    private val field: GraphQlQuerySelectionField,
    private val onBuiltCallback: (GraphQlQuerySelectionField) -> Unit,
) {
    fun name(name: String) {
        field.name = name
    }

    fun build(): SelectionFieldBuilder {
        onBuiltCallback(field)
        return this
    }
}

fun OutputDefinition.FieldSelection(builder: SelectionFieldBuilder.() -> Unit) {
    val field = GraphQlQuerySelectionField()

    builder(SelectionFieldBuilder(field, onBuiltCallback = {
        this.queryDefinitions.query.output.fields.add(it)
    }).build())
}