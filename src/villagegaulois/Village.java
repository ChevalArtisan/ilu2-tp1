package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			this.nbEtals=nbEtals;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if (indiceEtal>0 || indiceEtal<nbEtals) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		private int trouverEtalLibre() {
			int i=0;int place=-1;
			while (i<nbEtals && place!=-1) {
				if (!etals[i].isEtalOccupe()) {
					place=i;
				}
			} 
			return place;
		}
		
		private Etal[] trouverEtals(String produit) {
			; int nbEtalsProduit=0;
			for (int i = 0; i <nbEtals; i++) {
				if (etals[i].contientProduit(produit)&& etals[i].isEtalOccupe()) {
					nbEtalsProduit++;
					}
				}
			Etal[] etalsProduit =new Etal[nbEtalsProduit]; int j=0;
			
			for (int i1 = 0; i1 < nbEtalsProduit; i1++) {
				if (etals[i1].contientProduit(produit)) {
					etalsProduit[j++]=etals[i1];
				}
			}
			return etalsProduit;
			
			}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			int i=0; Boolean recherche=true; Etal etal=null;
			while (i<nbEtals && recherche ) {
				if (etals[i].getVendeur()==gaulois) {
					etal=etals[i]; recherche=false;
				}
			}
			return etal;
		}
		private String afficherMarche() {
			int nbEtalVide=0; StringBuilder message=new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					message.append(etals[i].afficherEtal());
					
				} else {
					nbEtalVide++;
				}
			}
			message.append("Il reste " +nbEtalVide + " étals non utilisés dans le marché.\n");
			return message.toString();
		}
	}
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder message =new StringBuilder();
		message.append(vendeur.getNom()+" cherche un endroit pour vendre"+nbProduit+" "+produit);
		int place=marche.trouverEtalLibre();
		if (place==-1) {
			message.append("Le marché n'as plus de place"+ vendeur.getNom()+" devra revenir demain");
		}else {
			marche.utiliserEtal(place, vendeur, produit, nbProduit);
			message.append("Le vendeur"+vendeur.getNom()+" vend des "+produit+" a l'etal n"+place);
		}
		return message.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder message =new StringBuilder();
		message.append("Les vendeurs qui proposent des "+produit+" sont :\n");
		Etal[] etalproduit=marche.trouverEtals(produit);
		for (int i = 0; i < etalproduit.length; i++) {
			message.append("-"+etalproduit[i].getVendeur().getNom());
		}
		return message.toString();
	}
	
	
}