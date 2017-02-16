package college.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import college.model.User;
import college.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//returns all enabled users
	@RequestMapping("/users/")
	public String users(Model model){
		model.addAttribute("users", userService.findUsers());
		return "users";
	}
	
	//creates form for new user
	@RequestMapping("/users/save")
	public String addUser(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("listRoles", userService.findRoles());
		return "userForm";
	}
	
	//save new user
	@RequestMapping(value="/users/save", method=RequestMethod.POST)
	public String addUser(User user, Model model){
		if(userService.findUser(user.getUsername())!=null){
			model.addAttribute("message", "Username exist!");
		}else{
			userService.saveOrUpdate(user);
			model.addAttribute("user", new User());
			model.addAttribute("message", "Your data has been successfuly saved!");
		}
		model.addAttribute("listRoles", userService.findRoles());
		return "userForm";
	}
	
	//deletes user with the specified username
	@RequestMapping("/users/delete/{username}")
	public String deleteUser(Model model, @PathVariable String username){
		userService.deleteUser(username);
		return "redirect:/users/";
	}
	
	//disables user with the specified username
	@RequestMapping("/users/disable/{username}")
	public String disableUser(Model model, @PathVariable String username){
		userService.disableUser(username);
		return "redirect:/users/";
	}
	
	//creates form for update password
	@RequestMapping("/password/save")
	public String changePassword(Model model, Principal principal){
		model.addAttribute("listRoles", userService.findRoles());
		model.addAttribute("user", userService.findUser(principal.getName()));
		return "password";
	}
	
	//logged user changes the password
	@RequestMapping(value="/password/save", method=RequestMethod.POST)
	public String changePassword(User user, Model model, Principal principal){
		userService.saveOrUpdate(user);
		model.addAttribute("message", "Password changed.");
		model.addAttribute("user", userService.findUser(principal.getName()));
		model.addAttribute("listRoles", userService.findRoles());
		return "password";
	}
	
}
