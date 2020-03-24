package com.ecoforma.db.mappers;

import com.ecoforma.entities.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface StoreMapper {
    @Select("SELECT * FROM store WHERE name = (SELECT department.name FROM department WHERE department.ID = " +
            "(SELECT employee.department_ID FROM employee " +
            "WHERE ID = (SELECT registrationData.employee_ID FROM registrationData WHERE " +
            "(registrationData.login = #{login} AND registrationData.password = #{password})))) AND deleted = 0;")
    Store getStore(@Param("login") String login, @Param("password") String password);

    @Select("SELECT product.ID,  product.name, productCategory.name AS 'category', product.cost FROM product " +
            "JOIN productCategory ON product.productCategory_ID = productCategory.ID WHERE product.deleted = 0;")
    ArrayList<ProductView> getProductView();

    @Select("SELECT product_to_store.ID, product.name, product_to_store.count FROM product JOIN product_to_store ON " +
            "product.ID = product_to_store.product_ID " +
            "WHERE (product_to_store.store_ID = #{store_ID} AND product_to_store.deleted = 0);")
    ArrayList<StoreView> getStoreView(@Param("store_ID") int store_ID);

    @Select("SELECT name FROM productCategory WHERE deleted = 0;")
    String[] getCategories();

    @Select("SELECT name FROM store WHERE (ID != #{currentStoreID} AND deleted = 0);")
    String[] getStoresBesides(@Param("currentStoreID") int currentStoreID);

    @Select("SELECT ID, name, characteristics, productCategory_ID, cost FROM product WHERE ID = #{ID}")
    Product getProductByID(@Param("ID") int ID);

    @Select("SELECT * FROM product_to_store WHERE (ID = #{ID} AND deleted = 0);")
    ProductToStore getProductToStoreByID(@Param("ID") int ID);
}
