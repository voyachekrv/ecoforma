package com.ecoforma.db.mappers;

import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface HRMapper {
    @Select("SELECT employee.ID, employee.name, employee.dateOfBirth, employee.passport, employee.education, employee.adress, employee.phoneNumber, employee.email, employee.dateOfEmployment, post.name AS 'post', department.name AS 'department', employee.personalSalary FROM employee JOIN post ON employee.post_ID = post.ID JOIN department ON employee.department_ID = department.ID WHERE employee.deleted = 0;")
    ArrayList<EmployeeFull> getAllEmployees();

    @Select("SELECT * FROM employee WHERE (ID = #{ID} AND deleted = 0);")
    Employee getEmployeeByID(@Param("ID") long ID);

    @Select("SELECT name FROM post WHERE deleted = 0")
    String[] getPostNames();

    @Select("SELECT name FROM department WHERE deleted = 0")
    String[] getDepartmentNames();
}
