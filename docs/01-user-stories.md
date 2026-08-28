# User stories

## 1. Register an issue

**As** a user, **I want** to register a new issue by providing a description and location **so** that it is recorded and can be processed.

- When I fill in the description and location fields and clicks ‘Create’, the issue appears in the list with the status ‘Pending’.
- If I click “Create” whilst the description or location field is empty, the system displays an error message on the form.

## 2. Assign a technician to an issue

**As** a user, **I want** to assign a technician (name and phone number) to a pending issue **so** that it is recorded as assigned and it is clear who is handling it.

- When I click “Assign” on a pending issue, a form opens with fields for name and phone number.
- When I enter the name and phone number (9 digits) and confirms, the issue changes to “Assigned” status and displays the technician’s name.
- If I enter a phone number that is not 9 digits long, the system displays an error on the form.

## 3. Solve an issue

**As** a user, **I want** to mark an assigned issue as solved **so** that its status changes to “solved” and it is recorded that the issue has been solved.

- When I click “solve” on an assigned issue, the issue changes to “solved” status.

## 4. Viewing issues

**As** a user, **I want** to view the list of issues, being able to filter by status **so that** I can see the current status of each one.

- When I open the app, the full list of issues is displayed.
- When I select a status from the tabs (‘Pending’, ‘Assigned’, ‘solved’), the list is filtered to show only those with that status.
- If there are no issues with the selected status, an empty list is displayed.