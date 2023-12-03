package com.example.demo.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void shouldGetEmployeeByName() {

        String name = "one";
        EmployeeEntity employee = new EmployeeEntity();
        employee.setAccountId(1);
        employee.setName(name);
        int count = employeeRepository.add(employee);
        assertThat(count)
                .isGreaterThan(0);

        final var dbEmployee = employeeRepository.getEmployeeByName(name);
        assertThat(dbEmployee)
                .returns(1, EmployeeEntity::getAccountId)
                .returns(name, EmployeeEntity::getName);
    }

    @Test
    @Order(2)
    void shouldGetEmployeeByNameReadOnly() {

        String name = "one";
        final var dbEmployee = employeeRepository.getEmployeeByName(name);
        assertThat(dbEmployee)
                .returns(1, EmployeeEntity::getAccountId)
                .returns(name, EmployeeEntity::getName);
    }

    @Test
    void shouldGetEmployeeById() {
        String name = "two";
        int accountId = 2;
        EmployeeEntity employee = new EmployeeEntity();
        employee.setAccountId(accountId);
        employee.setName(name);
        int id = employeeRepository.add(employee);
        assertThat(id)
                .isGreaterThan(0);

        final var dbEmployee = employeeRepository.getEmployeeById(id);
        assertThat(dbEmployee)
                .returns(id, EmployeeEntity::getEmployeeId)
                .returns(accountId, EmployeeEntity::getAccountId)
                .returns(name, EmployeeEntity::getName);
    }

}
