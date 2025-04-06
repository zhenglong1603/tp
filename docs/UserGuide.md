---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# Klinix User Guide

Klinix is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, you can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. **Install Java:**
   Ensure you have Java `17` or above installed on your computer.
   - **Windows & Linux Users:** Download and install Java from [Oracle JDK 17 Archive Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
   - **Mac Users:** Install the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. **Download the Application:**
   Get the latest `.jar` file from [this release page](https://github.com/AY2425S2-CS2103T-T09-2/tp/releases).

3. **Prepare Your Folder:**
   Create an empty folder that will serve as your _home folder_ for Klinix, and copy the downloaded `.jar` file into that folder.

4. **Run the Application:**
   - Open a command terminal.
   - Navigate to the folder where you copied the `.jar` file:
     - **Windows:** Open Command Prompt, type `cd ` followed by the folder path, then press Enter.
     - **macOS/Linux:** Open Terminal, type `cd ` followed by the folder path, then press Enter.
   - Run the application by entering the following command:
     ```
     java -jar Klinix.jar
     ```
     After a few seconds, a GUI will appear. Note that when you first start the app, there will be sample data. You may wish to clear the sample data using the `clear` command.

5. **Using the Application:**
   - Type a command in the command box and press Enter to execute it. For example, typing **`help`** and pressing Enter will open the help window.
   - Here are some example commands you can try:
     - `list` : Lists all contacts.
     - `add <name> <details>` : Adds a new contact to Klinix.
     - `delete 3` : Deletes the 3rd contact shown in the current list.
     - `clear` : Deletes all contacts.
     - `exit` : Exits the app.

6. **More Information:**
   Refer to the [Features](#features) section below for detailed descriptions of each command.

--------------------------------------------------------------------------------------------------------------------
# Input parameters

<box type="info" seamless>

**Notes about parameters:**<br>

Parameters will be in the form of `p/[PARAMETER]` where p is the parameter symbol. For example, the command `add n/John`<br>

- `n/` -> parameter symbol<br>
- `John` -> parameter
  </box>

---

### **Patient Parameters**

| Symbol  | Parameter       | Constraints                                                                                                                                                               |
|---------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`n`** | `NAME`          | - Must start with a letter, allowing spaces, apostrophes, periods, and hyphens between words; optional suffix ('s/o', 'd/o', 'c/o', '@') followed by another valid name. |
| **`p`** | `PHONE_NUMBER`  | - Must be a valid phone number (at least 3 digits. e.g. 999)                                                                                                              |
| **`e`** | `EMAIL`         | - Must follow standard email format (e.g. `user@domain.com`).                                                                                                             |
| **`ic`**| `NRIC`          | - **9-character alphanumeric**: Starts with an uppercase letter (A-Z), followed by 7 digits (0-9), ends with an uppercase letter (A-Z). Example: `S1234567A`.             |
| **`b`** | `BIRTHDATE`     | - Format: `dd-MM-yyyy` (e.g. `01-01-1990`).                                                                                                                               |
| **`a`** | `ADDRESS`       | - No specific constraints.                                                                                                                                                |
| **`t`** | `TAG`           | - Optional. Can be repeated (e.g. `t/friend t/colleague`).                                                                                                                |

---

### **Medical Report Parameters**

| Symbol    | Parameter         | Constraints                                                                                                     |
|-----------|-------------------|-----------------------------------------------------------------------------------------------------------------|
| **`ic`**  | `PATIENT_NRIC`    | - Same as Patient NRIC (9-character alphanumeric).                                                              |
| **`al`**  | `ALLERGIES`       | - Must contain at least one letter and does not include special symbols other than spaces, commas, and hyphens. |
| **`ill`** | `ILLNESSES`       | - Must contain at least one letter and does not include special symbols other than spaces, commas, and hyphens.     |
| **`sur`** | `SURGERIES`       | - Must contain at least one letter and does not include special symbols other than spaces, commas, and hyphens.     |
| **`imm`** | `IMMUNIZATIONS`   | - Must contain at least one letter and does not include special symbols other than spaces, commas, and hyphens.     |

---

### **Medicine Usage Parameters**

| Symbol     | Parameter          | Constraints                                                                                   |
|------------|--------------------|-----------------------------------------------------------------------------------------------|
| **`ic`**   | `PATIENT_NRIC`     | - Same as Patient NRIC.                                                                       |
| **`n`**    | `MEDICINE_NAME`    | - Must contain **at least 1 alphabetic character**.                                           |
| **`dos`**  | `DOSAGE`           | - Free-text (e.g. `Two 500mg tablets, 4 times daily`).                                       |
| **`from`** | `START_DATE`       | - Format: `dd-MM-yyyy` (e.g. `23-02-2025`).                                                  |
| **`to`**   | `END_DATE`         | - Format: `dd-MM-yyyy` (e.g. `25-02-2025`).<br>- Must be **after or equal to `START_DATE`**. |

---

### **Appointment Parameters**

| Symbol     | Parameter          | Constraints                                                                                                                                                 |
|------------|--------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`ic`**   | `PATIENT_NRIC`     | - Same as Patient NRIC.                                                                                                                                     |
| **`appt`** | `DESCRIPTION`      | - **1–40 alphabetic characters** (e.g. `Dental Check-up`).                                                                                                 |
| **`from`** | `START_DATE`       | - Format: `dd-MM-yyyy HH:mm` (e.g. `22-02-2025 10:00`).                                                                                                    |
| **`to`**   | `END_DATE`         | - Format: `dd-MM-yyyy HH:mm` (e.g. `22-02-2025 10:15`).<br>- Must be **after `START_DATE`**.                                                               |

---

### **General Parameters**

| Symbol     | Parameter       | Constraints                                                                         |
|------------|-----------------|-------------------------------------------------------------------------------------|
| -          | `INDEX`         | - **Positive integer** (1, 2, 3, ...).<br>- Must exist in the current displayed list. |
| -          | `KEYWORD`       | - Non-empty string.                                         |
| **`date`** | `DATE`          | - Format: `dd-MM-yyyy` (e.g. `22-03-2025`).             |

---

### **Notes**:
1. **NRIC Consistency**: All NRIC fields (`ic`) follow the same 9-character alphanumeric rule.
2. **Date Formats**:
    - `dd-MM-yyyy` for birthdates/medicine dates.
    - `dd-MM-yyyy HH:mm` for appointments.
3. **Error Handling**: Refer to the use cases for validation messages (e.g. "Invalid NRIC format").

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing past commands:

You can scroll through your previously entered commands using the **Up** and **Down** arrow keys.

- **Up Arrow**: Scrolls to the previous command in the history.
- **Down Arrow**: Scrolls to the next command in the history.

This feature allows you to quickly reuse or edit previously entered commands without retyping them.

#### Example Usage:

1. Enter a command, such as `list`, and press Enter.
2. Press the **Up Arrow** key to bring up the `list` command in the command box.
3. Press the **Down Arrow** key to navigate back to a more recent command.

#### Notes:

- The command history is saved only for the current session and will reset when the application is restarted.

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a patient: `add`

Adds a patient to Klinix.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL ic/NRIC b/BIRTHDATE a/ADDRESS [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A patient can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com ic/S0123456A b/10-10-2000 a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com ic/S9876543B b/01-01-1990 a/Newgate Prison p/1234567 t/criminal`

### Listing all patients : `list`

Shows a list of all patients in Klinix.

Format: `list`

### Editing a patient : `edit`

Edits an existing patient in Klinix.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [ic/NRIC] [b/BIRTHDATE] [a/ADDRESS] [t/TAG]..​`

parameters:
* `INDEX`: The index of the patient in the displayed list. It must be a positive integer.
* `NAME`: (Optional) The name of the patient. It should not be blank.
* `PHONE`: (Optional) The phone number of the patient. It should be a valid phone number.
* `EMAIL`: (Optional) The email of the patient. It should be a valid email address.
* `NRIC`: (Optional) The NRIC of the patient. It should be a valid NRIC number.
* `BIRTHDATE`: (Optional) The birthdate of the patient. It should be a valid date in the format `dd-MM-yyyy`.
* `ADDRESS`: (Optional) The address of the patient. It should not be blank.
* `TAG`: (Optional) The tag of the patient.

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the patient will be removed i.e adding of tags is not cumulative.
* You can remove all the patient’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st patient to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd patient to be `Betsy Crower` and clears all existing tags.

### Locating patients by name: `find`

Finds patients whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Patients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a Patient : `delete`

Deletes the specified patient from Klinix.

Format 1: `delete INDEX`

* Deletes the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed patient list.
* The index **must be a positive integer** 1, 2, 3, …​

Format 2: `delete [ic/NRIC]`

* Deletes the person with the specified `NRIC`.
* The `NRIC` must be valid.
* The command works regardless of whether if the person with the specified NRIC is displayed or not.

**Note:** You can use whichever format you find convenience, but not both at the same time. Otherwise, 
Klinix will give an error.

Examples:

* `list` followed by `delete 2` deletes the 2nd person in Klinix.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
* `delete ic/S1234567A` deletes the person with NRIC S1234567A from Klinix (if exists).
* `list` followed by `delete 2` deletes the 2nd patient in Klinix.
* `find Betsy` followed by `delete 1` deletes the 1st patient in the results of the `find` command.


### Clearing all entries : `clear`

Clears all entries from the clinic.

Format: `clear`

### Adding medical report: `addmr`

Add a new medical report to a patient's record.

Format: `addmr ic/NRIC [al/ALLERGIES] [ill/ILLNESSES] [sur/SURGERIES] [imm/IMMUNIZATIONS]`

parameters:
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.
- `ALLERGIES`: (Optional) The allergies of the patient. It can be `None` or a string of allergies separated by commas.
- `ILLNESSES`: (Optional) The illnesses of the patient. It can be `None` or a string of illnesses separated by commas.
- `SURGERIES`: (Optional) The surgeries of the patient. It can be `None` or a string of surgeries separated by commas.
- `IMMUNIZATIONS`: (Optional) The immunizations of the patient.
It can be `None` or a string of immunizations separated by commas.

Examples:
* `addmr ic/S1234567A al/Penicillin ill/Flu sur/Appendectomy imm/Flu Vaccine`
* `addmr ic/T0260144G al/None ill/None sur/None imm/None`
* `addmr ic/S1234567A al/Peanuts, Penicillin ill/None sur/Appendectomy imm/Flu Vaccine, Hepatitis B`

**Warning:**

1. The patient with the given NRIC must exist, otherwise Klinix will show an error message.
2. If the patient already has a medical report, adding a new one will overwrite the existing one.

### Deleting medical report: `deletemr`

Delete a patient's existing medical report.

Format 1: `deletemr ic/NRIC`

Parameters:
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.

Examples:
* `deletemr ic/S1234567A`
* `deletemr ic/T0260144G`

Format 2: `deletemr INDEX`

* Deletes the medical report of the person at the specified `INDEX`
* The index refers to the index number shown in the displayed person list.
* This index **must be a positive integer** 1, 2, 3, ...

Examples:
* `deletemr 1`

**Warning**

1. The patient with the given NRIC, or at the specified index must exist, otherwise Klinix will show an error message.
2. If the patient does not have an existing medical report, nothing will happen.

### Adding medicine usage records: `addmu`

Add a new medicine usage record as part of a patient’s medical history or medical needs.

Format: `addmu ic/NRIC n/MEDICINE_NAME dos/DOSAGE from/START to/END`

Parameters:
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.
- `MEDICINE_NAME`: The name of the medicine. should only contain alphanumeric characters and spaces, and it should not be blank.
- `DOSAGE`: The dosage of the medicine. It should not be blank.
- `START`: The start date of the medicine usage. It must be in the format `dd-MM-yyyy`.
- `END`: The end date of the medicine usage. It must be in the format `dd-MM-yyyy`.

Examples:
* `addmu ic/T0260144G n/Paracetamol dos/Two 500mg tablets, 4 times in 24 hours from/23-02-2025 to/25-02-2025`
* `addmu ic/S1234567A n/Panadol Extra dos/Two 250mg tablets, once per day from/01-03-2025 to/05-03-2025`

**Warning:**

1. `START` and `END`must be in the format of `dd-MM-yyyy`
2. The patient with the given NRIC must exist, otherwise Klinix will show an error message.
3. Two medicine usage have the same medicine name if they are equal **ignoring case**.
4. Two medicine usage records from the same patient are overlapped if they have the same name and overlapping duration.
Klinix will detect such overlapping instances and give an error message when you try to add them.
5. `START` should be before (or on the same day as) `END`
6. `START` should not be before the patient's birthday.

### Clearing all medicine usage records: `clearmu`

Clear all medicine usage records of a patient's medical history.

Format 1: `clearmu ic/NRIC`

* Deletes all medicine usages from the person with the specified `NRIC`.
* The `NRIC` must be valid.

Examples: `clearmu ic/S1234567A`

Format 2: `clearmu INDEX`

* Deletes all medicine usages from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, ...

Examples: `clearmu 3`

**Note:** You can use whichever format you find convenient, but not both at the same time.
Otherwise, Klinix will give an error.

**Warning:**

1. The patient with the given NRIC must exist, otherwise Klinix will show an error message.

### Deleting medicine usage records: `deletemu`

Delete a particular medicine usage record of a patient's medical history.

Format: `deletemu INDEX ic/NRIC`

Parameters:
- `INDEX`: The index of the medicine usage record in the displayed medicine usage list. It **must be a positive integer**.
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.

**Note:** The medicine usage record list could be viewed by navigating to the person with the specified NRIC, 
and clicking on their displayed card to view patient details

Examples:
* `deletemu 2 ic/S1234567A`

### Finding medicine usages by medicine names: `findmu`

Finds patient whose medicine usages contain the specified medicine name(s).

Format: `findmu KEYWORDS [MORE_KEYWORDS]`

Examples: `findmu Paracetamol Amoxicillin`

### Add appointment: `addappt`

Add a new appointment to the patient.

Format: `addappt ic/NRIC appt/DESCRIPTION from/START to/END`

Example:
* `addappt ic/S1234567A appt/Check-up from/22-02-2025 10:00 to/23-02-2025 10:15`

**Warning:**

1. `START` and `END`must be in the format of `dd-MM-yyyy HH:mm`
2. The patient with the given NRIC must exist, otherwise Klinix will show an error message.
2. Two appointments from the same patient are overlapped if they have the same name and overlapping duration.
   Klinix will detect such overlapping instances and give an error message when you try to add them.

### Delete Appointment: `deleteappt`

Delete the specified appointment from the patient.

Format: `deleteappt INDEX ic/NRIC`

Parameters:
- `INDEX`: The index of the appointment in the displayed list. It must be a positive integer.
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.

Example:
* `deleteappt 2 ic/S1234567A`

**Warning:**

1. The patient with the given NRIC must exist, otherwise Klinix will show an error message.
2. The index given must also be valid, a positive integer and within the appointment list size.

### Clear all Appointment records: `clearappt`

Clear all appointments of a patient.

Format 1: `clearappt ic/NRIC`

Parameters:
- `NRIC`: The NRIC of the patient. It must be a valid NRIC number.

Examples: `clearappt ic/S1234567A`

Format 2: `clearappt INDEX`

Parameters:
- `INDEX`: The index of the patient shown in the displayed person list. It **must be a positive integer** 1, 2, 3, ...

Examples: `clearappt 1` clears all appointments of the first patient currently displayed.

**Warning:**

1. The patient with the given NRIC, or the given index must exist, otherwise Klinix will show an error message.

### View Appointments on specific date: `appton`

Display all appointments on a specific date.

Format: `appton date/DATE`

Parameters:
- `DATE`: The date to view appointments on. It must be in the format `dd-MM-yyyy`.

Examples: `appton date/22-03-2025`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Klinix data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Klinix data are saved automatically as a JSON file `[JAR file location]/data/Klinix.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="info" seamless>

**Caution:**
If your changes to the data file makes its format invalid, Klinix will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the Klinix to behave in unexpected ways (e.g. if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install Klinix in the other computer and overwrite its data file with the old data file from your previous Klinix home folder.
**Q**: How to edit an appointment since there is no command for it<br>
**A**: We suggest deleting the existing appointment with `deleteappt` command first before using `addappt` command to create a new appointment with the edited details.

--------------------------------------------------------------------------------------------------------------------

## Glossary

1. Command Line Interface (CLI): A user interface where individuals interact with a computer or software by typing text-based commands. Instead of using icons or buttons, users enter specific instructions in a terminal or command prompt to execute tasks.

2. Graphical User Interface (GUI): A user interface that enables users to interact with electronic devices through visual elements like icons, buttons, and windows, rather than through text-based commands. GUIs simplify navigation and usage of software applications through a visual approach.

3. Hard Disk Drive (HDD): A storage device within a computer that utilizes rotating magnetic disks to read and write data. It is primarily used for long-term storage of files, applications, and the operating system.
--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Future Improvements

1. **Issue**: Deleting a medical report when the patient does not have an existing medical report does not reflect the
correct error message. Instead, it shows the success message.
   **Improvement**: Ensure that the correct error message is displayed when attempting to delete a medical report 
that does not exist.



--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER ic/NRIC e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Add Appointment** | `addappt ic/NRIC appt/DESCRIPTION from/START to/END` <br> e.g. `addappt ic/T0260144G appt/Check-Up from/22-02-2025 10:00 to/23-02-2025 10:15`
**Add Medical Report** | `addmr ic/NRIC [al/ALLERGIES] [ill/ILLNESSES] [sur/SURGERIES] [imm/IMMUNIZATIONS]` <br> e.g. `addmr ic/S1234567A al/Penicillin ill/Flu sur/Appendectomy imm/Flu Vaccine`
**Add Medicine Usage** | `addmu ic/NRIC n/MEDICINE_NAME dos/DOSAGE from/START to/END` <br> e.g. `addmu ic/T0260144G n/Paracetamol dos/Two 500mg tablets, 4 times in 24 hours from/23-02-2025 to/25-02-2025`
**Clear**  | `clear`
**Clear Appointments** | Format 1: `clearappt ic/NRIC` <br> e.g. `clearappt ic/S1234567A` <br> Format 2: `clearappt INDEX` <br> e.g. `clearappt 2`
**Clear Medicine Usage** | Format 1: `clearmu ic/NRIC` <br> e.g. `clearmu ic/S1234567A` <br> Format 2: `clearmu INDEX` <br> e.g. `clearmu 1`
**Delete** | Format 1: `delete INDEX`<br> e.g. `delete 3` <br> Format 2: `delete ic/NRIC` <br> e.g. `delete ic/S1234567A`
**Delete Appointment** | `deleteappt INDEX ic/NRIC` <br> e.g. `deleteappt 3 ic/S1234567A`
**Delete Medicine Usage** | `deletemu INDEX ic/NRIC` <br> e.g. `deletemu 1 ic/S1234568B`
**Delete Medical Report** | Format 1: `deletemr ic/NRIC` <br> e.g. `deletemr ic/S1234567B` <br> Format 2: `deletemr INDEX` <br> e.g. `deletemr 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [ic/NRIC] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.`edit 2 n/James Lee e/jameslee@example.com`
**Exit**   | `exit`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g. `find James Jake`
**Find Medicine Usage** | `findmu KEYWORD [MORE_KEYWORDS]`<br> e.g. `findmu Paracetamol Amoxicillin`
**Mark Appointment** | `markappt INDEX ic/NRIC`<br> e.g. `markappt 2 ic/S1234567A`
**Unmark Appointment** | `unmarkappt INDEX ic/NRIC`<br> e.g. `unmarkappt 2 ic/S1234567A`
**Help**   | `help`
**List**   | `list`
**Unmark Appointment** | `unmarkappt INDEX ic/NRIC`<br> e.g. `unmarkappt 2 ic/S1234567A`
**View Appointment on specific date** | `appton date/DATE` <br> e.g. `appton date/22-03-2025`
