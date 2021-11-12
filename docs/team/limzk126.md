---
layout: page
title: Lim Zhe Kang's Project Portfolio Page
---

## Project: ContactSh

ContactSH is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still
having the benefits of a Graphical User Interface (GUI). If you can type fast, ContactSH can get your contact management
tasks done faster than traditional GUI apps.

## **Code contributed**:

The code I contributed can be found [here](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=limzk&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17)

### **Implemented functionality to view tasks of a specified person**
* **What it does**: Allow users to view task list of specified person using either a mouse-click, or a CLI command.
* **Justification**: One of the core functionalities of ContactSh.
* **Challenges**: I had to link and modify different functionalities such as editing, removing and editing
   of tasks so that the GUI reflects the correct information.

Relevant PRs include:
[#49](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/49),
[#67](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/67)

### **Implemented functionality to view all person's tasks**
* **What it does**: Allow users to view all person's tasks in a single list.
* **Justification**: Implemented using TreeView JavaFx control as it was the best choice, however implementation
   was hard as I had to design my own algorithm to ensure the TreeView structure is modified correctly whenever
   commands which modified tasks were made.
* **Challenges**: I had to link and modify different functionalities such as editing, removing and editing
   of tasks so that the GUI reflects the correct information.

Relevant PRs include: [#85](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/85)

### **Implemented functionality to find tasks within the view all person's tasks panel**
* **What it does**: Filter/narrow down the tasks they wish to see using keywords, when viewing all person's tasks.
* **Justification**: Important feature as it allows users the flexibility of viewing the tasks they want to see.

Relevant PRs include: [#104](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/104)

### **Implemented functionality to view overall tasks statistics**
* **What it does**:
   * View tasks completion statistics using a PieChart.
   * taskStatusChecker: A class which uses a thread to automatically check and update all task's status (once every 5 seconds), such
   that users would not view outdated tasks completion information.
* **Justification**: This feature aids the user experience as it is allows users to view
   useful up to date information regarding their tasks completion.
* **Challenges**: The PieChart was hard to implement by itself and even harder to make it dynamic.
   I.e., Updates instantly whenever a task's completion status is modified. I also had to link the taskStatusChecker to work
   with task viewing functionalities such that tasks updated automatically are reflected on the GUI correctly.

Relevant PRs include: [#95](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/95)

### **Implemented functionality to set when to remind users of tasks that are due soon**
* **What it does**: Sets when tasks are reminded to users that it is due soon. The input is the number of days
   prior to a task's happening/due date.
* **Justification**: Essential functionality as it allows the users to have the flexibility of when they are reminded that their tasks
   are due soon.

Relevant PRs include: [#110](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/110)

## **UI/UX contributions:**
* Designed and implemented the task list panel and statistics panel, both of which are core
   portions of the GUI.
* Implemented split sliding panels to allow greater resize flexibility for users.

## **Team tasks/contributions:**
* Managed Releases: `v1.2.1(trial release for v1.3)`, `v1.3`, `v1.3.1(Revision of v1.3)`.
* Created and Changed product icon, name for the GUI.
* Maintained issue tracker: Created and managed issues in most milestones. Wrapped up milestones `v1.3` and `v1.3.1`.
* Refactored  `EditCommand`, `DeleteTaskCommand`, `DoneCommand`, `UndoCommand`. [#209](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/209),
  [#220](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/220)
* Reviewed team members' PRs regularly to ensure it upholds coding standards.

## **Documentation**:

### **User Guide**:
* Added documentation for the features `cat`, `cat -A`. [#20](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/20),
  [#87](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/87)
* Added documentation for the feature `reminder`. [#111](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/111)
* Added Table of contents, Introduction, Guide on using the UG and product Icon. [#87](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/87/files),
  [#116](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/116/files)

### **Developer Guide**:
* Added user stories, product scope and target audience. [#23](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/23),
  [#195](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/195)
* Added Implementation for view tasks functionality. [#73](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/73)
* Added delete tasks instructions to the testing manual.

## **Testing:**
* Added more Junit tests for LogicManager and ModelManager. [#54](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/54)
* Added more Junit tests for DeleteTaskCommand and DeleteTaskCommandParser. [#56](https://github.com/AY2122S1-CS2103T-W10-1/tp/pull/56)
