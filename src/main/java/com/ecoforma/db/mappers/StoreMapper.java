package com.ecoforma.db.mappers;

import com.ecoforma.db.entities.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("SELECT product.ID,  product.name, productCategory.name AS 'category', product.cost FROM product " +
            "JOIN productCategory ON product.productCategory_ID = productCategory.ID WHERE ${column} LIKE ${query} AND product.deleted = 0;")
    ArrayList<ProductView> searchProductView(@Param("column") String column, @Param("query") String query);

    @Select("SELECT product_to_store.ID, product.name, product_to_store.count FROM product JOIN product_to_store ON " +
            "product.ID = product_to_store.product_ID WHERE " +
            "(product_to_store.store_ID = #{store_ID} AND product.name LIKE ${name} AND product_to_store.deleted = 0);")
    ArrayList<StoreView> searchStoreView(@Param("store_ID") int store_ID, @Param("name") String name);

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

    @Select("SELECT * FROM product WHERE name = #{name}")
    ArrayList<Product> getProductsByName(@Param("name") String name);

    @Select("SELECT * FROM product_to_store WHERE (product_ID = #{product_ID} AND store_ID = #{store_ID} AND deleted = 0);")
    ArrayList<ProductToStore> getProductToStore(@Param("product_ID") int product_ID, @Param("store_ID") int store_ID);

    @Select("SELECT ID FROM store WHERE name = #{name} AND deleted = 0;")
    int getStoreIDByName(@Param("name") String name);

    @Insert("INSERT INTO product (name, characteristics, cost, productCategory_ID, deleted) " +
            "VALUES (#{name}, #{characteristics}, #{cost}, #{productCategory_ID}, 0);")
    void insertProduct(
            @Param("name") String name,
            @Param("characteristics") String characteristics,
            @Param("cost") int cost,
            @Param("productCategory_ID") int productCategory_ID
    );

    @Insert("INSERT INTO product_to_store (store_ID, product_ID, count, deleted) " +
            "VALUES (#{store_ID}, #{product_ID}, #{count}, 0);")
    void insertProductToStore(
            @Param("store_ID") int store_ID,
            @Param("product_ID") int product_ID,
            @Param("count") int count
    );

    @Update("UPDATE product SET name = #{name}, characteristics = #{characteristics}, " +
            "cost = #{cost}, productCategory_ID = #{productCategory_ID} WHERE ID = #{ID} AND deleted = 0;")
    void updateProduct(
            @Param("ID") int ID,
            @Param("name") String name,
            @Param("characteristics") String characteristics,
            @Param("cost") int cost,
            @Param("productCategory_ID") int productCategory_ID
    );

    @Update("UPDATE product SET deleted = 1 WHERE ID = #{ID} AND deleted = 0;")
    void deleteProduct(@Param("ID") int ID);

    @Update("UPDATE product_to_store SET deleted = 1 WHERE ID = #{ID} AND deleted = 0;")
    void deleteProductFromStore(@Param("ID") int ID);

    @Update("UPDATE product_to_store SET count = #{count} WHERE ID = #{ID} AND deleted = 0;")
    void updateCount(@Param("ID") int ID, @Param("count") int count);

    @Update("UPDATE product_to_store SET deleted = 1 WHERE product_ID = #{product_ID};")
    void deleteProductFromAllStores(@Param("product_ID") int product_ID);
}
