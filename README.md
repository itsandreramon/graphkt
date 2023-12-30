# graphkt

Generate a GraphQL schema and corresponding Apollo client from a shared Kotlin DSL built as part of the Project III course at the University of Applied Sciences Brandenburg.

## Supported Clients
- Java
- Kotlin

## Kotlin DSL

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

### Schema

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

### Client query

```graphql
query OptimizeDirections($directions: DirectionsInput!) {
    optimizeDirections(directions: $directions) {
        ...directionsFragment
    }
}
```

### Java Client
```java
class RemoteDataSourceImpl implements RemoteDataSource {

    private final ApolloClient apollo;
    private final SchedulerProvider schedulerProvider;

    public RemoteDataSourceImpl(ApolloClient apollo, SchedulerProvider schedulerProvider) {
        this.apollo = apollo;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<OptimizeDirections.Data> optimizeDirections(DirectionsInput directions) {
        ApolloQueryCall<OptimizeDirections.Data> call = apollo.query(
             OptimizeDirections.builder()
                 .directions(directions)
                 .build()
        );

        return Rx3Apollo.from(call)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .map(response -> response.getData());
    }
}
```
