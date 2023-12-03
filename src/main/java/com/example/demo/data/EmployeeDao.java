package com.example.demo.data;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public int add(EmployeeEntity employeeEntity) {
        return jdbcTemplate.queryForObject(
                "INSERT INTO test.employees(account_id, name) VALUES (?, ?) RETURNING employee_id",
                Integer.class,
                employeeEntity.getAccountId(),
                employeeEntity.getName());
    }

    public EmployeeEntity getEmployeeById(Integer id) {
        val query = "SELECT * FROM test.employees where employee_id = ?";
        EmployeeEntity employee = jdbcTemplate.queryForObject(
                query,
                new Object[]{id},
                new EmployeeRowMapper()
        );
        return employee;
    }

    public EmployeeEntity getEmployeeByName(String name) {
        val query = "SELECT * FROM test.employees where name = ?";
        EmployeeEntity employee = jdbcTemplate.queryForObject(
                query,
                new Object[]{name},
                new EmployeeRowMapper()
        );
        return employee;
    }
}
