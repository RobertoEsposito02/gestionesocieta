package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteService {
	public List<Dipendente> listAllDipendenti();
	
	public Dipendente caricaSingoloDipendente(Long id);
	
	public void aggiorna(Dipendente dipendente);
	
	public void inserisci(Dipendente dipendente);
	
	public void rimuovi(Dipendente dipendente);
	
	public Dipendente cercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990(Date dataFondazione);
}
