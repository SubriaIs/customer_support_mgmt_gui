# Customer Support Management

## Overview
`customer_support_mgmt_gui` is a JavaFX-based project designed to manage customer support interactions. The system is built with three primary roles: **Customer**, **Staff**, and **Admin**. Each role has different levels of access and functionality, including report management and user creation, new report creation, and report handling.

The project includes a login system for authentication, where users can log in with a user ID and password. New customers can sign up to create their own accounts.

## Features

### Authentication System
- **Login**: All users (Customers, Staff, and Admin) must authenticate with a user ID and password.
  ![Application Screenshot](Authentication%20view.png)
- **Signup**: New customers can create an account using the signup feature.
  ![Application Screenshot](Signup%20View.png)

### Customer View
- **Create Report**: Customers can create new reports detailing their issues.
- **View Reports**: Customers can view all their previously submitted reports in a table format.
- **Search Reports**: Customers can search for specific reports using various parameters.
    ![Application Screenshot](Customer%20view.png)

### Staff View
- **Manage Reports**: Staff members can update the handling status and fixed information of reports that have been assigned to them.
- **Assigned Reports**: Staff can view all the reports assigned to them.
- **Search Reports**: Staff can search through their assigned reports based on different criteria.
    ![Application Screenshot](Staff%20view.png)

### Admin View
Admins have access to two primary views:

1. **Person Management View**:
    - **Create Users**: Admins can create new users (either Customers, Staff, or Admin).
    - **View User Information**: Admins can view all user-related information (for Customers, Staff, and Admins) in a table.
    - **Search Users**: Admins can search for users using various parameters.
      ![Application Screenshot](Admin%20view(Person%20Management).png)
      
2. **Customer Support (cases) View**:
    - **View Reports**: Admins can see all reports (both created and solved) in a table format.
    - **Search Reports**: Admins can search for reports using various parameters.
  ![Application Screenshot](Admin%20view(Customer%20Support(Cases)).png)

## Role-Based Features

## Role-Based Features

| Role     | Features                                                                                                                                     |
|----------|----------------------------------------------------------------------------------------------------------------------------------------------|
| Customer | - Signup and Login  
|          | - Create new support reports  
|          | - View own reports  
|          | - Search reports                                                                                                                             |
| Staff    | - Login  
|          | - Update report handling information  
|          | - View reports assigned to them  
|          | - Search assigned reports                                                                                                                    |
| Admin    | - Login  
|          | - **Person Management**: Create new users (Customers, Staff, or Admins), view user info, and search users by parameters                      |
|          | - **Customer Support (cases) Management**: View all reports (created and solved) and search by various parameters                            |


## Mandatory Dependency
This project depends on another project called `customer_support_mgmt_lib`, which is used as a library in the `customer_support_mgmt_gui` project. 

- **customer_support_mgmt_lib** is required to run the `customer_support_mgmt_gui` project.
- The `customer_support_mgmt_lib` project is available on GitHub and must be downloaded and integrated before running this project.

### Steps to Include `customer_support_mgmt_lib` Library
1. Clone the `customer_support_mgmt_lib` repository from GitHub:
   ```bash
   git clone https://github.com/SubriaIs/customer_support_mgmt_lib

2. Build the project using Maven 
   ```bash
   mvn clean package

## Installation and Setup

### Prerequisites
- **Java 18 or higher**
- **JavaFX SDK** (Ensure that JavaFX libraries are correctly linked to your IDE or build system)
- **Maven** (for dependency management)

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/SubriaIs/customer_support_mgmt_gui
2. Ensure the built customer_support_mgmt_lib library is added as a dependency in the customer_support_mgmt_gui project.
3. Build the project using Maven 
   ```bash
   mvn clean package

### License
This project is licensed under the **MIT License**.

## Contact
For any queries, feel free to contact the project maintainers at:

Email: subria.islam@outlook.com
