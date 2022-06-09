package tn.banque;

import java.util.Date;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tn.banque.Entities.Client;
import tn.banque.Entities.Compte;
import tn.banque.Entities.CompteCourant;
import tn.banque.Entities.CompteEpargne;
import tn.banque.Entities.Retrait;
import tn.banque.Entities.Versement;
import tn.banque.dao.ClientRepository;
import tn.banque.dao.CompteRepository;
import tn.banque.dao.OperationRepository;
import tn.banque.metier.OperationMetier;
import tn.banque.metier.OperationMetierImpl;

@SpringBootApplication
public class ProjetBanqueApplication implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private OperationMetierImpl operationMetierImp;
	@Autowired
	private OperationMetier banqueMetier;

	public static void main(String[] args) {
		SpringApplication.run(ProjetBanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// TODO Auto-generated method stub
		Client c1 = clientRepository.save(new Client("leeman","leeman@gmail"));
		Client c2 = clientRepository.save(new Client("Alimou","alimou@gmail"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 9000, c1, 6000));
		Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 6000, c2, 5.5));
		
		operationRepository.save(new Retrait(new Date(), 100, cp1));
		operationRepository.save(new Retrait(new Date(), 200, cp1));
		
		
		operationRepository.save(new Retrait(new Date(), 300, cp2));
		operationRepository.save(new Retrait(new Date(), 400, cp2));
		
		boolean test = operationMetierImp.virement("c2", "c1", 250);
		
		boolean test1 = operationMetierImp.retirer("c1", 50);
		
		
		
		if (test == true )
			System.out.print("Test reussi");
		else
			System.out.print("Failed");
		
		if (test == true )
			System.out.print("Test reussi");
		else
			System.out.print("Failed");
		
		//System.out.println(Compte.class.getSimpleName());
	
		
	}

}
