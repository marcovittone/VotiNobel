package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	
	EsameDAO e;
	int numeroCrediti;
	List<Esame> tuttiEsami;
	Set <Esame> migliori;
	double mediaMigliore;

	
	public Model() {
		this.e = new EsameDAO();
		
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		this.numeroCrediti=numeroCrediti;
		this.mediaMigliore=0;
		this.migliori = new HashSet<Esame>();
		this.tuttiEsami = this.e.getTuttiEsami();
		ricorsione(new HashSet<Esame>(),0);	
		return this.migliori;	
	}
	
	
	private void ricorsione(Set<Esame> parziale, int livello) {
		
		
		for(int i=0;i<this.tuttiEsami.size();i++)
			{
				//Esame e = this.tuttiEsami.get(i);
			
				System.out.println(parziale.contains(this.tuttiEsami.get(i)));
				
				if(!parziale.contains(this.tuttiEsami.get(i))); //immediate
				{
						if(sommaCrediti(parziale)+this.tuttiEsami.get(i).getCrediti()>this.numeroCrediti || livello == this.tuttiEsami.size())
						{
							double temp= this.calcolaMedia(parziale);
							
							if(temp > this.mediaMigliore)
								{
									this.migliori = new HashSet<Esame>(parziale);
									this.mediaMigliore = temp;
									
								}
						}
					
					else{
							parziale.add(this.tuttiEsami.get(i)); //immediate
							ricorsione(parziale,livello+1);
							parziale.remove(this.tuttiEsami.get(i));  //immediate
						}
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
