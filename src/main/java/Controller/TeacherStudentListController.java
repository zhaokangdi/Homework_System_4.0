package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import Bean.Teacher;
import Bean.Student;
import Jdbc.TeacherJdbc;

@Controller
public class TeacherStudentListController {
    @RequestMapping(value = "teacher_student_list")
    public String getStudent(Model model,
                                  HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacher_jdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        List<Student> student_list = teacher_jdbc.QueryStudent(teacher);
        model.addAttribute("student_list", student_list);

        return "teacher_student";
    }
}
