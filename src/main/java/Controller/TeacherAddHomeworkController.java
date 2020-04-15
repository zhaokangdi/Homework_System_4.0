package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Bean.Teacher;
import Jdbc.TeacherJdbc;

import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class TeacherAddHomeworkController {
    @RequestMapping(value = "teacher_add_homework")
    public String addHomework(@RequestParam("homework_title") String homework_title,
                              HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacher_jdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        teacher_jdbc.AddHomework(teacher, homework_title);

        return "redirect:/teacher_homework_list";
    }
}
