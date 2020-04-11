package com.ecoforma.db.services;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.entities.*;
import com.ecoforma.db.mappers.SaleMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class SaleService {
    public ArrayList<LegalPersonView> getAllLegalPersons() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllLegalPersons();
        }
    }

    public ArrayList<ContractView> getAllContractsModel() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllContractsModel();
        }
    }

    public Contract getContractByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getContractByID(ID);
        }
    }

    public ArrayList<Contract> getAllContracts() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllContracts();
        }
    }

    public ArrayList<IndividualPersonView> getAllIndividualPersons() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllIndividualPersons();
        }
    }

    public LegalPersonView getLegalPersonByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getLegalPersonByID(ID);
        }
    }

    public IndividualPersonView getIndividualPersonByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getIndividualPersonByID(ID);
        }
    }

    public int getContractID(String name, String dateOfEnd) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getContractID(name, dateOfEnd);
        }
    }

    public int getContractID(String name) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getContractIDByName(name);
        }
    }

    public ArrayList<ProductOnCashBox> getAllProductsOnCashBox() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllProductsOnCashBox();
        }
    }

    public ArrayList<String> getStoreNames() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getStoreNames();
        }
    }

    public String[] getPaymentTypes() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getPaymentTypes();
        }
    }

    public Employee getEmployeeOnCashBox(String login, String password) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getEmployeeOnCashBox(login, password);
        }
    }

    public ArrayList<ProductOnCashBox> searchProductsOnCashBox(String column, String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchProductsOnCashBox(column, query);
        }
    }

    public ArrayList<ProductOnCashBox> searchProductsOnCashBoxInStore(String column, String query, String store) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchProductsOnCashBoxInStore(column, query, store);
        }
    }

    public StoreOnCashBox getStoreInformation(String store) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getStoreInformation(store);
        }
    }

    public ArrayList<Customer> getCustomersOnList(int isLegal) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getCustomersOnList(isLegal);
        }
    }

    public ArrayList<Customer> searchCustomerOnList(String column, String query, int isLegal) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchCustomerOnList(column, query, isLegal);
        }
    }

    public int getStoreID(String name) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getStoreID(name);
        }
    }

    public ArrayList<OrderWithPrepayment> getAllOrdersWithPrepayment() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllOrdersWithPrepayment();
        }
    }

    public ArrayList<OrderWithoutPrepayment> getAllOrdersWithoutPrepayment() {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.getAllOrdersWithoutPrepayment();
        }
    }

    public ArrayList<OrderWithPrepayment> searchOrdersWithPrepayment(String column, String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchOrdersWithPrepayment(column, query);
        }
    }

    public ArrayList<OrderWithoutPrepayment> searchOrdersWithoutPrepayment(String column, String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchOrdersWithoutPrepayment(column, query);
        }
    }

    public void deleteContract(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.deleteContract(ID);
            session.commit();
        }
    }

    public void deleteCustomer(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.deleteCustomer(ID);
            session.commit();
        }
    }

    public void updateCustomer(int ID, String name, String adress, String phoneNumber) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.updateCustomer(ID, name, adress, phoneNumber);
            session.commit();
        }
    }

    public void updateContractWithLegal(int ID, int contractWithLegal_ID) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.updateContractWithLegal(ID, contractWithLegal_ID);
            session.commit();
        }
    }

    public void insertLegalPerson(String name, String adress, int сontractWithLegal_ID, String phoneNumber) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.insertLegalPerson(name, adress, сontractWithLegal_ID, phoneNumber);
            session.commit();
        }
    }

    public void insertIndividualPerson(String name, String adress, String phoneNumber) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.insertIndividualPerson(name, adress, phoneNumber);
            session.commit();
        }
    }

    public ArrayList<LegalPersonView> searchLegalPerson(String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchLegalPerson(query);
        }
    }

    public ArrayList<IndividualPersonView> searchIndividualPerson(String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchIndividualPerson(query);
        }
    }

    public ArrayList<Contract> searchContracts(String query) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            return mapper.searchContracts(query);
        }
    }

    public void addSurcharge(int ID, int surcharge) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.addSurcharge(ID, surcharge);
            session.commit();
        }
    }

    public void insertContract(String name, String dateOfEnd) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.insertContract(name, dateOfEnd);
            session.commit();
        }
    }

    public void updateContract(int ID, String name, String dateOfEnd) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.updateContract(ID, name, dateOfEnd);
            session.commit();
        }
    }

    public void addOrderWithPrepayment(
            int customer_ID,
            int product_ID,
            int store_ID,
            int employee_ID,
            int count,
            int paymentType_ID,
            int prepayment,
            int fullPayment,
            byte isDeliveryNeeded
    ) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.addOrderWithPrepayment(
                    customer_ID,
                    product_ID,
                    store_ID,
                    employee_ID,
                    count,
                    paymentType_ID,
                    prepayment,
                    fullPayment,
                    isDeliveryNeeded
            );
            session.commit();
        }
    }

    public void addOrderWithoutPrepayment(
            int customer_ID,
            int product_ID,
            int store_ID,
            int employee_ID,
            int count,
            int paymentType_ID,
            int fullPayment,
            byte isDeliveryNeeded
    ) {
        try (SqlSession session = DbSession.startSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            mapper.addOrderWithoutPrepayment(customer_ID, product_ID, store_ID, employee_ID, count, paymentType_ID, fullPayment, isDeliveryNeeded);
            session.commit();
        }
    }
}
