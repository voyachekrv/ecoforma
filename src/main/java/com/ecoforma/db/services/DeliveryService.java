package com.ecoforma.db.services;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.entities.Employee;
import com.ecoforma.db.entities.Order;
import com.ecoforma.db.mappers.DeliveryMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class DeliveryService {
    public ArrayList<Order> getAllOrders() {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            return mapper.getAllOrders();
        }
    }

    public ArrayList<Employee> getAllDrivers() {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            return mapper.getAllDrivers();
        }
    }

    public ArrayList<Employee> getDriverByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            return mapper.getDriverByID(ID);
        }
    }

    public ArrayList<Employee> getDriverByName(String name) {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            return mapper.getDriverByName(name);
        }
    }

    public void setDriverOnDelivery(int order_ID, int employee_ID, String status) {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            mapper.setDriverOnDelivery(order_ID, employee_ID, status);
            session.commit();
        }
    }

    public void setStatus(int order_ID, String status) {
        try (SqlSession session = DbSession.startSession()) {
            DeliveryMapper mapper = session.getMapper(DeliveryMapper.class);
            mapper.setStatus(order_ID, status);
            session.commit();
        }
    }
}
