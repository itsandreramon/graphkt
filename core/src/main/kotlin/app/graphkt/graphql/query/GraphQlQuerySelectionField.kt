/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.query

import app.graphkt.concept.AbstractGraphQlConcept

data class GraphQlQuerySelectionField(
    var name: String = "",
) : AbstractGraphQlConcept(), GraphQlQuerySelection