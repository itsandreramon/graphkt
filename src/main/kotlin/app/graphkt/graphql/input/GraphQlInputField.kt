/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.input

import app.graphkt.concept.AbstractGraphQlConcept

class GraphQlInputField(
    var name: String = "",
    var type: String = "",
) : AbstractGraphQlConcept()