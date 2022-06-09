package tn.banque.web;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tn.banque.Entities.Client;
import tn.banque.Entities.Compte;
import tn.banque.Entities.CompteCourant;
import tn.banque.Entities.CompteEpargne;
import tn.banque.Entities.Operation;
import tn.banque.dao.ClientRepository;
import tn.banque.dao.CompteRepository;
import tn.banque.dao.OperationRepository;
import tn.banque.metier.OperationMetier;
import tn.banque.metier.OperationMetierImpl;
import tn.banque.metier.PageOperation;

@Controller
public class BanqueController {

	@Autowired
	private OperationMetier banqueMetier;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationMetierImpl operationMetierImpl;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	@RequestMapping("/operation")
	public String index(){
		return "comptes";
	}
	
	@RequestMapping("/consulter")
	public String consulter(Model model, String codeCompte, 
			@RequestParam(name="page", defaultValue = "0")int page, 
			@RequestParam(name="size", defaultValue = "5")int size){
		model.addAttribute("codeCompte", codeCompte);
		try {
			Optional<Compte> cp = banqueMetier.consulterCompteM1(codeCompte);
			
			
			Compte c = banqueMetier.consulterCompte(codeCompte);
			Page<Operation> pageOperation = banqueMetier.listOperation(codeCompte, page, size);
			
			//Page<Operation> pageOperation = operationRepository.findAll(PageRequest.of(page, size));
			int[] pages = new int[pageOperation.getTotalPages()];
			model.addAttribute("listOperation", pageOperation.getContent());
			model.addAttribute("pages", pages);
			model.addAttribute("compte",c);	
			model.addAttribute("comptes", cp);
			
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error", e);
		}		
		return "comptes";
		
	}
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, 
			String codeCompte, double montant, String codeCompte2) {

		try {
			if (typeOperation.equals("VERS")){
				banqueMetier.verser(codeCompte, montant);
			}
			else if(typeOperation.equals("RETR")){
				banqueMetier.retirer(codeCompte, montant);		
			}
			else if(typeOperation.equals("VIR")) {
				banqueMetier.virement(codeCompte, codeCompte2, montant);
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error", e);
			return "redirect:/consulter?codeCompte="+codeCompte+"&error="+e.getMessage();
		}
		return "redirect:/consulter?codeCompte="+codeCompte;
	}
	
	@RequestMapping("/AddCompte")
	public String addCompte(){
		
		return "AddCompte";
	}
	
	@RequestMapping(value="/saveCompte", method=RequestMethod.POST)
	public String saveCompte(String codeCompte, String mailCompte,
			String nomCompte, String soldeCompte, String typeCompte){
		
		double solde = Double.parseDouble(soldeCompte);
		Client cl = clientRepository.save(new Client(nomCompte,mailCompte));
		
		if(typeCompte.equals("CC")) {
			Compte cp1 = compteRepository.save(new CompteCourant(codeCompte, new Date(), solde, cl, 6000));
		}
		else
		{
			Compte cp1 = compteRepository.save(new CompteEpargne(codeCompte, new Date(), solde, cl, 5.5));

		}

		return "redirect:/consulter?codeCompte="+codeCompte;
	}
	
	@RequestMapping("/ListComptes")
	public String ListComptes(){
		
		return "ListComptes";
	}
	@RequestMapping(value="/ConsulterAllComptes",method=RequestMethod.GET)
	public List<Compte> ConsulterAllComptes(Model model) {
		List<Compte> Comptes = banqueMetier.ConsulterAllComptes();
		model.addAttribute("Comptes", Comptes);
		return Comptes;
	}
	
	
}
