package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Teacher;
import Jdbc.TeacherJdbc;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TeacherHomeworkContentController {
    @RequestMapping("homework_content")
    public String getContent(@RequestParam("homework_title") String homework_title,
                             @RequestParam("student_name") String student_name,
                             Model model,
                             HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacherJdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        String content = teacherJdbc.CheckContent(homework_title, teacher, student_name);
        model.addAttribute("homework_title", homework_title);
        model.addAttribute("student_name", student_name);
        model.addAttribute("content", content);

        return "homework_content";
    }
}
