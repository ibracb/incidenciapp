# Domain model

The platform centres on the **issue** and its lifecycle, as well as the **technician** assigned to it.

## Model Diagram

```mermaid
classDiagram
    class Issue {
        +String id
        +String description
        +String location
        +String date
        +IssueStatus state
        +Technician technician
    }
    class IssueStatus {
        <<enumeration>>
        PENDING
        ASSIGNED
        SOLVED
    }
    class Technician {
        +String name
        +String phone
    }

    Issue "1" --> "0..1" Technician
    Issue "1" --> "1" IssueStatus
```

## Notes on modelling

- `Issue` is the main entity; its lifecycle is controlled by `IssueStatus`.
- `IssueStatus` is an enumeration with three values representing the valid transitions: `PENDING` → `ASSIGNED` → `SOLVED`. There are no backward transitions.
- `Technician` is a value object (it has no identity of its own).
- A `Technician` only exists when the issue is in the `ASSIGNED` or `SOLVED` state; in the `PENDING` state, it doesn't exist.