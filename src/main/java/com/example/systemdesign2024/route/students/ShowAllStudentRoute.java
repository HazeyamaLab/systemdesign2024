package com.example.systemdesign2024.route.students;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.systemdesign2024.control.students.GetAllStudent;
import com.example.systemdesign2024.control.students.GetAllStudentResult;

@Controller
public class ShowAllStudentRoute {
  @RequestMapping(method = RequestMethod.GET, value = "/students/show-all")
  public String execute(Model model) {
    GetAllStudent control = new GetAllStudent();
    GetAllStudentResult getAllStudentResult = control.execute();
    if (getAllStudentResult.isOk() == false) {
      model.addAttribute("error", getAllStudentResult.getMessage());
    } else {
      model.addAttribute("message", getAllStudentResult.getMessage());
    }
    model.addAttribute("students", getAllStudentResult.getStudents());
    return "students/show-all";
  }
}
