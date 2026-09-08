# Expense Tracker

A full-stack expense management application built to help individuals, families, and groups keep track of their spending in a simple and organized way.

The project has three main features

- Personal Expense Tracking — Add, edit, delete, categorize, and analyze personal expenses.
- Family Budget Management — Track spending for different family members and compare expenses against monthly budgets.
- Group Trip Expenses — Manage shared trip expenses without requiring user accounts and view spending breakdowns over time.

The goal of this project was to build a complete full-stack application while learning how a frontend, backend API, and database work together in a real-world setup.

---

# Features

## Personal Expenses

Manage daily expenses with

- Add new expenses
- Edit and delete existing expenses
- Categorize spending
- Filter expenses by category
- View monthly and category-wise summaries

Supported categories

```
FOOD
RENT
TRAVEL
UTILITIES
ENTERTAINMENT
HEALTH
SHOPPING
EDUCATION
OTHER
```

---

## Family Budget Tracking

Keep track of household spending by assigning expenses to family members.

Features

- Add family members
- Set monthly budgets
- Track individual spending
- View budget progress
- Identify when someone exceeds their limit

Example

A family member with a ₹20,000 monthly budget can see how much they have spent and how much remains.

---

## Group Trip Expense Management

Designed for situations like vacations or group outings.

Features

- Create trips
- Add shared expenses
- Track who paid for each expense
- View total trip spending
- See week-by-week spending patterns

No login system is required — simply enter the name of the person who paid.

---

# Tech Stack

## Backend

- Java
- Spring Boot 4.1
- Maven
- MySQL
- REST APIs
- JPA  Hibernate

## Frontend

- React
- Vite
- React Router
- Axios

## Database

- MySQL

---

# Application Architecture

```
React Frontend
       
       
       ↓
Spring Boot REST API
       
       
       ↓
MySQL Database
```

The frontend communicates with the backend through REST APIs, while Spring Boot handles business logic and database operations.

---

# Project Structure

```
expense-tracker

├── backend
│
│   └── srcmainjavacomexpensetrackerbackend
│       ├── entity          # Database models
│       ├── repository      # Database operations
│       ├── service         # Business logic
│       ├── controller      # REST endpoints
│       ├── dto             # Requestresponse objects
│       ├── exception       # Error handling
│       └── config          # Application configuration
│
└── frontend
    └── src
        ├── api             # API communication
        ├── components      # Reusable UI components
        ├── pages           # Application screens
        └── App.jsx          # Routes and navigation
```

---

# Running the Project

## 1. Backend Setup

Make sure you have

- Java 17+
- Maven
- MySQL

Create the database

```sql
CREATE DATABASE expense_tracker;
```

Update your MySQL password in

```
backendsrcmainresourcesapplication.properties
```

Run the backend

```bash
cd backend
mvn spring-bootrun
```

The backend will start on

```
httplocalhost8080
```

---

## 2. Frontend Setup

Install dependencies

```bash
cd frontend
npm install
```

Start React

```bash
npm run dev
```

The frontend will usually run on

```
httplocalhost5173
```

---

# API Overview

## Expenses

 Method  Endpoint  Purpose 
---------
 GET  `apiexpenses`  Get all expenses 
 POST  `apiexpenses`  Create expense 
 PUT  `apiexpenses{id}`  Update expense 
 DELETE  `apiexpenses{id}`  Delete expense 
 GET  `apiexpensessummaryby-category`  Category summary 
 GET  `apiexpensessummaryby-month`  Monthly summary 

---

## Family Members

 Method  Endpoint  Purpose 
---------
 GET  `apifamily-members`  List members 
 POST  `apifamily-members`  Add member 
 PUT  `apifamily-members{id}`  Update member 
 DELETE  `apifamily-members{id}`  Remove member 
 GET  `apifamily-membersbudget-summary`  Budget overview 

---

## Trips

 Method  Endpoint  Purpose 
---------
 GET  `apitrips`  View trips 
 POST  `apitrips`  Create trip 
 GET  `apitrips{id}`  Trip details 
 POST  `apitrips{id}expenses`  Add trip expense 
 DELETE  `apitrips{id}expenses{expenseId}`  Remove expense 
 GET  `apitrips{id}summaryweekly`  Weekly spending summary 

---

# Challenges & Learnings

While building this project, I worked through several real development challenges

### Spring Boot 4.1 Updates

Handled changes including

- Jackson 3 package changes
- Updated testing dependencies
- MySQL connection configuration changes

### Full-Stack Integration

Connected

- React frontend
- Spring Boot backend
- MySQL database

and verified the complete flow from UI interaction to database updates.

### API Design

Designed REST endpoints for

- CRUD operations
- Filtering
- Aggregations
- Budget calculations
- Trip summaries

---

# Future Improvements

Possible improvements

- Add user authentication and profiles
- Add expense charts and analytics
- Add recurring expenses
- Add receipt image upload with OCR
- Add mobile application support
- Deploy using cloud services

---

# Final Note

This project helped me understand how a real full-stack application is built from end to end — starting from database design and backend APIs to frontend development and user interaction.

It focuses on solving a practical problem while applying concepts from Java, Spring Boot, React, databases, and REST API development.