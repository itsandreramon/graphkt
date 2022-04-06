/*
 * Copyright 2022 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package app.graphkt.concept

abstract class AbstractGraphQlBuilder<T : AbstractGraphQlConcept>(
    val concept: T,
    open val onBuiltCallback: (T) -> Unit,
) {
    fun build(): AbstractGraphQlBuilder<T> {
        onBuiltCallback(concept)
        return this
    }
}