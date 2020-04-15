package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Student;
import Jdbc.StudentJdbc;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentSubmitHomeworkController {
    @RequestMapping("student_submit")
    public String submitHomework(@RequestParam("homework_title") String homework_title,
                               @RequestParam("teacher_name") String teacher_name,
                               @RequestParam("content") String content,
                               HttpServletRequest request) {

        String student_name = (String) request.getSession().getAttribute("student_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) applicationContext.getBean("student");
        student.setStudent_name(student_name);

        StudentJdbc studentJdbc = (StudentJdbc) applicationContext.getBean("student_jdbc");
        studentJdbc.InsertSubmit(homework_title, teacher_name, student, content);

        return "redirect:/student_homework_list";
    }
}
