package com.challenge.api.service;

import com.challenge.api.model.CreateEmployeeRequest;
import com.challenge.api.model.DefaultEmployee;
import com.challenge.api.model.Employee;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final Map<UUID, DefaultEmployee> employees = new ConcurrentHashMap<>();

    public EmployeeService() {
        seedEmployees();
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    public Optional<Employee> getEmployeeByUuid(UUID uuid) {
        return Optional.ofNullable(employees.get(uuid));
    }

    public Employee createEmployee(CreateEmployeeRequest request) {
        validateRequest(request);

        DefaultEmployee employee = new DefaultEmployee();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setFullName(request.getFirstName() + " " + request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setJobTitle(request.getTitle());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        employee.setAge(request.getAge());
        employee.setContractHireDate(Instant.now());
        employees.put(employee.getUuid(), employee);
        return employee;
    }

    private void seedEmployees() {
        addEmployee("Ava", "Lopez", "ava.lopez@example.com", "Software Engineer", "Engineering", 115000, 26);
        addEmployee("Noah", "Patel", "noah.patel@example.com", "Platform Engineer", "Infrastructure", 124000, 29);
        addEmployee("Zoe", "Reed", "zoe.reed@example.com", "Product Manager", "Product", 132000, 32);
    }

    private void addEmployee(
            String firstName,
            String lastName,
            String email,
            String title,
            String department,
            Integer salary,
            Integer age) {
        DefaultEmployee employee = new DefaultEmployee();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setFullName(firstName + " " + lastName);
        employee.setEmail(email);
        employee.setJobTitle(title);
        employee.setDepartment(department);
        employee.setSalary(salary);
        employee.setAge(age);
        employee.setContractHireDate(Instant.now());
        employees.put(employee.getUuid(), employee);
    }

    private void validateRequest(CreateEmployeeRequest request) {
        if (isBlank(request.getFirstName())) {
            throw new IllegalArgumentException("firstName is required.");
        }
        if (isBlank(request.getLastName())) {
            throw new IllegalArgumentException("lastName is required.");
        }
        if (isBlank(request.getEmail())) {
            throw new IllegalArgumentException("email is required.");
        }
        if (isBlank(request.getTitle())) {
            throw new IllegalArgumentException("title is required.");
        }
        if (isBlank(request.getDepartment())) {
            throw new IllegalArgumentException("department is required.");
        }
        if (request.getSalary() == null || request.getSalary() <= 0) {
            throw new IllegalArgumentException("salary must be greater than 0.");
        }
        if (request.getAge() == null || request.getAge() <= 0) {
            throw new IllegalArgumentException("age must be greater than 0.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
