package com.kubernetes.util;
import com.kubernetes.config.MysqlConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import oracle.jdbc.driver.OracleDriver;

/**
 * @author Administrator DB工具类
 */
public class DBUtil {

    private static final String CONN_URL = MysqlConfig.url; // 连接串
    private static final String USER = MysqlConfig.user;
    private static final String PWD = MysqlConfig.password;

    private static final DBUtil UTILS = new DBUtil();

    public static DBUtil getInstance() {
        return UTILS;
    }

    private DBUtil() {

    }

    public Connection getConn() {

        Connection conn = null;

        // 在类路径中，去查找一个，名字为如下字符串的类名
        try {
            Class.forName(MysqlConfig.driver);// oracle驱动入口类
            conn = DriverManager.getConnection(CONN_URL, USER, PWD);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

    public void releaseRes(Connection conn, PreparedStatement pstmt,
                           ResultSet rset) {

        try {
            if (rset != null)
                rset.close();
            if (pstmt != null)
                pstmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
