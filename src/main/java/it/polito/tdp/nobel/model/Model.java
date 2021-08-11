package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	
	private EsameDAO ed;
	private List<Esame> tuttiEsami;
	private int numeroCrediti;
	private double mediaMigliore;
	private Set<Esame> migliori;
	
	
	public Model() {
		this.ed = new EsameDAO();
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		this.tuttiEsami= this.ed.getTuttiEsami();
		this.numeroCrediti= numeroCrediti;
		this.mediaMigliore=0;
		this.migliori = new HashSet<Esame>();
		this.permuta2(new HashSet<Esame>(), 0);
		
		if(this.migliori.size()>0)
			return this.migliori;
		else
			return null;
		
	}
	
	
	private void permuta(Set <Esame> parziale, int livello) {
		
		if(this.sommaCrediti(parziale) == this.numeroCrediti) {
			
			double media = this.calcolaMedia(parziale);
			
			if(media>this.mediaMigliore)
			{
				this.mediaMigliore= media;
				this.migliori= new HashSet<Esame>(parziale);
			}
			return;
			
		}
		else if(this.sommaCrediti(parziale) > this.numeroCrediti)
			return;
		else if(livello==this.tuttiEsami.size())
			return;
		
		else {
			
			for(int i=0;i<this.tuttiEsami.size();i++)
			{
				if(!parziale.contains(this.tuttiEsami.get(i)) && i>=livello)
				{
					parziale.add(this.tuttiEsami.get(i));
					permuta(parziale,livello+1);
					parziale.remove(this.tuttiEsami.get(i));
				}
			}
			
		}
		
	}
	
private void permuta2(Set <Esame> parziale, int livello) {
	
	 int somma= this.sommaCrediti(parziale);
		
		if( somma == this.numeroCrediti) {
			
			double media = this.calcolaMedia(parziale);
			
			if(media>this.mediaMigliore)
			{
				this.mediaMigliore= media;
				this.migliori= new HashSet<Esame>(parziale);
			}
			return;
			
		}
		else if(somma > this.numeroCrediti)
			return;
		else if(livello==this.tuttiEsami.size())
			return;
		
		else {
			
				//for(int i=livello;i<this.tuttiEsami.size();i++)
				{
						parziale.add(this.tuttiEsami.get(livello));
						permuta(parziale,livello+1);
						parziale.remove(this.tuttiEsami.get(livello));
						permuta(parziale,livello+1);
						
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
