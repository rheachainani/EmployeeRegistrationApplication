package application;

import javafx.beans.property.*;

public class Employee {
    private final StringProperty employeeId;
    private final StringProperty name;
    private final IntegerProperty age;
    private final StringProperty email;
    private final StringProperty department;

    public Employee(String employeeId, String name, int age, String email, String department) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.email = new SimpleStringProperty(email);
        this.department = new SimpleStringProperty(department);
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }
}
