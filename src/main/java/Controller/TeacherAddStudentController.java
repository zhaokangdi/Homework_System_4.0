package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Teacher;
import Jdbc.TeacherJdbc;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TeacherAddStudentController {
    @RequestMapping(value = "teacher_add_student")
    public String addStudent(@RequestParam("student_name") String student_name,
                              HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacher_jdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        teacher_jdbc.AddStudent(teacher, student_name);

        return "redirect:/teacher_student_list";
    }
}
