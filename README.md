# graphkt

Generate a GraphQL schema and corresponding Apollo client from a shared Kotlin DSL.

## Example

```kotlin
val schema = buildSchema(name = "MySchema") {
    queries {
        Query(name = "optimizeDirections") {
            inputs {
                Input { name("directions"); type("DirectionsInput!") }
            }

            Output(type = "Directions!") {
                FragmentSelection { name("directionsFragment") }
            }
        }
    }

    types {
        Type(name = "Directions") {
            generateFragment(true)
            generateInput(true)

            fields {
                Field { name("originLatLng"); type("[String!]!") }
                Field { name("destinationLatLng"); type("[String!]!") }
                Field { name("waypoints"); type("[String!]!") }
            }
        }
    }
}
```

## Result

```graphql
schema {
    query: Query
}

type Query {
    optimizeDirections(directions: DirectionsInput!): Directions!
}

type Directions {
    originLatLng: [String!]!
    destinationLatLng: [String!]!
    waypoints: [String!]!
}

input DirectionsInput {
    originLatLng: [String!]!
    destinationLatLng: [String!]!
    waypoints: [String!]!
}

fragment directionsFragment on Directions {
    originLatLng
    destinationLatLng
    waypoints
}
```