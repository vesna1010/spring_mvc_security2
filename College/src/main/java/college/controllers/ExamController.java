package college.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import college.model.Exam;
import college.model.Professor;
import college.model.StudyProgram;
import college.model.Subject;
import college.service.HibernateService;
import college.service.impl.ExamService;

@Controller
public class ExamController {
	
	@Autowired
	private ExamService examService;
		
	@Autowired
	private HibernateService<StudyProgram> studyProgramService;
	
	@Autowired
	private HibernateService<Subject> subjectService;
	
	@Autowired
	private HibernateService<Professor> professorService;
	
	//creates form for new exam
	@RequestMapping("/exams/save")
	public String showForm(Model model, @RequestParam String id){
		model.addAttribute("exam", new Exam());
		model.addAttribute("studyProgram", studyProgramService.findOne(id));
		return "examForm";
	}
	
	//save or update the exam
	@RequestMapping(value="/exams/save", method=RequestMethod.POST)
	public String saveOrSearchExams(Model model, @Valid @ModelAttribute("exam") Exam exam, BindingResult result){
			if(!result.hasErrors()){
				examService.save(exam);
				return "redirect:/exams/save?id="+exam.getSubject().getStudyProgram().getId();
		}
		return "examForm";
	}
	
	//returns exam with specified ID
	@RequestMapping("/exams/find/{id}")
	public String showExam(Model model, @PathVariable Long id){
		model.addAttribute("exam", examService.findOne(id));
		model.addAttribute("studyProgram", examService.findOne(id).getSubject().getStudyProgram());
		return "examForm";
	}
	
	//deletes exam with specified ID
	@RequestMapping("/exams/delete/{id}")
	public String deleteExam(Model model, @PathVariable Long id){
		Exam exam=examService.findOne(id);
		examService.delete(id);
		model.addAttribute("exams", examService.findByObjects(exam.getProfessor(), exam.getSubject(), exam.getDate()));
		return "listExams";
	}
	
	//creates form for search list exams
	@RequestMapping("/exams/search")
	public String searchExams(Model model){
		model.addAttribute("exam", new Exam());
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("professors", professorService.findAll());
		return "searchExamsForm";
	}
	
	//searches list exams for professor, subject and date
	@RequestMapping(value="/exams/search", method=RequestMethod.POST)
	public String searchedExams(Model model, @Valid @ModelAttribute("exam") Exam exam, BindingResult result){
			if(!result.hasFieldErrors("professor") && !result.hasFieldErrors("subject")
					&& !result.hasFieldErrors("date")){
				model.addAttribute("exams", examService.findByObjects(exam.getProfessor(), exam.getSubject(), exam.getDate()));
				return "listExams";
		}
		model.addAttribute("subjects", subjectService.findAll());
		model.addAttribute("professors", professorService.findAll());
		return "searchExamsForm";
	}
	
	
}
