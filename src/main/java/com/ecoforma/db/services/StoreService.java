package com.ecoforma.db.services;

import com.ecoforma.db.DbSession;
import com.ecoforma.db.entities.*;
import com.ecoforma.db.mappers.StoreMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

public class StoreService {
    public Store getStore(String login, String password) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getStore(login, password);
        }
    }

    public String[] getCategories() {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getCategories();
        }
    }

    public String[] getStoresBesides(int currentStoreID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getStoresBesides(currentStoreID);
        }
    }

    public ArrayList<ProductView> getProductView() {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getProductView();
        }
    }

    public ArrayList<StoreView> getStoreView(int store_ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getStoreView(store_ID);
        }
    }

    public Product getProductByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getProductByID(ID);
        }
    }

    public ProductToStore getProductToStoreByID(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getProductToStoreByID(ID);
        }
    }

    public ArrayList<Product> getProductsByName(String name) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getProductsByName(name);
        }
    }

    public void insertProduct(String name, String characteristics, int cost, int productCategory_ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.insertProduct(name, characteristics, cost, productCategory_ID);
            session.commit();
        }
    }

    public void updateProduct(int ID, String name, String characteristics, int cost, int productCategory_ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.updateProduct(ID, name, characteristics, cost, productCategory_ID);
            session.commit();
        }
    }

    public void deleteProduct(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.deleteProduct(ID);
            session.commit();
        }
    }

    public ArrayList<ProductToStore> getProductToStore(int product_ID, int store_ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getProductToStore(product_ID, store_ID);
        }
    }

    public void insertProductToStore(int store_ID, int product_ID, int count) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.insertProductToStore(store_ID, product_ID, count);
            session.commit();
        }
    }

    public void deleteProductFromStore(int ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.deleteProductFromStore(ID);
            session.commit();
        }
    }

    public void updateCount(int ID, int count) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.updateCount(ID, count);
            session.commit();
        }
    }

    public int getStoreIDByName(String name) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.getStoreIDByName(name);
        }
    }

    public ArrayList<ProductView> searchProductView(String column, String query) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.searchProductView(column, query);
        }
    }

    public ArrayList<StoreView> searchStoreView(int store_ID, String name) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            return mapper.searchStoreView(store_ID, name);
        }
    }

    public void deleteProductFromAllStores(int product_ID) {
        try (SqlSession session = DbSession.startSession()) {
            StoreMapper mapper = session.getMapper(StoreMapper.class);
            mapper.deleteProductFromAllStores(product_ID);
            session.commit();
        }
    }
}
