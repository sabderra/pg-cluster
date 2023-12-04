package com.example.demo.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class EmployeeEntity {

    @Id
    private Integer employeeId;
    private Integer accountId;
    private String name;
}
