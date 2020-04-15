package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import Bean.Teacher;
import Bean.Homework;
import Jdbc.TeacherJdbc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TeacherHomeworkListController {
    @RequestMapping(value = "teacher_homework_list")
    public String getHomeworkList(Model model,
                                  HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacher_jdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        List<Homework> homework_list = teacher_jdbc.QueryHomework(teacher);
        model.addAttribute("homework_list", homework_list);

        return "teacher_homework";
    }
}
