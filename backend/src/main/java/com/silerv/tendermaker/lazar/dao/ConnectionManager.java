package com.silerv.tendermaker.lazar.dao;

/**
 * Created by lazar on 2018. 01. 03..
 */
import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

class ConnectionManager {
    private static final String url = "jdbc:mysql://localhost/tendermaker?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String uname = "root";
    private static final String password = "";
    private static Connection connection;
    private static ConnectionManager instance;

    private ConnectionManager() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        try {
            connection = dataSource.getConnection(uname, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
