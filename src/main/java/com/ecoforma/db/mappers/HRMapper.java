package com.ecoforma.db.mappers;

import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;
import com.ecoforma.entities.RegistrationData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface HRMapper {
    @Select("SELECT employee.ID, employee.name, employee.dateOfBirth, employee.passport, employee.education, " +
            "employee.adress, employee.phoneNumber, employee.email, employee.dateOfEmployment, post.name AS 'post', department.name AS 'department', " +
            "employee.personalSalary FROM employee JOIN post ON employee.post_ID = post.ID " +
            "JOIN department ON employee.department_ID = department.ID WHERE employee.deleted = 0;")
    ArrayList<EmployeeFull> getAllEmployees();

    @Select("SELECT * FROM employee WHERE (ID = #{ID} AND deleted = 0);")
    Employee getEmployeeByID(@Param("ID") long ID);

    @Select("SELECT name FROM post WHERE deleted = 0")
    String[] getPostNames();

    @Select("SELECT name FROM department WHERE deleted = 0")
    String[] getDepartmentNames();

    @Select("SELECT employee.ID, employee.name, employee.dateOfBirth, employee.passport, employee.education, employee.adress, " +
            "employee.phoneNumber, employee.email, employee.dateOfEmployment, post.name AS 'post', department.name AS 'department', " +
            "employee.personalSalary FROM employee JOIN post ON employee.post_ID = post.ID JOIN department ON employee.department_ID = department.ID " +
            "WHERE (${column} LIKE ${query} AND employee.deleted = 0);")
    ArrayList<EmployeeFull> findEmployee(@Param("column") String column, @Param("query") String query);

    @Select("SELECT * FROM registrationData WHERE employee_ID = #{employeeID}")
    RegistrationData getRegistrationData(@Param("employeeID") long employeeID);

    @Select("SELECT name FROM role")
    String[] getRoleNames();

    @Update("UPDATE employee SET deleted = 1 WHERE ID = #{ID};")
    void deleteEmployee(@Param("ID") int ID);

    @Update("UPDATE employee SET name = #{name}, dateOfBirth = #{dateOfBirth}, passport = #{passport}, " +
            "education = #{education}, adress = #{adress}, phoneNumber = #{phoneNumber}, email = #{email}, " +
            "post_ID = #{post_ID}, department_ID = #{department_ID}, personalSalary = #{personalSalary} WHERE ID = #{ID};")
    void updateEmployee(
            @Param("ID") long ID,
            @Param("name") String name,
            @Param("dateOfBirth") String dateOfBirth,
            @Param("passport") String passport,
            @Param("education") String education,
            @Param("adress") String adress,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email,
            @Param("post_ID") int post_ID,
            @Param("department_ID") int department_ID,
            @Param("personalSalary") int personalSalary
    );

    @Update("UPDATE employee SET name = #{name} WHERE ID = #{ID};")
    void updateName(@Param("ID") int ID, @Param("name") String name);
}
