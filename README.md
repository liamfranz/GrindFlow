# GrindFlow – Project Report

> **GrindFlow** is an Android productivity and schedule-planning application designed to help users organise tasks, daily activities, schedules and academic responsibilities in one central platform.

---

## 1. Project Overview

GrindFlow is a mobile productivity application developed to provide users with a simple and organised way to manage their everyday activities.

The application allows users to:

- Register and log into their account.
- View an overview of their daily activities.
- Create, edit, complete and delete tasks.
- Organise tasks according to dates, times, categories and priorities.
- View upcoming schedule activities.
- Monitor their productivity progress.
- Customise application settings.
- Store task information locally using Room Database.
- Authenticate users using Firebase Authentication.

GrindFlow is developed using **Android Studio, Kotlin and Jetpack Compose**. Firebase Authentication is used for user registration and login, while **Room Database** provides local data storage for task management.

An **ASP.NET Core REST API** is being developed to provide communication between the Android application and an online database. This will support future synchronisation between the local and online data sources.

---

## 2. Project Purpose

The main purpose of GrindFlow is to provide a centralised productivity platform that allows users to manage their daily responsibilities from a mobile device.

The application was designed with students and individuals with busy schedules in mind. Instead of managing tasks, schedules and progress across multiple applications, GrindFlow brings these functions together into one application.

The project also demonstrates the practical implementation of several software development concepts, including:

- Mobile application development
- User authentication
- Local database management
- REST API integration
- Offline data storage
- User interface design
- Version control
- Continuous integration

---

## 3. Main Features

### 3.1 User Registration and Login

Users can create an account and securely log into GrindFlow using Firebase Authentication.

The login system provides:

- Email and password authentication
- Account registration
- Password reset functionality
- Firebase-based authentication
- Google sign-in interface for future implementation

### Login Screen

<img width="177" height="392" alt="GrindFlow Login Screen" src="https://github.com/user-attachments/assets/a9d53435-ba10-48e5-af03-212e05fc1ff9" />

---

### 3.2 User Registration

New users can create a GrindFlow account by entering their required registration information.

The registration interface is designed to remain simple and easy to understand.

### Registration Screen

<img width="185" height="391" alt="GrindFlow Registration Screen" src="https://github.com/user-attachments/assets/a35b0eec-7fae-457b-a351-443338eb0474" />

---

## 4. Home Dashboard

The GrindFlow home screen acts as the central dashboard of the application.

It provides an overview of the user's current activities and gives quick access to the main sections of the application.

The dashboard includes:

- Daily progress overview
- Task completion information
- Schedule preview
- Quick navigation to tasks
- Quick navigation to the schedule
- Access to progress tracking
- Access to application settings

### Home Screen

<img width="197" height="437" alt="GrindFlow Home Page" src="https://github.com/user-attachments/assets/96339adc-e827-42a3-b0d8-31d042c7242f" />

---

## 5. Schedule Management

The Schedule section allows users to view planned activities and organise events according to their date and time.

The schedule provides users with a structured representation of upcoming activities, making it easier to keep track of academic and personal responsibilities.

### Schedule Screen

<img width="205" height="439" alt="GrindFlow Schedule Screen" src="https://github.com/user-attachments/assets/db5dfdfb-4ee9-4999-9b26-82197c091bb3" />

---

## 6. Task Management

Task management is one of the core features of GrindFlow.

Users are able to create and manage tasks according to their requirements.

Each task can contain information such as:

- Task title
- Description
- Date
- Time
- Category
- Priority
- Completion status

Users can also edit, complete and delete existing tasks.

### Tasks Screen

<img width="206" height="437" alt="GrindFlow Tasks Screen" src="https://github.com/user-attachments/assets/3c7d722c-045b-4eaa-973f-bf5538bd246c" />

---

## 7. Add and Edit Tasks

The task management interface allows users to enter new task information and modify existing tasks.

This provides flexibility when task information changes, such as a change in deadline, time or priority.

### Add/Edit Task Screen

<img width="203" height="431" alt="GrindFlow Add and Edit Task Screen" src="https://github.com/user-attachments/assets/ae150425-ddcd-4abc-98c8-7cc25cc14a0b" />

---

## 8. Progress Tracking

The Progress section provides users with an overview of their productivity and task completion.

The purpose of this feature is to help users monitor how effectively they are completing their planned activities.

The progress functionality can be extended in the future to include additional productivity statistics and historical information.

### Progress Screen

<img width="206" height="437" alt="GrindFlow Progress Page" src="https://github.com/user-attachments/assets/42c995e4-673a-44f5-9211-d3a1b5b8fdad" />

---

## 9. Application Settings

The Settings section allows users to customise aspects of the GrindFlow application.

The settings interface provides functionality for managing user preferences and application behaviour.

Current settings functionality includes:

- Language selection
- Dark mode
- Logout functionality

### Settings Screen

<img width="198" height="438" alt="GrindFlow Settings" src="https://github.com/user-attachments/assets/eb32466a-6b25-4d50-bb38-ef7a021b5203" />

---

# 10. Design Considerations

The design of GrindFlow was based on several important software development considerations.

## 10.1 User Interface Design

Jetpack Compose was used to create the application's interface.

The design focuses on:

- Clear navigation
- Simple layouts
- Readable text
- Consistent spacing
- Accessible controls
- Easy-to-understand interactions

The application uses a bottom navigation structure to provide quick access to major sections.

---

## 10.2 Usability

Usability was considered throughout the development process.

Users should be able to perform common tasks with minimal navigation. For example, tasks can be accessed directly from the home screen, while frequently used sections are available through the bottom navigation bar.

---

## 10.3 Local Data Storage

Room Database was implemented to store task information locally on the Android device.

This allows task data to remain available even when the application does not have an active internet connection.

The local database currently contains information such as:

- Task ID
- Title
- Description
- Date
- Time
- Category
- Priority
- Completion status

---

## 10.4 Authentication and Security

Firebase Authentication is used to manage user registration and login.

Authentication helps ensure that application access is associated with authenticated users rather than relying entirely on locally stored credentials.

The planned REST API will communicate with the Android application using HTTP requests and JSON data.

---

## 10.5 Performance

The application was designed to keep interactions lightweight and responsive.

Room Database provides efficient local storage while Jetpack Compose provides a modern declarative approach to building Android interfaces.

---

## 10.6 Scalability

The project architecture allows additional functionality to be added without completely redesigning the application.

Future development can include:

- Online database synchronisation
- Push notifications
- Additional languages
- Gamification
- Advanced productivity statistics
- Cloud-based data storage

---

# 11. Technology Stack

| Technology | Purpose |
|---|---|
| **Kotlin** | Main programming language |
| **Android Studio** | Android development environment |
| **Jetpack Compose** | User interface development |
| **Firebase Authentication** | User registration and authentication |
| **Room Database** | Local task data storage |
| **ASP.NET Core** | REST API development |
| **Online Database** | Future cloud data storage |
| **Git** | Version control |
| **GitHub** | Source-code hosting and project management |
| **GitHub Actions** | Continuous integration and automated workflows |

---

# 12. Application Architecture

The planned GrindFlow architecture follows a structure where the Android application communicates with local and remote data sources.

```text
                     ┌───────────────────────┐
                     │      GrindFlow        │
                     │    Android App        │
                     └───────────┬───────────┘
                                 │
                         HTTPS / JSON
                                 │
                     ┌───────────▼───────────┐
                     │    ASP.NET Core       │
                     │      REST API         │
                     └───────────┬───────────┘
                                 │
                     ┌───────────▼───────────┐
                     │     Online Database   │
                     └───────────────────────┘


                     ┌───────────────────────┐
                     │     Room Database     │
                     │     Local Storage     │
                     └───────────▲───────────┘
                                 │
                         Offline Access
                                 │
                     ┌───────────┴───────────┐
                     │      GrindFlow        │
                     │     Android App       │
                     └───────────────────────┘

The architecture is intended to support both local and remote data management.

Room Database is currently used for local task storage, while the ASP.NET Core REST API and online database are part of the planned online architecture.
13. GitHub Utilisation

GitHub is used as the central repository for the GrindFlow project.

The repository provides version control and allows the project's source code and development history to be maintained in one location.

GitHub is used for:

    Source-code management

    Version control

    Tracking project changes

    Maintaining different versions of the application

    Project documentation

    Collaboration

    Storing the project README

    Managing the project's development workflow

Git provides a history of changes made to the application, making it possible to identify when functionality was added or modified.
14. GitHub Actions

GitHub Actions can be used to automate parts of the software development workflow.

For GrindFlow, GitHub Actions can be used as a continuous integration (CI) mechanism to automatically check the project when code is pushed to the repository.

A typical workflow can perform tasks such as:

Developer
    │
    ▼
Push Code to GitHub
    │
    ▼
GitHub Actions Triggered
    │
    ├── Check Project
    ├── Build Application
    └── Run Tests
    │
    ▼
Build Result

This approach helps identify build or testing problems earlier in the development process.

As the project develops, GitHub Actions can also be extended to include automated testing, application builds and other development checks.
15. Firebase Integration

Firebase Authentication is integrated into GrindFlow to manage user authentication.

The authentication functionality supports:

    User registration

    Email and password login

    Password reset

    Authentication state handling

Firebase provides the authentication service while the Android application manages the user interface and authentication flow.
16. Offline Storage and Synchronisation

A key design consideration for GrindFlow is the ability to access task information without depending completely on an internet connection.

Room Database is currently used to provide local storage.

The planned architecture is:

Android Application
        │
        ▼
   Room Database
        │
        │ Synchronisation
        ▼
   REST API
        │
        ▼
 Online Database

This approach is intended to allow information created while offline to be synchronised with the online database when connectivity becomes available.
17. Future Development

Several features can be added to further expand GrindFlow.
Planned improvements

    ASP.NET Core REST API implementation

    Online database integration

    Automatic data synchronisation

    Firebase Cloud Messaging push notifications

    Additional South African language support

    Gamification features

    Expanded progress statistics

    Improved schedule functionality

    Additional account and security features

18. Project Outcome

GrindFlow demonstrates the development of a functional Android productivity application using modern Android technologies.

The current application provides a working foundation for:

    User authentication

    Task management

    Local database storage

    Schedule planning

    Progress tracking

    Application settings

    Modern mobile user interface design

The project also establishes the foundation for integrating an online REST API and cloud-based database in future development.
19. Conclusion

GrindFlow was developed to provide users with a centralised productivity and scheduling solution.

The application combines task management, scheduling, progress monitoring and user settings into a single Android application.

The use of Kotlin, Jetpack Compose, Firebase Authentication and Room Database provides the foundation for the current application, while the planned ASP.NET Core REST API and online database will support the project's future online and synchronisation capabilities.

The project also demonstrates the practical use of GitHub for version control and GitHub Actions for automated development workflows, providing a foundation for a more structured software development lifecycle.
20. Project Technologies

Platform: Android
Language: Kotlin
UI Framework: Jetpack Compose
Authentication: Firebase Authentication
Local Database: Room Database
Backend: ASP.NET Core REST API
Version Control: Git / GitHub
CI: GitHub ActionsGrindFlow – Project Report
1. Project Overview
<img width="177" height="392" alt="GrindFlow - Login" src="https://github.com/user-attachments/assets/a9d53435-ba10-48e5-af03-212e05fc1ff9" /> <img width="185" height="391" alt="GrindFlow - Registration" src="https://github.com/user-attachments/assets/a35b0eec-7fae-457b-a351-443338eb0474" /> <img width="198" height="438" alt="GrindFlow - Settings" src="https://github.com/user-attachments/assets/eb32466a-6b25-4d50-bb38-ef7a021b5203" /> <img width="197" height="437" alt="GrindFlow - Home Page" src="https://github.com/user-attachments/assets/96339adc-e827-42a3-b0d8-31d042c7242f" /> <img width="205" height="439" alt="GrindFlow Screen" src="https://github.com/user-attachments/assets/db5dfdfb-4ee9-4999-9b26-82197c091bb3" /> <img width="206" height="437" alt="GrindFlow - Progress Page" src="https://github.com/user-attachments/assets/42c995e4-673a-44f5-9211-d3a1b5b8fdad" /> <img width="208" height="437" alt="GrindFlow Screen" src="https://github.com/user-attachments/assets/3c7d722c-045b-4eaa-973f-bf5538bd246c" /> <img width="203" height="431" alt="GrindFlow Screen" src="https://github.com/user-attachments/assets/ae150425-ddcd-4abc-98c8-7cc25cc14a0b" />

GrindFlow is an Android productivity and schedule-planning application designed to help users organise their daily activities, tasks, schedules, and academic responsibilities in one place.

The application provides users with a central platform where they can create and manage tasks, plan their schedules, monitor their progress, and customise application settings.

GrindFlow is developed using Android Studio, Kotlin, and Jetpack Compose. Firebase Authentication is used for user registration and login, while Room Database provides local storage for tasks. An ASP.NET Core REST API is being developed to allow communication between the Android application and an online database.

2. Purpose of the Application

The purpose of GrindFlow is to provide users with a central productivity platform for managing their daily responsibilities.

The application is designed particularly for users who need to manage multiple activities such as academic work, assignments, personal tasks, projects, and deadlines.

The main objectives of GrindFlow are to:

Organise daily schedules and activities.
Create, edit, and manage tasks.
Record task deadlines, times, categories, and priorities.
Track completed and outstanding tasks.
Monitor user productivity and progress.
Provide secure user authentication.
Support local storage and offline access.
Connect to an online backend through a REST API.
Synchronise locally stored information with an online database.
Provide notifications for important activities and deadlines.
Provide a simple and accessible user interface.
3. Main Features
3.1 User Registration and Login

GrindFlow provides registration and login functionality using Firebase Authentication.

New users can create an account, while existing users can log into the application using their credentials.

The login screen also contains a Continue with Google option as part of the planned Google Sign-In functionality.

Login Screen
<img width="177" height="392" alt="GrindFlow - Login Screen" src="https://github.com/user-attachments/assets/a9d53435-ba10-48e5-af03-212e05fc1ff9" />

Figure 1: GrindFlow Login Screen

Registration Screen
<img width="185" height="391" alt="GrindFlow - Registration Screen" src="https://github.com/user-attachments/assets/a35b0eec-7fae-457b-a351-443338eb0474" />

Figure 2: GrindFlow Registration Screen

3.2 Home Screen

The Home screen acts as the main dashboard of the application.

It provides users with an overview of their current progress and scheduled activities while also providing navigation to the other main sections of GrindFlow.

Home Screen
<img width="197" height="437" alt="GrindFlow - Home Page" src="https://github.com/user-attachments/assets/96339adc-e827-42a3-b0d8-31d042c7242f" />

Figure 3: GrindFlow Home Screen

The Home screen provides access to:

Today's progress
Today's schedule
Tasks
Schedule
Progress
Settings
3.3 Schedule

The Schedule section is designed to allow users to organise activities according to dates and times.

This gives users a structured view of upcoming activities and helps them plan how they will use their time.

Schedule Screen
<img width="205" height="439" alt="GrindFlow - Schedule Screen" src="https://github.com/user-attachments/assets/db5dfdfb-4ee9-4999-9b26-82197c091bb3" />

Figure 4: GrindFlow Schedule Screen

3.4 Task Management

The Tasks section allows users to create and manage individual tasks.

Each task can contain:

Task title
Description
Due date
Due time
Category
Priority
Completion status

Users can add new tasks, edit existing tasks, delete tasks, and mark tasks as completed.

Tasks Screen
<img width="208" height="437" alt="GrindFlow - Tasks Screen" src="https://github.com/user-attachments/assets/3c7d722c-045b-4eaa-973f-bf5538bd246c" />

Figure 5: GrindFlow Tasks Screen

Task information is stored locally using Room Database, allowing saved tasks to remain available after the application is closed and reopened.

3.5 Add and Edit Tasks

The task-management functionality provides a dedicated form for entering task information.

Users can provide the task title, description, date, time, category, and priority before saving the task.

This allows tasks to contain enough information for users to understand what needs to be completed and when it is due.

Add/Edit Task Screen
<img width="203" height="431" alt="GrindFlow - Add or Edit Task Screen" src="https://github.com/user-attachments/assets/ae150425-ddcd-4abc-98c8-7cc25cc14a0b" />

Figure 6: GrindFlow Add/Edit Task Screen

3.6 Progress Tracking

The Progress screen provides an overview of task completion and user productivity.

It is designed to help users understand how much of their planned work has been completed.

Progress Screen
<img width="206" height="437" alt="GrindFlow - Progress Page" src="https://github.com/user-attachments/assets/42c995e4-673a-44f5-9211-d3a1b5b8fdad" />

Figure 7: GrindFlow Progress Screen

3.7 Settings

The Settings section provides users with application preferences and configuration options.

Settings are intended to allow GrindFlow to be adjusted according to the user's preferences.

Settings Screen
<img width="198" height="438" alt="GrindFlow - Settings" src="https://github.com/user-attachments/assets/eb32466a-6b25-4d50-bb38-ef7a021b5203" />

Figure 8: GrindFlow Settings Screen

4. Design Considerations
4.1 User Interface Design

GrindFlow uses Jetpack Compose to create its user interface.

The interface was designed around a consistent visual structure across all screens. The application uses cards, buttons, text fields, navigation controls, and clear headings to organise information.

The main screens are separated into:

Login
Registration
Home
Schedule
Tasks
Progress
Settings

This separation allows users to access specific functionality without unnecessarily complicated navigation.

4.2 Usability

Usability was considered throughout the design of the application.

Common actions such as creating a task, completing a task, or opening the schedule should require only a small number of steps.

The navigation system provides direct access to the main functions of the application, while task information is grouped into clearly labelled fields.

4.3 Task Organisation

Tasks contain multiple data fields instead of only a task name.

For example, a task can have a title, description, due date, due time, category, priority, and completion status.

This provides more context and allows users to distinguish between different types of activities.

4.4 Local Data Storage

Room Database is used to store task information locally on the Android device.

The application uses a database entity called TaskEntity, together with a TaskDao and GrindFlowDatabase.

This provides a local data layer that supports the planned offline-first approach.

The intended structure is:

Tasks Screen
      ↓
Room Database
      ↓
Local Device Storage
4.5 Security

Security is an important consideration because GrindFlow stores user account information and task-related data.

Firebase Authentication is used to manage user authentication.

The planned REST API communication will use HTTPS to protect data transmitted between the Android application and backend services.

4.6 Performance

The application is designed to keep frequently used data available locally.

Room allows tasks to be retrieved from local storage without requiring a network connection.

This can reduce reliance on network availability for basic task-management functionality.

4.7 Scalability

The application is designed so that its functionality can be expanded in the future.

The separation between the Android frontend, local Room database, REST API, and online database allows different parts of the system to be developed independently.

Potential future additions include:

Expanded notification functionality
More productivity statistics
Gamification
Additional language support
More advanced scheduling
Additional user settings
5. Application Architecture

The planned GrindFlow architecture consists of an Android frontend, local storage, authentication services, a REST API, and an online database.

                    ┌───────────────────────┐
                    │     GrindFlow App     │
                    │   Android / Kotlin    │
                    │     Jetpack Compose   │
                    └───────────┬───────────┘
                                │
                 ┌──────────────┼──────────────┐
                 │              │              │
                 ↓              ↓              ↓
        ┌────────────────┐  ┌────────────┐  ┌────────────────┐
        │ Room Database  │  │ Firebase   │  │ Application UI │
        │ Local Storage  │  │ Auth       │  │ & Navigation   │
        └───────┬────────┘  └────────────┘  └────────────────┘
                │
                │ Synchronisation
                ↓
        ┌───────────────────────┐
        │ ASP.NET Core REST API  │
        └───────────┬───────────┘
                    │
                    ↓
        ┌───────────────────────┐
        │    Online Database    │
        └───────────────────────┘

The architecture is intended to allow the application to continue using locally stored task information while also supporting online synchronisation.

6. Technologies Used
Technology	Purpose
Kotlin	Main programming language
Android Studio	Android application development
Jetpack Compose	User interface development
Firebase Authentication	Registration and login
Room Database	Local task storage
ASP.NET Core	REST API/backend development
Online Database	Server-side data storage
Git	Version control
GitHub	Source-code repository
GitHub Actions	Automated build and testing
7. GitHub Usage

GitHub is used as the main source-code repository for GrindFlow.

Git is used throughout development to track changes made to the project. Each major development stage can be committed and pushed to GitHub, creating a history of the project's development.

The main benefits of using GitHub for GrindFlow include:

Tracking changes to source code.
Maintaining a history of development.
Creating backups of project files.
Reverting to previous versions when required.
Sharing the project repository.
Supporting continuous integration through GitHub Actions.

A typical Git workflow is:

Developer
    ↓
Make Changes
    ↓
Test Application
    ↓
Git Add
    ↓
Git Commit
    ↓
Git Push
    ↓
GitHub Repository

Meaningful commit messages can also be used to identify the functionality or changes introduced during each stage of development.

8. GitHub Actions

GitHub Actions is used to automate tasks associated with the GrindFlow development process.

A workflow can be configured to automatically run when changes are pushed to the GitHub repository.

For example, the workflow can:

Check out the repository.
Set up the required development environment.
Download Gradle dependencies.
Build the Android project.
Run automated tests.
Report whether the workflow succeeded or failed.

The general workflow can be represented as:

Developer
    ↓
Push Code
    ↓
GitHub
    ↓
GitHub Actions
    ↓
Build Project
    ↓
Run Tests
    ↓
Success / Failure

This provides an automated verification process and can help detect build or testing problems earlier in development.

GitHub Actions therefore contributes to the project's continuous integration (CI) process.

9. Firebase Integration

Firebase is used within GrindFlow to support authentication.

The application is connected to a Firebase project where authentication services can be configured and monitored.

The Firebase Authentication service allows GrindFlow to handle user registration and login without implementing the entire authentication infrastructure manually.

Firebase Dashboard

Insert the Firebase Dashboard screenshot here if you have a separate dashboard image.

Figure 9: Firebase Dashboard used for GrindFlow authentication.

10. Offline Storage and Synchronisation

One of the planned aspects of GrindFlow is the ability to continue working when an internet connection is unavailable.

Room Database provides local storage for tasks.

The planned synchronisation process is:

             ONLINE
                ↓
        ┌───────────────┐
        │   REST API    │
        └───────┬───────┘
                ↕
        ┌───────────────┐
        │  Room Database│
        └───────────────┘
                ↑
             OFFLINE

When the application is offline, task information can continue to be stored locally. When connectivity becomes available, the application can synchronise local changes with the backend.

11. REST API

An ASP.NET Core REST API is being developed as the backend service for GrindFlow.

The API will act as the communication layer between the Android application and the online database.

The planned API operations include:

GET     /api/tasks
GET     /api/tasks/{id}
POST    /api/tasks
PUT     /api/tasks/{id}
DELETE  /api/tasks/{id}

These endpoints will provide the basic operations needed to retrieve, create, update, and delete tasks.

The Android application will exchange data with the API using HTTP requests and JSON.

The intended communication flow is:

GrindFlow Android App
          ↓
      HTTPS / JSON
          ↓
ASP.NET Core REST API
          ↓
    Online Database
12. Future Features

Additional features planned for GrindFlow include:

Firebase Cloud Messaging

Firebase Cloud Messaging can be used to provide notifications about upcoming tasks, deadlines, and other scheduled activities.

Multiple Languages

The application is planned to support at least two South African languages in addition to English.

Gamification

Gamification can be introduced through features such as achievements, points, streaks, or other productivity milestones.

Improved Synchronisation

The online and offline systems can be expanded to provide automatic synchronisation and conflict handling between Room and the REST API.

13. Conclusion

GrindFlow is an Android productivity and schedule-planning application designed to provide users with a central platform for managing daily activities, schedules, tasks, deadlines, and progress.

The application uses Kotlin and Jetpack Compose for Android development, Firebase Authentication for user registration and login, and Room Database for local task storage. An ASP.NET Core REST API is being developed to provide communication between the Android application and an online database.

The project also makes use of GitHub for version control and GitHub Actions for automated development processes such as building and testing.

The combination of local storage, authentication, task management, scheduling, progress tracking, and planned online synchronisation provides the foundation for GrindFlow to function as a complete productivity and schedule-management application.
