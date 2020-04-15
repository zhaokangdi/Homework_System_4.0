package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Bean.Student;
import Bean.Homework;
import Jdbc.StudentJdbc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StudentHomeworkListController {
    @RequestMapping(value = "student_homework_list")
    public String getHomework(HttpServletRequest request) {

        String student_name = (String) request.getSession().getAttribute("student_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) applicationContext.getBean("student");
        student.setStudent_name(student_name);

        StudentJdbc studentJdbc = (StudentJdbc) applicationContext.getBean("student_jdbc");
        List<Homework> homework_list = studentJdbc.QueryHomework(student);
        request.setAttribute("homework_list", homework_list);

        return "student_submit";
    }
}
