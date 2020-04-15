package Controller;

import Bean.Teach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Teacher;
import Bean.Submit;
import Jdbc.TeacherJdbc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeworkSubmitListController {
    @RequestMapping(value = "homework_submit_list")
    public String getSubmitList(@RequestParam("homework_title") String homework_title,
                                Model model,
                                HttpServletRequest request) {

        String teacher_name = (String) request.getSession().getAttribute("teacher_name");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        teacher.setTeacher_name(teacher_name);

        TeacherJdbc teacherJdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
        List<Submit> submit_list = teacherJdbc.QuerySubmit(homework_title, teacher);
        model.addAttribute("submit_list", submit_list);

        return "homework_student";
    }
}
