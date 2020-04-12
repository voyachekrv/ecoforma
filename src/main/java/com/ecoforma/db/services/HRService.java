package com.ecoforma.db.services;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.db.entities.Employee;
import com.ecoforma.db.entities.EmployeeView;
import com.ecoforma.db.entities.RegistrationData;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class HRService {
    public String[] getPostNames() {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getPostNames();
        }
    }

    public String[] getDepartmentNames() {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getDepartmentNames();
        }
    }

    public String[] getRoleNames() {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getRoleNames();
        }
    }

    public ArrayList<EmployeeView> getAllEmployees() {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getAllEmployees();
        }
    }

    public Employee getEmployeeByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getEmployeeByID(ID);
        }
    }

    public RegistrationData getRegistrationData(long employeeID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getRegistrationData(employeeID);
        }
    }

    public ArrayList<EmployeeView> findEmployee(String column, String query) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.findEmployee(column, query);
        }
    }

    public void deleteEmployee(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.deleteEmployee(ID);
            session.commit();
        }
    }

    public void deleteRegistrationData(int employee_ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.deleteRegistrationData(employee_ID);
            session.commit();
        }
    }

    public void updateEmployee(
            int ID,
            String name,
            String dateOfBirth,
            String passport,
            String education,
            String address,
            String phoneNumber,
            String email,
            int post_ID,
            int department_ID,
            int personalSalary
    ) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.updateEmployee(ID, name, dateOfBirth, passport, education, address, phoneNumber, email, post_ID, department_ID, personalSalary);
            session.commit();
        }
    }

    public int getChiefID() {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            return mapper.getChiefID();
        }
    }

    public void setChiefWhenUpdate(int ID, int employee_ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.setChiefWhenUpdate(ID, employee_ID);
            session.commit();
        }
    }

    public void updateRegistrationData(int employee_ID, String login, String password, int role_ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.updateRegistrationData(employee_ID, login, password, role_ID);
            session.commit();
        }
    }

    public void insertRegistrationData(int employee_ID, String login, String password, int role_ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.insertRegistrationData(employee_ID, login, password, role_ID);
            session.commit();
        }
    }

    public void insertEmployee(
            String name,
            String dateOfBirth,
            String passport,
            String education,
            String address,
            String phoneNumber,
            String email,
            int post_ID,
            int department_ID
    ) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.insertEmployee(name, dateOfBirth, passport, education, address, phoneNumber, email, post_ID, department_ID);
            session.commit();
        }
    }

    public void setChiefWhenInsert(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.setChiefWhenInsert(ID);
            session.commit();
        }
    }

    public void insertRegistrationDataWithEmployee(String login, String password, int role_ID) {
        try (SqlSession session = DbSession.startSession()) {
            HRMapper mapper = session.getMapper(HRMapper.class);
            mapper.insertRegistrationDataWithEmployee(login, password, role_ID);
            session.commit();
        }
    }
}
