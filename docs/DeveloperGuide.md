---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# Klinix Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AdressBook-Level 3 project created by the [SE-EDU initiative](https://se-education.org/)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280"></puml>

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T09-2/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `AppointmentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` and `Appointment` objects residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T09-2/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to a `KlinixParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `KlinixParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `KlinixParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T09-2/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T09-2/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `KlinixStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Person class
The person class represents patients of the clinic.

<puml src="diagrams/PersonClassDiagram.puml" width="550" />

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add Appointment Feature

#### Overview
The `addappt` command allows a user to add an appointment tied to a patient with the specified NRIC. The command requires:
- **NRIC** – Patient's NRIC in the address book.
- **Description** – Description of the appointment.
- **Start Date** – Beginning date and time of the appointment.
- **End Date** – Ending date and time of the appointment.

<puml src="diagrams/AddAppointmentSequenceDiagram.puml" alt="AddAppointmentSequenceDiagram" />

#### 1. Parsing User Input
The **`AddAppointmentCommandParser`** class is responsible for parsing user input. It uses `ArgumentTokenizer` to tokenize the input string, extracting:
- **NRIC** – Identifies the patient in the address book.
- **Description** – Additional details about the appointment.
- **Start Date** – Beginning of the appointment.
- **End Date** – End of the appointment.

During this parsing process:
- An `Appointment` instance is created to hold the appointment details.

#### 2. Executing the Command
The **`AddAppointmentCommand`** class performs the following steps to add an appointment:

1. **Retrieve Patient Information**:
   Uses the NRIC from the parser to locate the patient.

2. **Create New Person Instance with Appointment added to Appointment List**:
   - Utilises patient information from the current patient (identified by the NRIC) and the new `Appointment` details.
   - Creates an new `Person` instance with patient information and `Appointment` instance.

3. **Replace Existing Patient Record**:
   The new `Person` instance, containing the `Appointment`, replaces the existing patient record in the `Model`.

#### 3. Handling Invalid Inputs
The **`AddAppointmentCommandParser`** and **`AddAppointmentCommand`** classes enforce validation rules to ensure correct formats and scheduling logic:

- **Format Verification**:
   - **`AddAppointmentCommandParser`** checks if the date and time format follows `dd-MM-yyyy HH:mm`.
   - It checks if the date and time are valid.
   - It also ensures the **Start Date** is before or equal to the **End Date**.
   - **`AddAppointmentCommandParser`** also checks if the NRIC of patient follows its format.
   - **`AddAppointmentCommandParser`** also checks if the description of the appointment follows its format.
     <br><br>

- **Conflict Checking**:
   - **`AddAppointmentCommand`** checks if the new appointment to be added overlaps with any existing appointments for the patient or other patients.
   - If there is an overlap for any of these scenarios, an error message is thrown, preventing the appointment from being created.
   - If no overlap exists, the new appointment is added to the appointment list of the patient.

### View appointment command

#### Implementation

The `ViewAppointmentCommand` is implemented as follows:

##### Steps:
1. **Initialization**
    - The user sends a command `"appton date/10-10-2020"` to the `LogicManager`.

2. **Command Parsing**
    - `LogicManager` forwards the command to `KlinixParser` to parse it.
    - `KlinixParser` processes the input and identifies it as a `ViewAppointmentByDateCommand`.

3. **Command Creation**
    - `KlinixParser` creates a new `ViewAppointmentByDateCommand` object, passing the parsed date (`10-10-2020`) as an argument.
    - The newly created command is returned to `LogicManager`.

4. **Command Execution**
    - `LogicManager` calls `execute()` on the `ViewAppointmentByDateCommand`.
    - The command interacts with the `Model` to update the displayed appointments for the given date (`10-10-2020`).

5. **Result Handling**
    - The `Model` confirms the update and returns a result to the command.
    - The command forwards this result back to `LogicManager`.

6. **Command Cleanup**
    - The `ViewAppointmentByDateCommand` is destroyed after execution.
    - `LogicManager` returns the final result to the user.

<puml src="diagrams/ViewAppointmentByDateSequenceDiagram.puml" alt="ViewAppointmentSequenceDiagram" />

--------------------------------------------------------------------------------------------------------------------

## **Planned Enhancements**

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

General Clinic Counter Receptionist

**Value proposition**:
* Helps to keep track of the patients’ records and add details
* Keep track of patients’ medicine needs (for supply management)
* Helps people who type fast
* Ensures accurate patient information handling
* Help set up and manage appointments with patients
* Powerful search and filtering

### User stories

Priorities: High (must have) - * * *, Medium (nice to have) - * *, Low (unlikely to have) - *

| Priority     | As a ...                            | I want to ...                                                     | So that ...                                                                   |
| ------------ | ----------------------------------- |-------------------------------------------------------------------| ----------------------------------------------------------------------------- |
| `***`    | Basic User                          | add medicine usage records                                        | the supply management system remains accurate.                                |
| `***`    | Basic User                          | delete medicine usage records                                     | the supply management system remains accurate.                                |
| `***`    | Basic User                          | view detailed patient medical report                              | I have the necessary context during patient check-in.                         |
| `***`    | Basic User                          | view patients’ medicine needs                                     | I can manage the clinic’s medicine supply efficiently.                        |
| `***`    | Basic User                          | view patient records                                              | I always work with the most current information.                              |
| `***`    | Basic User                          | view patient's appointments                                       | I always work with the most current information.                              |
| `***`    | Basic User                          | add medical report                                                | the clinic database remains current and accurate.                             |
| `***`    | Basic User                          | delete medical report                                             | the clinic database remains current and accurate.                             |
| `***`    | Basic User                          | quickly add new patient details                                   | the clinic database remains current and accurate.                             |
| `***`    | Basic User                          | quickly delete new patient details                                | the clinic database remains current and accurate.                             |
| `***`    | Basic User                          | add appointments                                                  | the clinic database remains current and accurate.                             |
| `***`    | Basic User                          | delete appointments                                               | the clinic database remains current and accurate.                             |
| `**` | Basic User                          | edit existing patient details                                     | all information stays up-to-date and error-free.                              |
| `**` | Advanced User                       | search for patient records using various filters                  | I can locate the right records quickly and efficiently.                       |
| `**` | Basic User                          | schedule new patient appointments                                 | clinic visits are well organized.                                             |
| `**` | Advanced/Forgetful User             | receive notifications for upcoming appointments                   | I can remind patients and manage time effectively.                            |
| `**` | Basic User                          | quickly update appointment details                                | any last-minute changes are accurately reflected in the schedule.             |
| `**` | Basic User                          | view real-time inventory levels of medicines                      | I know when to reorder supplies without delays.                               |
| `**` | Basic User                          | update medicine usage records                                     | the supply management system remains accurate.                                |
| `**` | Advanced User/Fast Typer            | utilize fast-typing shortcuts during data entry                   | I can enter information quickly and reduce wait times.                        |
| `**` | Beginner, Basic User, Advanced User | work with an intuitive user interface                             | I need less training and can work more efficiently.                           |
| `**` | Basic User, Beginner                | have error-prevention features built into the data entry process  | patient information is recorded correctly the first time.                     |
| `**` | Basic User                          | confirm any record changes before finalizing them                 | I avoid mistakes and ensure accuracy in patient data.                         |
| `**` | Basic User                          | search for patients by multiple parameters (e.g., name, ID, date) | I can quickly narrow down results to find the correct record.                 |
| `**` | Basic User                          | filter appointments by date and time                              | I can efficiently manage busy schedules.                                      |
| `**` | Basic User                          | receive alerts for potential double-booking                       | scheduling conflicts are minimized.                                           |
| `**` | Basic User                          | securely export patient data                                      | backups are maintained and information can be shared with authorized parties. |
| `**` | Basic User                          | import external patient data                                      | existing records are seamlessly integrated into Klinix.                       |
| `**` | Advanced User, Beginner             | receive suggestions for common search queries                     | I can work faster and reduce typing effort.                                   |
| `**` | Basic User, Beginner                | navigate the application easily                                   | I can quickly access the functionality I need most often.                     |
| `**` | Forgetful User                      | add notes during patient check-in                                 | any additional details are captured for future reference.                     |
| `**` | Forgetful User                      | mark patients as “visited” after their appointments               | follow-ups and further actions can be tracked efficiently.                    |
| `**` | Advanced User, Careless user        | flag incomplete or inconsistent records                           | I can follow up and ensure all necessary details are completed.               |
| `**` | Basic User                          | securely log out of the system                                    | patient data remains confidential and secure.                                 |
| `**` | Basic User                          | securely log in                                                   | patient data remains confidential and secure.                                 |
| `**` | Beginner                            | view the user guide easily                                        | I can learn more about the product as and when I need                         |
| `**` | Beginner                            | view sample data table                                            | I can see what the end result would look like                                 |
| `**` | Beginner                            | see common medicines when entering prescriptions                  | so I don’t need to type everything manually                                   |
| `*`     | Basic User                          | generate daily appointment and check-in reports                   | I can plan resources and follow up as needed.                                 |
| `*`     | Advanced User                       | set up automatic email/SMS reminders for patients                 | patients receive timely notifications about their appointments.               |
| `*`     | Advanced User                       | filter records based on insurance type or payment status          | I can assist with billing and insurance-related queries promptly.             |
| `*`     | Basic User, Beginner                | view a visual calendar of appointments                            | I can manage daily schedules more intuitively.                                |
| `*`     | Beginner                            | see pop-up help tips when hovering over icons or fields           | I understand what each element does without feeling overwhelmed               |
*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Klinix` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add Patient**

**MSS**
1. User requests to add a new patient.
2. System validates the provided NRIC, name, age, contact number, and address.
3. If all inputs are valid and the NRIC is unique, the system registers the patient.
4. System confirms successful patient registration.

**Extensions**
- 1a. Missing required parameters → System returns an appropriate error message.
- 1b. Invalid NRIC, name, age, or contact number → System returns an appropriate error message.

Use case ends.

---

**Use case: Delete Patient**

**MSS**
1. User requests to delete a patient by NRIC.
2. System validates the NRIC.
3. System checks if a matching patient exists.
4. If the patient exists, the system deletes the patient record.
5. System confirms successful deletion.

**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Invalid NRIC format → System returns "Error: NRIC must be valid."
- 2a. No matching patient found → System returns "Error: No patient found with the given NRIC."
- 2b. Unexpected duplicate records → System returns "Error: Duplicate patient records detected. Contact administrator."
- 3a. System error occurs → System returns "Error: Unable to delete patient due to system error."

Use case ends.

---

**Use case: Edit Patient**

**MSS**
1. User requests to edit a patient by NRIC.
2. System validates the NRIC.
3. System checks if a matching patient exists.
4. System updates the patient details (name, age, contact number, address).
5. System confirms successful update.
6. System displays the updated patient details.


**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Invalid NRIC format → System returns "Error: NRIC must be valid."
- 2a. No matching patient found → System returns "Error: No patient found with the given NRIC."
- 3a. Invalid input format → System returns "Error: Invalid input format. Please check the details."
---

**Use case: Add Medical Report**

**MSS**
1. User requests to add a medical report for a patient.
2. System validates the NRIC.
3. System checks if the patient exists.
4. System records the provided medical history (allergies, illnesses, surgeries, immunizations, medical usage).
5. System confirms successful addition.

**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Invalid NRIC format → System returns "Error: NRIC must be valid."
- 2a. No matching patient found → System returns "Error: No medical report found with the given NRIC."

Use case ends.

---

**Use case: Delete Medical Report**

**MSS**
1. User requests to delete a patient’s medical report.
2. System validates the NRIC.
3. System checks if a medical report exists for the patient.
4. If a medical report exists, the system deletes it.
5. System confirms successful deletion.

**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Invalid NRIC format → System returns "Error: NRIC must be valid."
- 2a. No medical report found → System returns "Error: No medical report found with the given NRIC."

Use case ends.

---

**Use case: Add Medicine Usage Record**

**MSS**
1. User requests to add a medicine usage record for a patient.
2. System validates the NRIC.
3. System checks if the patient exists.
4. System records the medicine name, dosage, start date, and end date.
5. System confirms successful addition.

**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Invalid NRIC format → System returns "Error: NRIC must be valid."
- 2a. No medical report found → System returns "Error: Medical report for Patient [NRIC] is missing."
- 3a. Duplicate entry detected → System returns "Error: Duplicate entry detected. No changes were made."
- 3b. Start date is after end date → System returns "Error: Date must be in dd-MM-yyyy format."

Use case ends.

---

**Use case: Delete Medicine Usage Record**

**MSS**
1. User requests to delete a medicine usage record by ID.
2. System validates the NRIC and medicine usage ID.
3. System checks if the medicine usage record exists.
4. If a record exists, the system deletes it.
5. System confirms successful deletion.

**Extensions**
- 1a. Missing NRIC → System returns "Error: Patient NRIC is missing."
- 1b. Missing medicine usage ID → System returns "Error: ID must contain only numbers."
- 2a. No record found → System returns "Error: No medicine usage record found for the given ID."

Use case ends.

---

**Use case: Add Appointment**

**MSS**
1. User requests to add an appointment for a patient.
2. System validates the NRIC.
3. System checks for existing appointments to prevent overlaps.
4. System records the appointment details.
5. System confirms successful addition.
   Use case ends.

**Extensions**
- 1a. Patient with NRIC not registered → System returns an invalid Patient error message, "Patient with NRIC <NRIC> not found"
   * 1a1 Use case resumes at step 1.
- 1b. Invalid NRIC format → System returns an invalid format error message.
   * 1b1 Use case resumes at step 1.
- 1c. Overlapping appointment detected → System returns an error message, listing all the appointments that overlap as well.
   * 1c1 Use case resumes at step 1.
- 1d. Invalid date format → System returns an invalid format error message.
  * 1d1 Use case resumes at step 1.

Use case ends.

---

**Use case: Delete Appointment**
**Preconditions**: Klinix is displaying a non-empty list of patients and the patient is displaying a non-empty list of appointments.

**MSS**
1. User requests to delete an appointment by Index.
2. System validates the NRIC and appointment Index.
3. System checks if the appointment exists.
4. If the appointment exists, the system deletes it.
5. System confirms successful deletion.
   Use case ends.

**Extensions**
- 1a. Patient with NRIC not registered → System returns an invalid Patient error message
   * 1a1 Use case resumes at step 1.
- 1b. Missing appointment Index → System returns an invalid format error message.
   * 1b1 Use case resumes at step 1.
---

**Use case: Clear Appointment**
**Preconditions**: Klinix is displaying a non-empty list of patients and the patient is displaying a non-empty list of appointments.

**MSS**
1. User requests to clear the list of appointments of a particular patient.
2. System validates the NRIC.
3. System checks if list of appointments exist.
4. If any appointment exists, the system deletes it.
5. System confirms successful deletion.
   Use case ends.

**Extensions**
- 1a. Patient with NRIC not registered → System returns an invalid Patient error message
   * 1a1 Use case resumes at step 1.
---

**Use Case: View Patient details**

**MSS**
1. User requests to view a patient’s details by clicking on the list of patients.
3. System retrieves the patient details.
4. System displays the details (name, age, contact, address).
5. System displays the medical report (allergies, illnesses, surgeries, immunizations).
6. System displays the medicine usage records (name, dosage, start date, end date).
7. System displays the appointments (description, time).

**Use case ends.**

---

**Use Case: View Appointments on Date**

**MSS**
1. User requests to view appointments for a date.
2. System validates the date.
3. System retrieves and displays all appointments on the date.

**Extensions**
- 1a. Missing DATE → System returns "Error: DATE is required."
- 2a. No appointments → System returns "Info: No appointments found for DATE <DATE>."

**Use case ends.**

---

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

Here are some glossary terms that might be helpful to clarify within your document:

1. **NRIC (National Registration Identity Card)** – A unique identification number assigned to citizens and residents.
2. **Medical Report** – A document containing a patient's medical history, including illnesses, treatments, and surgeries.
3. **Medical Usage Record** – A record of medications prescribed to a patient, including dosage and duration.
8. **Overlapping Appointment** – When a new appointment conflicts with an existing one in terms of time and date.
9. **Deletion Confirmation** – A message displayed when a record is successfully removed from the system.
10. **MSS (Main Success Scenario)** – The sequence of steps that lead to a successful execution of a use case.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a patient

1. Addding a patient with all valid fields

   1. Test case: `add n/John Doe p/98765432 e/johnd@example.com ic/S0123456Z b/10-10-2000 a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney`<br>
      Expected: Patient is added to the list. Details of the new patient shown in the status message.

2. Adding a patient with missing mandatory fields

   1. Test case: `add n/Lam Tan ic/S1234567Y b/01-01-1990`<br>
      Expected: Patient is not added. Error details shown in the status message.

3. Adding a patient with invalid NRIC format

   1. Test case: `add n/Pam Tan ic/1234567X b/01-01-1990`<br>
      Expected: Patient is not added. Error details shown in the status message.

4. Additional test cases to try: invalid date format, invalid email format, invalid phone number format, etc.<br>
   Expected: Patient is not added. Error details shown in the status message.

### Editing a patient

1. Editing an existing patient (the first patient in the list)

   1. Prerequisites: Ensure at least one patient is in the list. If not, add a patient using the `add` command. Eg:<br>
      `add n/John Doe p/98765432 e/johnd@example.com ic/S0123456A b/10-10-2000 a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney`

   1. Test case: `edit 1 p/91234567 e/johndoe@example.com`<br>
      Expected: Patient details are updated. Details of the edited patient shown in the status message.

   1. Test case (invalid email format): `edit 1 e/wpeodkwoedpk`<br>
      Expected: Patient details are not updated. Error details shown in the status message.

   1. Additional test cases to try: editing the NRIC, name, age, contact number, address, tags, etc

1. Editing an invalid patient index

   1. Test case: `edit 0 p/91234567`<br>
      Expected: Patient details are not updated. Error details shown in the status message.

1. Invalid edit command

   1. Test case: `edit 1 wrfwefwefwef`<br>
      Expected: Patient details are not updated. Error details shown in the status message.

### Deleting a patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`<br>
      Expected: First patient is deleted from the list. Details of the deleted patient shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Adding a medical report

**Command:** `addmr`<br>

1. Adding a medical report to a patient
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
    * **Test Case:** `addmr ic/S1234567A al/Peanut Allergy ill/Diabetes sur/Appendectomy imm/Flu Shot, Tetanus`
    * **Expected:** The patient with NRIC S1234567A in the list is updated with the following fields:
        * Drug Allergies: `Peanut Allergy`
        * Illnesses: `Diabetes`
        * Surgeries: `Appendectomy`
        * Immunizations: `Flu Shot, Tetanus`
        * Other fields remain unchanged
          <br><br>

2. Adding a medical report to a patient with an existing medical report
   * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * That patient has an existing medical report
    * **Test Case:** `addmr ic/S1234567A al/Peanut Allergy ill/Diabetes sur/Appendectomy imm/Flu Shot, Tetanus`
    * **Expected:** The patient with NRIC S1234567A in the list is updated with the following fields, overwriting the previous fields:
        * Drug Allergies: `Peanut Allergy`
        * Illnesses: `Diabetes`
        * Surgeries: `Appendectomy`
        * Immunizations: `Flu Shot, Tetanus`
        * Other fields remain unchanged
          <br><br>

3. Adding a medical report with missing parameters
   * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
    * **Test Case:** `addmr ic/S1234567A al/Peanut Allergy ill/Diabetes`
    * **Expected:** The patient with NRIC S1234567A in the list is updated with the following fields:
        * Drug Allergies: `Peanut Allergy`
        * Illnesses: `Diabetes`
        * Surgeries: `None`
        * Immunizations: `None`
        * Other fields remain unchanged
          <br><br>

### Deleting a medical report

**Command:** `deletemr`<br>

1. Deleting a medical report from a patient
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
    * **Test Case:** `deletemr ic/S1234567A`
    * **Expected:** The patient with NRIC S1234567A in the list is updated with the following fields:
        * Drug Allergies: `None`
        * Illnesses: `None`
        * Surgeries: `None`
        * Immunizations: `None`
        * Other fields remain unchanged
          <br><br>

### Making an appointment

**Command:** `addappt`<br>

1. Adding an appointment to a patient
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * The appointment the user adds must not overlap with any existing appointment of that patient
          <br><br>
    * **Test Case:** `addappt ic/S1234567A appt/Check-Up from/22-02-2025 11:00 to/22-02-2025 11:30`
    * **Expected:** The patient with NRIC S123457A in the list is updated with the following fields:
        * Appointment: `Check-Up FROM 22-02-2025 11:00 TO 22-02-2025 11:30`
        * Other fields remain unchanged
          <br><br>
2. Adding an appointment that overlaps with existing patient's appointments
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * The appointment the user adds must overlap with an existing appointment
          <br><br>
    * **Test Case:** `addappt ic/S1234567A appt/Injection from/22-02-2025 11:15 to/22-02-2025 11:45`
    * **Expected:** Klinix throws the error message `Patient has overlapping appointments with the following patients: <Patients' Details>`
      <br><br>

### Deleting an appointment

**Command:** `deleteappt`<br>

1. Deleting an appointment from a patient who has an appointment
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * That patient has existing appointment(s)
          <br><br>
    * **Test Case:** `deleteappt 1 ic/S1234567A`
    * **Expected:** The patient with  in the list is updated with the following fields:
        * Appointment: `No appointments found`
        * Other fields remain unchanged.
        * Klinix will return a confirmation message `From patient S1234567A, successfully deleted appointment:
          Check-Up FROM 22-02-2025 11:00 TO 22-02-2025 11:30`
          <br><br>
2. Deleting an appointment from a patient who has no appointment
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * That patient has no existing appointment
          <br><br>
    * **Test Case:** `deleteappt 1 ic/S1234567A`
    * **Expected:** Klinix throws the error message `The patient has no existing appointments`
      <br><br>

### Clearing appointments

**Command:** `clearappt`<br>

1. Clearing appointments from a patient who has existing appointment(s)
   * **Prerequisites:**
     * The patient with that NRIC must be present in the patient list
     * That patient has existing appointment(s)
     <br><br>
       * **Test Case:** `clearappt ic/S1234567A`
       * **Expected:** The patient with  in the list is updated with the following fields:
           * Appointment: `No appointments found`
           * Other fields remain unchanged.
           * Klinix will return a confirmation message `Appointments successfully deleted from <Patient's NRIC>`
             <br><br>
2. Clearing appointments from a patient who has no appointment
    * **Prerequisites:**
        * The patient with that NRIC must be present in the patient list
        * That patient has no existing appointment
          <br><br>
    * **Test Case:** `clearappt ic/S1234567A`
    * **Expected:** Klinix throws the error message `No appointments to clear`
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Efforts**
#### Difficulty Level
#### Challenges Faced
#### Effort Required
#### Achievements

