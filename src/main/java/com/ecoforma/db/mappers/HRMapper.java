package com.ecoforma.db.mappers;

import com.ecoforma.entities.Employee;
import com.ecoforma.entities.EmployeeFull;
import com.ecoforma.entities.RegistrationData;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT ID FROM post WHERE name = 'Начальник отдела'")
    int getChiefID();

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

    @Update("UPDATE registrationData SET login = #{login}, password = #{password}, role_ID = #{role_ID} WHERE employee_ID = #{employee_ID}")
    void updateRegistrationData(
            @Param("employee_ID") int employee_ID,
            @Param("login") String login,
            @Param("password") String password,
            @Param("role_ID") int role_ID
    );

    @Update("UPDATE department SET employee_ID = #{employee_ID} WHERE ID = #{ID}")
    void setChiefWhenUpdate(@Param("ID") int ID, @Param("employee_ID") int employee_ID);

    @Update("UPDATE department SET employee_ID = (SELECT TOP 1 ID FROM employee ORDER BY ID DESC) WHERE ID = #{ID}")
    void setChiefWhenInsert(@Param("ID") int ID);

    @Insert("INSERT INTO registrationData (employee_ID, login, password, role_ID) VALUES (#{employee_ID}, #{login}, #{password}, #{role_ID});")
    void insertRegistrationData(
            @Param("employee_ID") int employee_ID,
            @Param("login") String login,
            @Param("password") String password,
            @Param("role_ID") int role_ID
    );

    @Delete("DELETE FROM registrationData WHERE employee_ID = #{employee_ID}")
    void deleteRegistrationData(@Param("employee_ID") int employee_ID);

    @Insert("INSERT INTO employee (name, dateOfBirth, passport, education, adress, phoneNumber, email, dateOfEmployment, " +
            "post_ID, department_ID, personalSalary, deleted) VALUES (#{name}, #{dateOfBirth}, #{passport}, #{education}, #{adress}, " +
            "#{phoneNumber}, #{email}, GETDATE(), #{post_ID}, #{department_ID}, (SELECT salary FROM post WHERE ID = #{post_ID}), 0);")
    void insertEmployee(
            @Param("name") String name,
            @Param("dateOfBirth") String dateOfBirth,
            @Param("passport") String passport,
            @Param("education") String education,
            @Param("adress") String adress,
            @Param("phoneNumber") String phoneNumber,
            @Param("email") String email,
            @Param("post_ID") int post_ID,
            @Param("department_ID") int department_ID
    );

    @Insert("INSERT INTO registrationData (employee_ID, login, password, role_ID) VALUES " +
            "((SELECT TOP 1 ID FROM employee ORDER BY ID DESC), #{login}, #{password}, #{role_ID});")
    void insertRegistrationDataWithEmployee(
            @Param("login") String login,
            @Param("password") String password,
            @Param("role_ID") int role_ID
    );
}
