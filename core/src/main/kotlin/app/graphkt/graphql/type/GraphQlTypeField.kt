/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.graphql.type

import app.graphkt.concept.AbstractGraphQlConcept

/**
 * Defines attributes that are used to generate fields of a GraphQL type.
 *
 * @param name The name of the field.
 * @param type The type of the field.
 */
data class GraphQlTypeField(
    var name: String = "",
    var type: String = "",
) : AbstractGraphQlConcept()