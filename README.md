# Employee Registration Application

This JavaFX application allows users to perform various operations related to employee registration, including creating a new employee table, registering employees, viewing employee details, and updating employee information.

## Features

- **Create Employee Table**: Creates a new table in the database to store employee information if it does not already exist.

- **Register Employee**: Allows users to register new employees by providing their details such as employee ID, name, age, email, and department.

- **View Employees**: Displays a list of all registered employees with their details in a table format.

- **Update Employee**: Allows users to update the information of an existing employee by providing their employee ID and new details.

## How to Use

1. **Database Setup**: Ensure you have a MariaDB database named `EmployeeRegistration` running locally on port `3306`, and change the code according to your username and password.

2. **Running the Application**: Execute the `Main.java` file to launch the application. This will open the main window of the application.

3. **Operations**:
    - Click on the buttons in the left panel to perform various operations.
    - Fill in the required fields and click on the respective buttons to perform actions like registering, viewing, and updating employees.
    
4. **Alerts**:
    - Information and error messages will be displayed using JavaFX alert dialogs to provide feedback on the success or failure of operations.

## Screenshots

![Screenshot 2024-05-10 120717](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/4c06321d-bcb8-4aaa-a9f5-b8ca085b29f3)
On clicking ‘Create Employee Table’ when table already exists:
![Screenshot 2024-05-10 120735](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/ba6a9dd2-f05a-4f82-925b-f20370787ddf)
On clicking ‘Register Employee’ or ‘Update Employee’ without filling all the fields:
![Screenshot 2024-05-10 120749](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/3809cf78-7b47-4a2f-9016-0da370be67ec)
On clicking ‘Register Employee’ after filling all the fields:
![Screenshot 2024-05-10 120911](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/b79fcce8-e749-475a-af62-600345863f04)
On clicking ‘Update Employee’ after filling all the fields with required updates:
![Screenshot 2024-05-10 120955](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/34a1122d-c8f2-490a-a428-e53f7be54908)
On clicking ‘View Employees’:
![Screenshot 2024-05-10 121010](https://github.com/rheachainani/EmployeeRegistrationApplication/assets/112756676/0441ca77-90ec-4e17-8b0f-da1766dd6b45)

## Technologies Used

- JavaFX
- MariaDB
- JDBC (Java Database Connectivity)

## Requirements

- Java Development Kit (JDK) 8 or higher
- MariaDB
- JavaFX SDK
