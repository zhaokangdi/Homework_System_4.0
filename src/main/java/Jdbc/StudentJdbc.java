package Jdbc;

import Bean.Homework;
import Bean.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentJdbc {
    private static final Logger logger = LoggerFactory.getLogger(StudentJdbc.class);

    public void InsertStudent(Student student) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String sql;
        sql = "INSERT INTO STUDENT VALUES (?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, student.getStudent_name());
            stmt.executeUpdate();

            // 提交事务
            JDBCUtils.commit();
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }
    }

    public List<Homework> QueryHomework(Student student) {
        Connection connection = null;
        PreparedStatement stmt = null;
        List<Homework> homework_list = new ArrayList<>();

        String sql;
        sql = "SELECT * FROM HOMEWORK WHERE HOMEWORK.TEACHER_NAME IN (SELECT TEACH.TEACHER_NAME FROM TEACH WHERE STUDENT_NAME=?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, student.getStudent_name());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
                Homework homework = (Homework) applicationContext.getBean("homework");
                homework.setHomework_title(rs.getString("homework_title"));
                homework.setTeacher_name(rs.getString("teacher_name"));
                homework_list.add(homework);
            }
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }

        return homework_list;
    }

    public boolean QuerySubmit(Student student, String homework_title, String teacher_name) {
        Connection connection = null;
        PreparedStatement stmt = null;
        Integer number = 0;

        String sql;
        sql = "SELECT COUNT(*) FROM SUBMIT WHERE HOMEWORK_TITLE=? AND TEACHER_NAME=? AND STUDENT_NAME=?";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, homework_title);
            stmt.setString(2, teacher_name);
            stmt.setString(3, student.getStudent_name());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                number = rs.getInt(1);
            }
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }

        if(number == 0) {
            return true;
        }else {
            return false;
        }
    }

    public void InsertSubmit(String homework_title, String teacher_name, Student student, String content) {
        Connection connection = null;
        PreparedStatement stmt = null;

        String sql;
        sql = "INSERT INTO SUBMIT VALUES (?,?,?,?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, homework_title);
            stmt.setString(2, teacher_name);
            stmt.setString(3, student.getStudent_name());
            stmt.setString(4, content);
            stmt.executeUpdate();

            // 提交事务
            JDBCUtils.commit();
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }
    }
}
