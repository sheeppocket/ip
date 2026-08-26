# UI Test Plan

## Test configuration

- Application: `Yapper`
- Java version: 25
- Source directory: `src/main/java`
- Generated class directory: `build/ui-test-classes`
- Launch command: `java -cp build/ui-test-classes Yapper`
- Response timeout: 5 seconds per command
- Comparison: Exact text after normalizing line endings to `LF`; whitespace, blank lines, punctuation, and separators remain significant.
- Isolation: Start a fresh application process for each test case.

## Test cases

### UI-1: Invalid task and index inputs preserve state

**Aim:** Verify empty and unknown task inputs are rejected, invalid mark/unmark inputs do not mutate tasks, and valid operations still work afterward.

#### Command 1

**Input**

```text
todo borrow book
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [T][ ] borrow book
 Your legendary list now contains 1 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 2

**Input**

```text
todo
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: The todo description cannot be empty. Try: todo <task description>
____________________________________________________________
```

#### Command 3

**Input**

```text
buy groceries
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Please precede a task with a command to add it. Otherwise, type 'help' for a list of commands.
____________________________________________________________
```

#### Command 4

**Input**

```text
list
```

**Expected output**

```text
 ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:
 1.[T][ ] borrow book
____________________________________________________________
```

#### Command 5

**Input**

```text
mark
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Please specify a task number. Try: mark <task number>
____________________________________________________________
```

#### Command 6

**Input**

```text
mark book
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: 'book' is not a valid task number. Try: mark <task number>
____________________________________________________________
```

#### Command 7

**Input**

```text
mark 2
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Task number 2 is not in the list. Type 'list' to see the available task numbers.
____________________________________________________________
```

#### Command 8

**Input**

```text
mark 1
```

**Expected output**

```text
 CONFETTI CANNONS! This task is now officially, unmistakably, spectacularly DONE:
  [T][X] borrow book
____________________________________________________________
```

#### Command 9

**Input**

```text
unmark
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Please specify a task number. Try: unmark <task number>
____________________________________________________________
```

#### Command 10

**Input**

```text
list
```

**Expected output**

```text
 ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:
 1.[T][X] borrow book
____________________________________________________________
```

#### Command 11

**Input**

```text
unmark 1
```

**Expected output**

```text
 PLOT TWIST! This task has returned to the thrilling realm of NOT DONE YET:
  [T][ ] borrow book
____________________________________________________________
```

#### Command 12

**Input**

```text
bye
```

**Expected output**

```text
 FAREWELL, magnificent human! Yapper shall now stop talking--an historic occasion!
 Return soon, because your tasks and I will have an absolutely enormous amount to discuss.
____________________________________________________________
```

### UI-2: Malformed dated tasks preserve state

**Aim:** Verify malformed deadlines and events are rejected without being added, while valid dated tasks and arbitrary date strings remain usable.

#### Command 1

**Input**

```text
deadline
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: The deadline description cannot be empty. Try: deadline <task description>
____________________________________________________________
```

#### Command 2

**Input**

```text
deadline submit report
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: A deadline needs a description and a due date. Try: deadline <task description> /by <date or time>
____________________________________________________________
```

#### Command 3

**Input**

```text
deadline submit report /by no idea :-p
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [D][ ] submit report (by: no idea :-p)
 Your legendary list now contains 1 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 4

**Input**

```text
event meeting /from Monday
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: An event needs a description, start, and end. Try: event <task description> /from <start> /to <end>
____________________________________________________________
```

#### Command 5

**Input**

```text
list
```

**Expected output**

```text
 ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:
 1.[D][ ] submit report (by: no idea :-p)
____________________________________________________________
```

#### Command 6

**Input**

```text
event project meeting /from Mon 2pm /to 4pm
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Your legendary list now contains 2 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 7

**Input**

```text
help
```

**Expected output**

```text
 PREPARE YOURSELF! Here is Yapper's impressively comprehensive command repertoire:
  todo <description>
  deadline <description> /by <date or time>
  event <description> /from <start> /to <end>
  list
  mark <task number>
  unmark <task number>
  delete <task number>
  help
  bye
____________________________________________________________
```

#### Command 8

**Input**

```text
list
```

**Expected output**

```text
 ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:
 1.[D][ ] submit report (by: no idea :-p)
 2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
```

#### Command 9

**Input**

```text
bye
```

**Expected output**

```text
 FAREWELL, magnificent human! Yapper shall now stop talking--an historic occasion!
 Return soon, because your tasks and I will have an absolutely enormous amount to discuss.
____________________________________________________________
```

### UI-3: Delete tasks and reject invalid delete indices

**Aim:** Verify deletion removes the selected task, renumbers the remaining tasks, and missing, non-numeric, or out-of-range task numbers do not mutate the list.

#### Command 1

**Input**

```text
todo read book
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [T][ ] read book
 Your legendary list now contains 1 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 2

**Input**

```text
event project meeting /from Aug 6th 2pm /to 4pm
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Your legendary list now contains 2 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 3

**Input**

```text
todo borrow book
```

**Expected output**

```text
 OHOHO, MAGNIFICENT! I have triumphantly added this dazzling new task:
   [T][ ] borrow book
 Your legendary list now contains 3 task(s). Yes, I counted them personally!
____________________________________________________________
```

#### Command 4

**Input**

```text
delete
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Please specify a task number. Try: delete <task number>
____________________________________________________________
```

#### Command 5

**Input**

```text
delete meeting
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: 'meeting' is not a valid task number. Try: delete <task number>
____________________________________________________________
```

#### Command 6

**Input**

```text
delete 4
```

**Expected output**

```text
 WHOOPS-A-DAISY! Yapper has encountered a tiny dramatic complication: Task number 4 is not in the list. Type 'list' to see the available task numbers.
____________________________________________________________
```

#### Command 7

**Input**

```text
delete 2
```

**Expected output**

```text
 BEHOLD! With one decisive flourish, I have removed this task from existence:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Your freshly streamlined list now contains 2 task(s). Delightfully tidy!
____________________________________________________________
```

#### Command 8

**Input**

```text
list
```

**Expected output**

```text
 ATTENTION, EVERYONE! Yapper proudly presents your complete and glorious task list:
 1.[T][ ] read book
 2.[T][ ] borrow book
____________________________________________________________
```

#### Command 9

**Input**

```text
bye
```

**Expected output**

```text
 FAREWELL, magnificent human! Yapper shall now stop talking--an historic occasion!
 Return soon, because your tasks and I will have an absolutely enormous amount to discuss.
____________________________________________________________
```

## Session notes

- Startup output is captured in the transcript but is not attributed to the first command.
- A failing command ends the entire test run immediately. Later commands and test cases are not run.
- The final transcript shows startup output, console input, console output, and the pass/fail result for every command that ran.
