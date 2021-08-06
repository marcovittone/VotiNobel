package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	
	EsameDAO e;
	int numeroCrediti;
	Set <Esame> migliori;
	double mediaMigliore;

	
	public Model() {
		this.e = new EsameDAO();
		
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		this.numeroCrediti=numeroCrediti;
		this.mediaMigliore=0;
		this.migliori = new HashSet<Esame>();
		ricorsione(new HashSet<Esame>(),this.e.getTuttiEsami(),0);	
		return this.migliori;	
	}
	
	
	private void ricorsione(Set<Esame> parziale,List<Esame> esami, int livello) {
		
		
		for(int i=0;i<esami.size();i++)
			{
					Esame e = esami.get(i);
						if(sommaCrediti(parziale)+e.getCrediti()>this.numeroCrediti)
						{
							double temp= this.calcolaMedia(parziale);
							
							if(temp > this.mediaMigliore)
								{
									this.migliori = new HashSet<Esame>(parziale);
									this.mediaMigliore = temp;
									
								}
						}
					
					else{
							parziale.add(e); //immediate
							esami.remove(i); //slugghish
							ricorsione(parziale,esami,livello+1);
							parziale.remove(e);  //immediate
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
