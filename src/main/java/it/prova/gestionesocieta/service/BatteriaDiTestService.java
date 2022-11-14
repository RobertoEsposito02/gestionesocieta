package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {
	@Autowired
	private SocietaService societaService;
	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciSocieta() {
		System.out.println("----------testInserisciSocieta START------------");

		Societa nuovaSocieta = new Societa("societa1", "Via sole", new Date());
		societaService.inserisci(nuovaSocieta);

		if (societaService.listAllSocieta().size() != 1)
			throw new RuntimeException("testInserisciSocieta: FALLITO inserimento societa non avvenuto");

		System.out.println("----------testInserisciSocieta PASSED------------");
	}

	public void testFindByExampleSocieta() throws ParseException {
		System.out.println("----------testFindByExampleSocieta START------------");

		String ragioneSocialeExample = "";
		String indirizzoExample = "";
		Date dataFondazioneExample = new SimpleDateFormat("yyyy/MM/dd").parse("2010/01/01");

		Societa societaExample = new Societa(ragioneSocialeExample, indirizzoExample, dataFondazioneExample);
		if (societaService.findByExample(societaExample).size() != 1)
			throw new RuntimeException("testFindByExampleSocieta: FALLITO ricerca non riuscita");

		System.out.println("----------testFindByExampleSocieta PASSED------------");
	}

	public void testRimozioneSocieta() throws ParseException {
		System.out.println("----------testRimozioneSocieta START------------");

		int quantiRecordPresentiAttualmente = societaService.listAllSocieta().size();
		if (quantiRecordPresentiAttualmente < 1)
			throw new RuntimeException("testRimozioneSocieta: FALLITO non c'è niente da eliminare");

		Dipendente nuovoDipendente = new Dipendente("Mario", "Olla",
				new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/25"), 2000, societaService.listAllSocieta().get(0));
		dipendenteService.inserisci(nuovoDipendente);
		//Societa societa = societaService.listAllSocieta().get(0);
		//societa.getDipendenti().add(nuovoDipendente);

		Societa societaDaEliminare = societaService.listAllSocieta().get(0);
		try {
			dipendenteService.rimuovi(nuovoDipendente);
		societaService.rimuovi(societaDaEliminare);
		}catch (SocietaConDipendentiException e) {
			e.printStackTrace();
		}

		if (societaService.listAllSocieta().size() != quantiRecordPresentiAttualmente - 1)
			throw new RuntimeException("testRimozioneSocieta: FALLITO societa non eliminata");

		System.out.println("----------testRimozioneSocieta PASSED------------");
	}
	
	public void testInserimentoDipendente() throws ParseException {
		System.out.println("----------testInserimentoDipendente START------------");
		
		Societa nuovaSocieta = new Societa("societa1", "Via sole", new Date());
		societaService.inserisci(nuovaSocieta);
		
		int quantiRecordPresentiAttualmente = dipendenteService.listAllDipendenti().size();
		
		Dipendente nuovoDipendente = new Dipendente("Mario", "Olla",
				new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/25"), 2000, societaService.listAllSocieta().get(0));
		dipendenteService.inserisci(nuovoDipendente);
		
		if(quantiRecordPresentiAttualmente +1 != dipendenteService.listAllDipendenti().size())
			throw new RuntimeException("testInserimentoDipendente: FALLITO inserimento non riuscito");
		
		System.out.println("----------testInserimentoDipendente PASSED------------");
	}
	
	public void testAggiornamentoDipendente() {
		System.out.println("----------tesAggiornamentoDipendente START------------");
		
		if(dipendenteService.listAllDipendenti().size() < 1)
			throw new RuntimeException("tesAggiornamentoDipendente: FALLITO impossibile aggiornare un DB vuoto");
		
		Dipendente dipendenteDaAggiornare = dipendenteService.caricaSingoloDipendente(dipendenteService.listAllDipendenti().get(0).getId());
		dipendenteDaAggiornare.setNome("Luigi");
		
		dipendenteService.aggiorna(dipendenteDaAggiornare);
		
		System.out.println("----------tesAggiornamentoDipendente PASSED------------");
	}
	
	
}
