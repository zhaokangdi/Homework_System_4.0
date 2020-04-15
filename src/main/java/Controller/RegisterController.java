package Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Bean.Student;
import Bean.Teacher;
import Jdbc.TeacherJdbc;
import Jdbc.StudentJdbc;

@Controller
public class RegisterController {
    @RequestMapping("register")
    public String Register(@RequestParam("register_name") String register_name,
                           @RequestParam("register_identity") String register_identity) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        if(register_identity.equals("学生")) {
            Student student = (Student) applicationContext.getBean("student");
            student.setStudent_name(register_name);

            StudentJdbc studentJdbc = (StudentJdbc) applicationContext.getBean("student_jdbc");
            studentJdbc.InsertStudent(student);
        }else {
            Teacher teacher = (Teacher) applicationContext.getBean("teacher");
            teacher.setTeacher_name(register_name);

            TeacherJdbc teacherJdbc = (TeacherJdbc) applicationContext.getBean("teacher_jdbc");
            teacherJdbc.AddTeacher(teacher);
        }

        return "index";
    }
}
