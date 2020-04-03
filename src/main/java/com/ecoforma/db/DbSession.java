package com.ecoforma.db;

import com.ecoforma.db.mappers.HRMapper;
import com.ecoforma.db.mappers.SaleMapper;
import com.ecoforma.db.mappers.SignInMapper;
import com.ecoforma.db.mappers.StoreMapper;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class DbSession {
    private static final String DB_DRIVER = "net.sourceforge.jtds.jdbc.Driver"; // Путь к драйверу
    private static final String DB_NAME = "ecoformaPenza"; // Название базы данных
    private static final String DB_URL = "jdbc:sqlserver://localhost:1434;databaseName=" + DB_NAME; // Строка подключения
    private static final String DB_USER = "ecoforma"; // Имя пользователя, имеющего доступ к базе данных
    private static final String DB_PASSWORD = "1"; // Пароль пользователя

    static final SqlSessionFactory sqlSessionFactory;

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setUser(DB_USER);
        dataSource.setURL(DB_URL);
        dataSource.setPassword(DB_PASSWORD);

        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(1);
        config.setDataSource(dataSource);

        HikariDataSource hikariDataSource = new HikariDataSource(config);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, hikariDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(SignInMapper.class);
        configuration.addMapper(HRMapper.class);
        configuration.addMapper(StoreMapper.class);
        configuration.addMapper(SaleMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public static SqlSession startSession() {
        return sqlSessionFactory.openSession();
    }
}
