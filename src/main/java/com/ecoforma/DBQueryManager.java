package com.ecoforma;

import java.sql.*;

// Менеджер запросов к базе данных (Устаревший метод)
@Deprecated
public class DBQueryManager {
    // Конфигурация подключения
    private final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver"; // Путь к драйверу
    private final String DB_NAME = "ecoformaPenza"; // Название базы данных
    private final String DB_URL = "jdbc:jtds:sqlserver://localhost:1434;databaseName=" + DB_NAME + ";instanceName=MSSQLSERVER"; // Строка подключения
    private final String DB_USER = "ecoforma"; // Имя пользователя, имеющего доступ к базе данных
    private final String DB_PASSWORD = "1"; // Пароль пользователя

    // Подключение драйвера
    @Deprecated
    DBQueryManager() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Запрос с возможностью получения результата
    @Deprecated
    public ResultSet query(String q) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = connection.createStatement();
        return statement.executeQuery(q);
    }

    // Запрос без возможности получения результата
    @Deprecated
    public void execute(String q) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = connection.createStatement();
        statement.executeUpdate(q);
    }
}
