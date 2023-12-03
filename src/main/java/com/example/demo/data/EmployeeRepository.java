package com.example.demo.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeRepository {

    private final EmployeeDao employeeDao;

    public int add(EmployeeEntity employeeEntity) {
        return employeeDao.add(employeeEntity);
    }

    @Transactional(readOnly = true)
    public EmployeeEntity getEmployeeById(Integer id) {
        return employeeDao.getEmployeeById(id);
    }

    @Transactional(readOnly = true)
    public EmployeeEntity getEmployeeByName(String name) {
        return employeeDao.getEmployeeByName(name);
    }
}
