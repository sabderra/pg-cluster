package com.example.demo.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class EmployeeDaoTests {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void shouldGetEmployeeByName() {

        String name = "one";
        EmployeeEntity employee = new EmployeeEntity();
        employee.setAccountId(1);
        employee.setName(name);
        int count = employeeDao.add(employee);
        assertThat(count)
                .isGreaterThan(0);

        final var dbEmployee = employeeDao.getEmployeeByName(name);
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
        int id = employeeDao.add(employee);
        assertThat(id)
                .isGreaterThan(0);

        final var dbEmployee = employeeDao.getEmployeeById(id);
        assertThat(dbEmployee)
                .returns(id, EmployeeEntity::getEmployeeId)
                .returns(accountId, EmployeeEntity::getAccountId)
                .returns(name, EmployeeEntity::getName);
    }

}
