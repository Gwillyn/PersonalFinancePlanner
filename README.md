# PersonalFinancePlanner

A personal finance tracker web application for managing income, recurring
expenses, budgets, and savings goals. The application is built with Java
Servlets, JSP, MySQL, and Apache Tomcat.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [Contributors](#contributors)

## Overview

Personal Finance Planner allows users to create an account, sign in, and view
their financial information in one place. Authenticated users can manage their
income sources, recurring expenses, budget allocation, savings goals, and
profile information from the web interface.

## Features

### User Accounts

- Register with a name, email address, password, and preferred currency
- Log in and log out using a session-based authentication flow
- Passwords are stored as BCrypt hashes
- Update profile information and preferred currency

### Transaction Management

- Add, edit, and delete income sources
- Add, edit, and delete recurring expenses
- Assign expenses to user-specific categories
- Track monthly income and monthly expenses on the dashboard

### Budgeting

- Create a budget for the current month
- Compare the budget with calculated monthly income
- Display the remaining amount after the budget is saved
- View a dashboard summary of income, expenses, budget, and balance

### Savings Goals

- Create, edit, and delete savings goals
- Track target amount, current amount, monthly contribution, and target date
- Display savings goal progress on the dashboard

### Reports

The current application provides financial summaries through the dashboard.
A separate reports module or reporting page is not currently implemented.

## Technologies

### Backend

- Java 21
- Jakarta Servlets
- Apache Tomcat 11.0.4
- JDBC
- BCrypt password hashing

### Frontend

- HTML
- CSS
- JSP
- JavaScript for page interactions

### Database

- MySQL
- MySQL Connector/J

### Tools

- Eclipse/WTP project configuration
- Git
- GitHub

## Project Structure

```text
PersonalFinancePlanner/
├── database/
│   ├── schema.sql
│   ├── personal-finance-planner-erd-v1.dbml
│   └── personal-finance-planner-erd-v1.png
├── src/main/java/com/personalBudgetPlanner/
│   ├── controller/       # Jakarta Servlets
│   └── database/         # Database connection and DAO classes
└── src/main/webapp/
    ├── WEB-INF/views/    # JSP views
    ├── WEB-INF/lib/      # MySQL Connector/J and jBCrypt
    └── css/              # Application stylesheets
```

## Database Setup

1. Install and start MySQL.
2. Create the database and tables by running the schema file:

   ```bash
   mysql -u root -p < database/schema.sql
   ```

3. Create the application database user and grant it access to the database:

   ```sql
   CREATE USER 'root'@'localhost' IDENTIFIED BY 'your-password';
   GRANT ALL PRIVILEGES ON personal_finance_planner.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

   If the user already exists, update its password instead of running the
   `CREATE USER` statement.

4. Set the password as the `DB_PASSWORD` environment variable. The application
   connects to `localhost:3306/personal_finance_planner` `root`.

   ```bash
   export DB_PASSWORD='your-password'
   ```

## Usage

1. Import the project into Eclipse as an existing Dynamic Web Project.
2. Configure the project to use Java 21 and Apache Tomcat 11.0.4.
3. Confirm that MySQL is running, the database schema has been applied, and
   `DB_PASSWORD` is available to the Tomcat process.
4. Run the project on Tomcat and open:

   ```text
   http://localhost:8080/<context-path>/
   ```

   The application entry point redirects to the dashboard; users who are not
   signed in are redirected to the login page.

5. Register a user, sign in, and use the navigation bar to manage income,
   expenses, budgets, savings goals, and profile information.

## Contributors

1. Walaa Abd Al Khane - https://github.com/Walaa1505, abda0156@algonquinlive.com
2. Maria Novikova - https://github.com/marianovikova617
3. Gwillyn Donaghy - https://github.com/Gwillyn, dona0173@algonquinlive.com
4. Jacob Daviau - davi1113@algonquinlive.com

