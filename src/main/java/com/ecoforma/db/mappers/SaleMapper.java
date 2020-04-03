package com.ecoforma.db.mappers;

import com.ecoforma.db.entities.Contract;
import com.ecoforma.db.entities.ContractView;
import com.ecoforma.db.entities.IndividualPersonView;
import com.ecoforma.db.entities.LegalPersonView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface SaleMapper {
    @Select("SELECT customer.ID, customer.name, customer.adress, customer.phoneNumber, сontractWithLegal.name " +
            "AS 'contractName', сontractWithLegal.dateOfEnd AS 'endOfContract' " +
            "FROM customer JOIN сontractWithLegal ON customer.сontractWithLegal_ID = сontractWithLegal.ID " +
            "WHERE (customer.isLegalPerson = 1 AND customer.deleted = 0);")
    ArrayList<LegalPersonView> getAllLegalPersons();

    @Select("SELECT ID, name FROM сontractWithLegal WHERE deleted = 0;")
    ArrayList<ContractView> getAllContractsModel();

    @Select("SELECT ID, name, dateOfEnd FROM сontractWithLegal WHERE deleted = 0;")
    ArrayList<Contract> getAllContracts();

    @Select("SELECT ID, name, dateOfEnd FROM сontractWithLegal WHERE deleted = 0 AND ID = #{ID};")
    Contract getContractByID(@Param("ID") int ID);

    @Select("SELECT ID, name, adress, phoneNumber FROM customer WHERE isLegalPerson = 0 AND deleted = 0;")
    ArrayList<IndividualPersonView> getAllIndividualPersons();

    @Select("SELECT customer.ID, customer.name, customer.adress, customer.phoneNumber, сontractWithLegal.name " +
            "AS 'contractName', сontractWithLegal.dateOfEnd AS 'endOfContract' " +
            "FROM customer JOIN сontractWithLegal ON customer.сontractWithLegal_ID = сontractWithLegal.ID " +
            "WHERE (customer.ID = #{ID} AND customer.isLegalPerson = 1 AND customer.deleted = 0);")
    LegalPersonView getLegalPersonByID(@Param("ID") int ID);

    @Select("SELECT ID, name, adress, phoneNumber FROM customer WHERE ID = #{ID} AND isLegalPerson = 0 AND deleted = 0;")
    IndividualPersonView getIndividualPersonByID(@Param("ID") int ID);

    @Select("SELECT ID FROM сontractWithLegal WHERE name = #{name} AND dateOfEnd = #{dateOfEnd} AND deleted = 0;")
    int getContractID(@Param("name") String name, @Param("dateOfEnd") String dateOfEnd);

    @Select("SELECT ID FROM сontractWithLegal WHERE name = #{name} AND deleted = 0;")
    int getContractIDByName(@Param("name") String name);

    @Select("SELECT customer.ID, customer.name, customer.adress, customer.phoneNumber, сontractWithLegal.name " +
            "AS 'contractName', сontractWithLegal.dateOfEnd AS 'endOfContract' " +
            "FROM customer JOIN сontractWithLegal ON customer.сontractWithLegal_ID = сontractWithLegal.ID " +
            "WHERE (customer.isLegalPerson = 1 AND customer.deleted = 0" +
            "AND customer.name LIKE ${query});")
    ArrayList<LegalPersonView> searchLegalPerson(@Param("query") String query);

    @Select("SELECT ID, name, adress, phoneNumber FROM customer WHERE isLegalPerson = 0 AND deleted = 0 AND name LIKE ${query};")
    ArrayList<IndividualPersonView> searchIndividualPerson(@Param("query") String query);

    @Select("SELECT ID, name, dateOfEnd FROM сontractWithLegal WHERE deleted = 0 AND name LIKE ${query}")
    ArrayList<Contract> searchContracts(@Param("query") String query);

    @Update("UPDATE сontractWithLegal SET deleted = 1 WHERE ID = #{ID} AND deleted = 0;")
    void deleteContract(@Param("ID") int ID);

    @Update("UPDATE customer SET deleted = 1 WHERE ID = #{ID} AND deleted = 0;")
    void deleteCustomer(@Param("ID") int ID);

    @Update("UPDATE customer SET name = #{name}, adress = #{adress}, phoneNumber = #{phoneNumber} WHERE ID = #{ID} AND deleted = 0;")
    void updateCustomer(
            @Param("ID") int ID,
            @Param("name") String name,
            @Param("adress") String adress,
            @Param("phoneNumber") String phoneNumber
    );

    @Update("UPDATE customer SET сontractWithLegal_ID = #{сontractWithLegal_ID} WHERE ID = #{ID} AND deleted = 0;")
    void updateContractWithLegal(@Param("ID") int ID, @Param("сontractWithLegal_ID") int сontractWithLegal_ID);

    @Update("UPDATE сontractWithLegal SET name = #{name}, dateOfEnd = #{dateOfEnd} WHERE ID = #{ID} AND deleted = 0;")
    void updateContract(@Param("ID") int ID, @Param("name") String name, @Param("dateOfEnd") String dateOfEnd);

    @Insert("INSERT INTO customer (name, adress, isLegalPerson, сontractWithLegal_ID, phoneNumber, deleted) " +
            "VALUES (#{name}, #{adress}, 1, #{сontractWithLegal_ID}, #{phoneNumber}, 0)")
    void insertLegalPerson(
            @Param("name") String name,
            @Param("adress") String adress,
            @Param("сontractWithLegal_ID") int сontractWithLegal_ID,
            @Param("phoneNumber") String phoneNumber
    );

    @Insert("INSERT INTO customer (name, adress, isLegalPerson, сontractWithLegal_ID, phoneNumber, deleted) " +
            "VALUES (#{name}, #{adress}, 0, NULL, #{phoneNumber}, 0)")
    void insertIndividualPerson(
            @Param("name") String name,
            @Param("adress") String adress,
            @Param("phoneNumber") String phoneNumber
    );

    @Insert("INSERT INTO сontractWithLegal (name, dateOfEnd, deleted) VALUES (#{name}, #{dateOfEnd}, 0);")
    void insertContract(@Param("name") String name, @Param("dateOfEnd") String dateOfEnd);
}
