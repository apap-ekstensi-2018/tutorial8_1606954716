package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    //@RequestMapping("/")
    /*public String index ()
    {
        return "index";
    }*/


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    @RequestMapping("/student/cari")
    public String cari(){
        return "cari";
    }

    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping(path="/student/viewall", method=RequestMethod.GET)
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }

    @RequestMapping(path="/student/viewData", method=RequestMethod.GET)
    public ResponseEntity<List<StudentModel>> returnDataTables (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        //model.addAttribute ("students", students);

        return new ResponseEntity<List<StudentModel>>(students, HttpStatus.OK);
    }

    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent(npm);
        if (student != null){
            studentDAO.deleteStudent(npm);
            return "delete";
        }
        else{
            model.addAttribute("npm", npm);
            return "not-found";

        }
    }

    @RequestMapping("/student/update/{npm}")
    public String update (Model model,
                          @PathVariable(value = "npm") String npm)
    {

        StudentModel student = studentDAO.selectStudent (npm);
        if (student != null) {
            model.addAttribute ("student", student);
            return "form-update";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    @RequestMapping(value = "/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit (@ModelAttribute("student") StudentModel student, ModelMap model)
    {
        if(student.getName() == null || student.getName() == "") {
            return "error";
        }

        StudentModel studentValid = studentDAO.selectStudent(student.getNpm());
        if (studentValid != null) {
            studentDAO.updateStudent(student.getNpm(), student.getName(), student.getGpa());
            return "success-update";
        }else {
            model.addAttribute ("npm", student.getNpm());
            return "not-found";
        }
    }

    @RequestMapping(value = "/student/update/submit", method = RequestMethod.GET)
    public String updateSubmitGET (@ModelAttribute("student") StudentModel student, ModelMap model)
    {
        if(student.getName() == null || student.getName() == "") {
            return "error";
        }

        StudentModel studentValid = studentDAO.selectStudent(student.getNpm());
        if (studentValid != null) {
            studentDAO.updateStudent(student.getNpm(), student.getName(), student.getGpa());
            return "success-update";
        }else {
            model.addAttribute ("npm", student.getNpm());
            return "not-found";
        }
    }
}
