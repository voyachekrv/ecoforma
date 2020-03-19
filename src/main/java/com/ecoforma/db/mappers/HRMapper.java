package com.ecoforma.db.mappers;

import com.ecoforma.entities.Employee;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface HRMapper {
    @Select("SELECT employee.ID, employee.name, employee.dateOfBirth, employee.passport, employee.education, employee.adress, employee.phoneNumber, employee.email, employee.dateOfEmployment, post.name AS 'post', department.name AS 'department', employee.personalSalary FROM employee JOIN post ON employee.post_ID = post.ID JOIN department ON employee.department_ID = department.ID WHERE employee.deleted = 0;")
    ArrayList<Employee> getAllEmployees();
}
