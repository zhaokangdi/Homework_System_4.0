package Jdbc;

import Bean.Homework;
import Bean.Student;
import Bean.Submit;
import Bean.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherJdbc {
    private static final Logger logger = LoggerFactory.getLogger(TeacherJdbc.class);

    public void AddTeacher(Teacher teacher) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String sql;
        sql = "INSERT INTO TEACHER VALUES (?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, teacher.getTeacher_name());
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

    public List<Homework> QueryHomework(Teacher teacher) {
        Connection connection = null;
        PreparedStatement stmt = null;
        List<Homework> homework_list = new ArrayList<>();
        String sql;
        sql = "SELECT * FROM HOMEWORK WHERE TEACHER_NAME=?";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, teacher.getTeacher_name());

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

    public void AddHomework(Teacher teacher, String homework_title) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String sql;
        sql = "INSERT INTO HOMEWORK VALUES (?,?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, homework_title);
            stmt.setString(2, teacher.getTeacher_name());
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

    public void AddStudent(Teacher teacher, String student_name) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String sql;
        sql = "INSERT INTO TEACH VALUES (?,?)";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, teacher.getTeacher_name());
            stmt.setString(2, student_name);
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

    public List<Student> QueryStudent(Teacher teacher) {
        Connection connection = null;
        PreparedStatement stmt = null;
        List<Student> student_list = new ArrayList<>();

        String sql;
        sql = "SELECT * FROM TEACH WHERE TEACHER_NAME=?";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, teacher.getTeacher_name());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
                Student student = (Student) applicationContext.getBean("student");
                student.setStudent_name(rs.getString("student_name"));
                student.setStudent_name(rs.getString("student_name"));
                student_list.add(student);
            }
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }

        return student_list;
    }

    public List<Submit> QuerySubmit(String homework_title, Teacher teacher) {
        Connection connection = null;
        PreparedStatement stmt = null;
        List<Submit> submit_list = new ArrayList<>();

        String sql;
        sql = "SELECT * FROM SUBMIT WHERE HOMEWORK_TITLE=? and TEACHER_NAME=?";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, homework_title);
            stmt.setString(2, teacher.getTeacher_name());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
                Submit submit = (Submit) applicationContext.getBean("submit");;
                submit.setHomework_title(homework_title);
                submit.setTeacher_name(teacher.getTeacher_name());
                submit.setStudent_name(rs.getString("student_name"));
                submit.setContent(rs.getString("content"));
                submit_list.add(submit);
            }
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }

        return submit_list;
    }

    public String CheckContent(String homework_title, Teacher teacher, String student_name) {
        Connection connection = null;
        PreparedStatement stmt = null;
        String content = null;

        String sql;
        sql = "SELECT CONTENT FROM SUBMIT WHERE HOMEWORK_TITLE=? and TEACHER_NAME=? and STUDENT_NAME=?";

        try {
            // 获得连接
            connection = JDBCUtils.getConnection();
            // 开启事务设置非自动提交
            JDBCUtils.startTransaction();
            // 获得Statement对象
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, homework_title);
            stmt.setString(2, teacher.getTeacher_name());
            stmt.setString(3, student_name);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                content = rs.getString(1);
            }
        } catch(Exception e) {
            JDBCUtils.rollback();
        } finally {
            // 释放资源
            JDBCUtils.release(connection, stmt, null);
        }

        return content;
    }
}
