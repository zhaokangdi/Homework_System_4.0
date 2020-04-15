package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Teacher;
import Bean.Student;
import Bean.Homework;
import Jdbc.TeacherJdbc;
import Jdbc.StudentJdbc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @RequestMapping(value = "index")
    public String Login(@RequestParam("login_name") String login_name,
                        @RequestParam("login_identity") String login_identity,
                        Model model,
                        HttpServletRequest request) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        if(login_identity.equals("学生")) {
            request.getSession().setAttribute("student_name", login_name);

            Student student = (Student) applicationContext.getBean("student");
            student.setStudent_name(login_name);

            StudentJdbc studentJdbc = (StudentJdbc) applicationContext.getBean("student_jdbc");
            List<Homework> homework_list = studentJdbc.QueryHomework(student);
            request.setAttribute("homework_list", homework_list);

            return "student_submit";
        }else {
            request.getSession().setAttribute("teacher_name", login_name);

            Teacher teacher = (Teacher) applicationContext.getBean("teacher");
            teacher.setTeacher_name(login_name);

            TeacherJdbc teacherJdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
            List<Homework> homework_list = teacherJdbc.QueryHomework(teacher);
            model.addAttribute("homework_list", homework_list);

            return "teacher_homework";
        }
    }
}
