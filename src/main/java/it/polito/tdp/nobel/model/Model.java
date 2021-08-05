package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	
	EsameDAO e;
	Set<Esame> esamiSet;  //risultati, depositabili sia in List che in Set
	List<Esame> esamiList;
	int numeroCrediti;
	Set <Set<Esame>> totale;

	
	public Model() {
		this.e = new EsameDAO();
		this.esamiSet= new HashSet<Esame>();
		this.esamiList= new ArrayList<Esame>();
		
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		this.numeroCrediti=numeroCrediti;
		this.totale = new HashSet<Set<Esame>>();
		ricorsione(new HashSet<Esame>(),this.e.getTuttiEsami(),0);
		
		double max=0;
		Set<Esame> top =null;
		
		
		for(Set<Esame> ss: this.totale)
		{
			double media = this.calcolaMedia(ss);
			System.out.println(media);
			if(media>=max)
			{
				max = media;
				top= ss;
				
				
			}
		}
		
//		for(Set<Esame> ss: this.totale)
//		{
//
//			System.out.println(ss);
//		}
//		
		
		return top;	
	}
	
	
	private void ricorsione(Set<Esame> parziale,List<Esame> esami, int livello) {
		
		
		for(int i=0;i<esami.size();i++)
			{
					Esame e = esami.get(i);
						if(sommaCrediti(parziale)+e.getCrediti()>this.numeroCrediti)
						{
							this.totale.add(new HashSet<Esame>(parziale));
						}
					
					else{
							parziale.add(e);
							esami.remove(i);
							ricorsione(parziale,esami,livello+1);
							parziale.remove(e);
							esami.add(i, e);
						}
					
			}
		}
	

	
	public double calcolaMedia(Set<Esame> esami) {
		
		double crediti = 0;
		double somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
