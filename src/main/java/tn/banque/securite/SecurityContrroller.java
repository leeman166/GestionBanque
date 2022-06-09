package tn.banque.securite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityContrroller {
	
	@RequestMapping(value ="/login")
	public String index(){
		return "login";
	}
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/operation";
	}
	
	@RequestMapping(value = "/403")
	public String accesDenied() {
		return "403";
	}

}
