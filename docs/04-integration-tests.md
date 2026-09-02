# Integration tests

Integration tests for the IncidenciApp REST API, implemented as a [Postman](https://www.postman.com/) collection.

## Prerequisites

The application must be running. See [Compilation and execution](../README.md#compilation-and-execution) for setup instructions.

## Running the tests

1. Import the environment [**IncidenciApp-local**](../postman/IncidenciApp-local.postman_environment.json) in Postman.
2. Import the collection [**IncidenciApp**](../postman/IncidenciApp.postman_collection.json).
3. Select the **IncidenciApp-local**(../postman/IncidenciApp-local.postman_environment.json) environment.
4. Run the collection sequentially (order matters, as tests depend on IDs created by earlier requests).

## Test flow

> **Note:** All endpoints use the `/api` prefix.

The collection executes the following sequence:

| # | Test | Method | Endpoint | Expected |
|---|---|---|---|---|
| 1 | Register first issue | POST | `/incidencias` | 201 Created |
| 2 | Register second issue | POST | `/incidencias` | 201 Created |
| 3 | Register third issue | POST | `/incidencias` | 201 Created |
| 4 | Assign technician (first) | PATCH | `/incidencias/{id}/asignar` | 204 No Content |
| 5 | Assign technician (second) | PATCH | `/incidencias/{id}/asignar` | 204 No Content |
| 6 | Solve first issue | PATCH | `/incidencias/{id}/resolver` | 204 No Content |
| 7 | Get pending incidents | GET | `/incidencias?estado=PENDIENTE` | 200 OK |
| 8 | Get assigned incidents | GET | `/incidencias?estado=ASIGNADA` | 200 OK |
| 9 | Get solved incidents | GET | `/incidencias?estado=RESUELTA` | 200 OK |
| 10 | Get all incidents | GET | `/incidencias` | 200 OK |

## Environment variables

The environment file defines:

- `host`: server hostname (`localhost`).
- `port`: server port (`8080`).
- `firstIssueId`, `secondIssueId`, `thirdIssueId`: populated automatically by the registration tests for use in later requests.