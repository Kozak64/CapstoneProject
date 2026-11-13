<div align="center">

# Gym Membership System
**Cebu Institute of Technology - University**  
**CSIT227 – Object-Oriented Programming | Capstone Project**  
**BSCS-F2 (Group C)**

</div>

## Group Members
* **Ecarma, Kirsten Van B.**
* **Gabito, Khian F.**
* **Palang, Javen Shane C.**
* **Villarba, Neil Allen A.**
* **Zarate, James Philip Rex T.**
<br>

## Project Overview
The **Gym Membership System** is a Java-based desktop application featuring a Graphical User Interface (GUI) built to automate and streamline gym operations. The system facilitates seamless interaction between three distinct user roles: **Members**, **Trainers**, and **Admins**. 

Through this application, gym members can register for membership plans, book training sessions, and process payments, while trainers can manage their schedules and track attendance. Administrators have full oversight to manage users, membership plans, and financial reports.
<br>
<br>

##  Code Breakdown & OOP Principles Used

Our system is designed around the four core pillars of Object-Oriented Programming (OOP), supplemented by error handling, file handling, and interactive GUI components.

### 1. The Four Pillars of OOP

* **Abstraction (Abstract Classes & Interfaces)**
  * **Abstract Class (`User`):** We created an abstract parent class called `User` that holds common attributes (`userId`, `name`, `email`, `password`, `role`) and shared methods (`login()`, `logout()`, `updateProfile()`). It also declares an abstract method `displayDashboard()`, which forces child classes to provide their own specific dashboard implementations.
  * **Interface (`PaymentProcess`):** We defined the `PaymentProcess` interface containing the `processPayment()` method. This establishes a strict contract that the `Payment` class implements to handle membership transactions reliably.

* **Inheritance**
  * The **`Member`**, **`Admin`**, and **`Trainer`** classes inherit directly from the abstract `User` superclass. By extending `User`, these child classes automatically reuse core account properties while introducing specialized fields and behaviors unique to their roles (e.g., `memberId` and `bookSession()` for `Member`; `specialization` and `markAttendance()` for `Trainer`; `generateReport()` for `Admin`).

* **Encapsulation**
  * Sensitive user data and system states such as `name`, `password`, `email`, and `membershipPlan` are kept `private` within their respective classes. 
  * When a user registers or updates information via `JTextField` inputs on the registration form, the GUI gathers the inputs and passes them to the `User` or `Member` class using public **setter methods**. When displaying records on the dashboard via a `JTable`, the GUI retrieves the data strictly through **getter methods**, preventing unauthorized direct access or accidental data corruption.

* **Polymorphism**
  * Our application utilizes dynamic method dispatch through the `User` reference. When a user logs into the system, the main dashboard holds a generic `User` object. Depending on whether the authenticated user is a `Member`, `Admin`, or `Trainer`, calling `displayDashboard()` dynamically executes the overridden method corresponding to their specific role.
