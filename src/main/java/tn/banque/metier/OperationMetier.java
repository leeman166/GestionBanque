package tn.banque.metier;

import tn.banque.Entities.Compte;
import tn.banque.Entities.Operation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;


public interface OperationMetier {

	public Compte consulterCompte(String codeCpte);

    // Operation versement
    public boolean verser( String code, double montant);

    // Retirer
    public boolean retirer( String code, double montant);

    // Pour le virement on a besoin de deux comptes(Emetteur - Recepteur)

    public boolean virement( String cpte1, String cpte2, double montant);

    // Page operation contient compte , pages , nbre de ligne

    public PageOperation getOperations( String codeCompte, int page, int size );
    
    public Page<Operation> listOperation(String codecpte, int page, int size);
    
    public List<Compte> ConsulterAllComptes();
    
    public Optional<Compte> consulterCompteM1(String codeCpte);
    
   

}
