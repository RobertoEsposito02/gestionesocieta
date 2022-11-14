package it.prova.gestionesocieta.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {
	@Autowired
	private SocietaService societaService;
	@Autowired
	private DipendenteService dipendenteService;
	
	public void testInserisciSocieta() {
		System.out.println("----------testInserisciSocieta START------------");

		Societa nuovaSocieta = new Societa("societa1","Via sole",new Date());
		societaService.inserisci(nuovaSocieta);
		
		if(societaService.listAllSocieta().size() != 1)
			throw new RuntimeException("testInserisciSocieta: FALLITO inserimento societa non avvenuto");
		
		System.out.println("----------testInserisciSocieta START------------");
	}
	
}
