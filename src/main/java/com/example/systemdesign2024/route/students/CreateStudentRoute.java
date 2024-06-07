package com.example.systemdesign2024.route.students;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.systemdesign2024.control.students.CreateStudent;
import com.example.systemdesign2024.control.students.CreateStudentInput;
import com.example.systemdesign2024.control.students.CreateStudentResult;

@Controller
public class CreateStudentRoute {
  @RequestMapping(method = RequestMethod.POST, value = "/students/create")
  public String execute(@RequestParam(name = "id") String id, @RequestParam(name = "name") String name, Model model, RedirectAttributes redirectAttributes) {
    CreateStudent control = new CreateStudent();
    CreateStudentInput createStudentInput = new CreateStudentInput(id, name);
    CreateStudentResult createStudentResult = control.execute(createStudentInput);
    if (createStudentResult.isOk() == false) {
      redirectAttributes.addFlashAttribute("errorFromPrev", createStudentResult.getMessage());
      redirectAttributes.addFlashAttribute("lastInputId", id);
      redirectAttributes.addFlashAttribute("lastInputName", name);
    } else {
      redirectAttributes.addFlashAttribute("messageFromPrev", createStudentResult.getMessage());
    }
    return "redirect:/students/show-all";
  }
}
