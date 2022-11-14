package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {
	public List<Societa> listAllSocieta();
	
	public Societa caricaSingolaSocieta(Long id);
	
	public void aggiorna(Societa societa);
	
	public void inserisci(Societa societa);
	
	public void rimuovi(Societa societa);
	
	public List<Societa> findByExample(Societa societa);
	
	public List<Societa> cercaLeSocietaConAlmenoUnDipendenteConRalSopraA(Integer input);
}
