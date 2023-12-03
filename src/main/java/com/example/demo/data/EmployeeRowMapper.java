package com.example.demo.data;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<EmployeeEntity> {
    @Override
    public EmployeeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeEntity employee = new EmployeeEntity();

        employee.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
        employee.setAccountId(rs.getInt("ACCOUNT_ID"));
        employee.setName(rs.getString("NAME"));

        return employee;
    }
}