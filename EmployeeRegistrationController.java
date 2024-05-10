package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRegistrationController {

    @FXML
    private TextField employeeIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField departmentTextField;

    private Connection connection;

    public void initialize() {
        try {
            // Connect to MariaDB
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/EmployeeRegistration", "testuser", "test123");
        } catch (SQLException e) {
            //e.printStackTrace();
            showAlert("Database Error", "Failed to connect to the database.");
        }
    }

    @FXML
    private void createEmployeeTable() {
        try {
        	// Check if table already exists
        	DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "employee", null);
            if (tables.next()) {
                showAlert("Table Already Exists", "Employee table already exists.");
                return;
            }
            
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE employee (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "employee_id VARCHAR(50) UNIQUE NOT NULL," +
                    "name VARCHAR(100) NOT NULL," +
                    "age INT NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "department VARCHAR(100) NOT NULL" +
                    ")";
            statement.executeUpdate(query);
            showAlert("Success", "Employee table created successfully.");
        } catch (SQLException e) {
            //e.printStackTrace();
            showAlert("Database Error", "Failed to create employee table.");
        }
    }

    @FXML
    private void registerEmployee() {
        String employeeId = employeeIdTextField.getText();
        String name = nameTextField.getText();
        String ageText = ageTextField.getText();
        String email = emailTextField.getText();
        String department = departmentTextField.getText();

        // Check for empty fields
        if (employeeId.isEmpty() || name.isEmpty() || ageText.isEmpty() || email.isEmpty() || department.isEmpty()) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }

        try {
            // Check if employee ID already exists
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");
            checkStatement.setString(1, employeeId);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                showAlert("Registration Error", "Employee with ID: " + employeeId + " already exists.");
                return;
            }

            // Insert new employee
            String query = "INSERT INTO employee (employee_id, name, age, email, department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            statement.setString(2, name);
            statement.setInt(3, Integer.parseInt(ageText));
            statement.setString(4, email);
            statement.setString(5, department);
            statement.executeUpdate();
            showAlert("Success", "Employee registered successfully.");
        } catch (SQLException e) {
            //e.printStackTrace();
            showAlert("Database Error", "Failed to register employee.");
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid age format. Please enter a valid integer for age.");
        }
    }

    @FXML
    private void viewEmployees() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

            TableView<Employee> tableView = new TableView<>();
            List<TableColumn<Employee, ?>> columns = new ArrayList<>();

            TableColumn<Employee, String> idColumn = new TableColumn<>("Employee ID");
            idColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty());
            columns.add(idColumn);

            TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            columns.add(nameColumn);

            TableColumn<Employee, Integer> ageColumn = new TableColumn<>("Age");
            ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
            columns.add(ageColumn);

            TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
            columns.add(emailColumn);

            TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
            departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
            columns.add(departmentColumn);

            tableView.getColumns().addAll(columns);

            ObservableList<Employee> employees = FXCollections.observableArrayList();
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getString("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getString("department")
                ));
            }
            tableView.setItems(employees);

            // create custom dialog
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Employee List");

            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tableView);

            dialog.getDialogPane().setContent(vBox);

            dialog.setWidth(520);

            dialog.showAndWait();
        
        } catch (SQLException e) {
            //e.printStackTrace();
            showAlert("Database Error", "Failed to fetch employee list.");
        }
    }


    @FXML
    private void updateEmployee() {
        String employeeId = employeeIdTextField.getText();
        String name = nameTextField.getText();
        String ageText = ageTextField.getText();
        String email = emailTextField.getText();
        String department = departmentTextField.getText();

        // Check for empty fields
        if (employeeId.isEmpty() || name.isEmpty() || ageText.isEmpty() || email.isEmpty() || department.isEmpty()) {
            showAlert("Error", "Please fill in all the fields.");
            return;
        }

        try {
            // Check if employee ID exists
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM employee WHERE employee_id = ?");
            checkStatement.setString(1, employeeId);
            ResultSet resultSet = checkStatement.executeQuery();
            if (!resultSet.next()) {
                showAlert("Update Error", "No employee found with ID: " + employeeId);
                return;
            }

            // Update employee details
            String query = "UPDATE employee SET name=?, age=?, email=?, department=? WHERE employee_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, Integer.parseInt(ageText));
            statement.setString(3, email);
            statement.setString(4, department);
            statement.setString(5, employeeId);
            statement.executeUpdate();
            showAlert("Success", "Employee updated successfully.");
        } catch (SQLException e) {
            //e.printStackTrace();
            showAlert("Database Error", "Failed to update employee.");
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid age format. Please enter a valid integer for age.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}