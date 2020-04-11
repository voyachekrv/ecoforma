package com.ecoforma.db.mappers;

import com.ecoforma.db.entities.Employee;
import com.ecoforma.db.entities.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface DeliveryMapper {
    @Select("SELECT delivery.ID, delivery.order_ID AS 'orderID', product.name AS 'productName', orders.count,  " +
            "store.name AS 'storeName', store.adress AS 'addressFrom', customer.adress AS 'addressTo', " +
            "customer.name 'customerName', customer.phoneNumber, delivery.status, delivery.employee_ID AS 'employeeID' " +
            "FROM delivery " +
            "JOIN orders ON  delivery.order_ID = orders.ID " +
            "JOIN store ON orders.store_ID = store.ID " +
            "JOIN customer ON orders.customer_ID = customer.ID " +
            "JOIN product ON orders.product_ID = product.ID;")
    ArrayList<Order> getAllOrders();

    @Select("SELECT ID, name, phoneNumber FROM employee WHERE (post_ID = (SELECT ID FROM post WHERE name = 'Водитель') AND deleted = 0);")
    ArrayList<Employee> getAllDrivers();

    @Select("SELECT ID, name, phoneNumber FROM employee WHERE " +
            "(post_ID = (SELECT ID FROM post WHERE name = 'Водитель') AND deleted = 0 AND ID = #{ID});")
    ArrayList<Employee> getDriverByID(@Param("ID") int ID);

    @Select("SELECT ID, name, phoneNumber FROM employee WHERE " +
            "(post_ID = (SELECT ID FROM post WHERE name = 'Водитель') AND deleted = 0 AND name LIKE ${name});")
    ArrayList<Employee> getDriverByName(@Param("name") String name);

    @Update("UPDATE delivery SET employee_ID = #{employee_ID}, status = #{status} WHERE order_ID = #{order_ID}")
    void setDriverOnDelivery(@Param("order_ID") int order_ID, @Param("employee_ID") int employee_ID, @Param("status") String status);

    @Update("UPDATE delivery SET status = #{status} WHERE order_ID = #{order_ID}")
    void setStatus(@Param("order_ID") int order_ID, @Param("status") String status);
}
