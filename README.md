# 🚦 Traffic Management System (Java OOP Project)

## 📌 Overview

This project is a **Traffic Management System** implemented in Java, designed to simulate real-world traffic operations such as managing vehicles, traffic lights, zones, violations, and system users.

The system aims to:

* Reduce traffic congestion
* Improve road safety
* Track violations efficiently
* Provide structured communication between system components

---

## 🧠 System Design

### 👤 Users

#### 🛠️ Admin

* Manage traffic lights (add / update / delete)
* View violations by vehicle or zone
* Generate reports
* Monitor system activities

#### 👮 Traffic Officer

* Record traffic violations
* Calculate fines automatically
* View violations
* Manage zone density

#### 🚗 Vehicle Owner

* View violations
* Pay fines
* Manage vehicles
* Receive notifications
* Track vehicle status

---

## ⚙️ System Features

* 🚦 Traffic Light Management
* 🚗 Vehicle & Owner Management
* 🚨 Violation Recording System
* 💰 Fine Calculation
* 🔔 Notification System
* 📊 Traffic Reports
* 📁 File Handling (Data Persistence)

---

## 🧩 Core Functionalities

### 🚨 Violation System

* Records violations with validation
* Automatically calculates fines
* Prevents invalid vehicle entries

### 🔔 Notification System

* Sends notifications to vehicle owners when violations occur
* Displays notifications **only once**
* Removes notifications after being viewed

### 📊 Reports & Monitoring

* Tracks violations by zones
* Detects high-density areas
* Helps in traffic analysis

---

## 📂 Project Structure

TrafficManagementSystem/
├── traffic/
│   ├── TrafficLight.java
│   ├── Zone.java
│   ├── File_Manager.java
│
├── trafficmanagementsystem/
│   ├── Admin.java
│   ├── TrafficOfficer.java
│   ├── TrafficViolation.java
│   ├── Vehicle.java
│   ├── Owner.java

---

## ⚡ Technologies Used

* Java
* Object-Oriented Programming (OOP)
* File Handling
* Collections Framework

---

## 🚀 How to Run

1. Open the project in an IDE (IntelliJ / Eclipse)

2. Compile:

```bash
javac *.java
```

3. Run:

```bash
java Main
```

---

## 💻 Sample Flow

* Traffic Officer records a violation
* System validates vehicle ID
* Fine is calculated automatically
* Owner receives notification
* Owner logs in and views notification (only once)

---

## 👥 Team Members

* Bassant Mohamed
* Omar Ashraf
* Omar Badr
* Omar Gamal
* Salma Magdy
* Ziad Magdy
* Ziad Osama

---

## 🚀 My Contribution

* Implemented core functionalities of the **Traffic Officer module**

  * Recording traffic violations with validation
  * Automatic fine calculation based on violation type
  * Viewing and managing recorded violations

- Contributed significantly to the **notification system**, including:
  - Implementing notification delivery for vehicle owners after violations
  - Developing the logic for displaying notifications only once and clearing them after viewing
  - Integrating notifications with the violation and vehicle-owner flow

* Built core **validation logic**, including:

  * Preventing recording violations for non-existing vehicles
  * Ensuring correct data flow between system components
  * Handling edge cases and invalid inputs

* Contributed to **system integration**, connecting:

  * Traffic Officer → Violation → Vehicle Owner

* Assisted in **debugging and improving system stability**, especially in:

  * Notification flow
  * Data consistency across modules

---

## 🏁 Conclusion

This system demonstrates a real-world simulation of traffic management using object-oriented design, efficient data handling, and modular system integration.

---
