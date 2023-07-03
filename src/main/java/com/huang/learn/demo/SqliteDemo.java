package com.huang.learn.demo;

import com.huang.learn.util.SqliteUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SqliteDemo
 * @Description 测试使用sqlite数据库
 * @Author huang lang
 * @Date 2023/7/3 17:26
 */
public class SqliteDemo {
    public static void main(String[] args) {
        try {
            SqliteUtil connection = getSqliteConnection("D:\\test\\sqlite\\my-sqlite.db");
            insertUserData(connection);
            List<Map<String, Object>> mapList = selectUserData(connection);
            mapList.forEach(stringObjectMap -> stringObjectMap.forEach((k, v) -> {
                System.out.println(k + v.toString());
            }));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqliteUtil getSqliteConnection(String path) throws IOException, SQLException, ClassNotFoundException {
        File dbFile = new File(path);
        File parentFile = dbFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!dbFile.exists()) {
            dbFile.createNewFile();
        }
        return new SqliteUtil(path);
    }

    public static void createTable(SqliteUtil sqliteUtil) throws SQLException, ClassNotFoundException {
        String createUser = "create table if not exists user(id integer primary key autoincrement,username varchar(20),password varchar(20))";
        // 执行sql
        sqliteUtil.executeUpdate(createUser);
    }

    public static void insertUserData(SqliteUtil sqliteUtil) throws SQLException, ClassNotFoundException {
        String insertUser = "insert into user(username,password) values ('小明','123456'),('李霞','654321')";
        // 执行sql
        sqliteUtil.executeUpdate(insertUser);
    }

    public static List<Map<String, Object>> selectUserData(SqliteUtil sqliteUtil) throws SQLException, ClassNotFoundException {
        String selectUser = "select * from user";
        // 执行sql
        List<Map<String, Object>> mapList = sqliteUtil.executeQuery(selectUser, (rs, index) -> {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("id", rs.getString("id"));
            resultMap.put("username", rs.getString("username"));
            resultMap.put("password", rs.getString("password"));
            return resultMap;
        });
        return mapList;
    }

}
