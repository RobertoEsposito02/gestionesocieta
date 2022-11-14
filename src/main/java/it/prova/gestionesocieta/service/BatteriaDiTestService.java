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
		// Societa societa = societaService.listAllSocieta().get(0);
		// societa.getDipendenti().add(nuovoDipendente);

		Societa societaDaEliminare = societaService.listAllSocieta().get(0);
		try {
			dipendenteService.rimuovi(nuovoDipendente);
			societaService.rimuovi(societaDaEliminare);
		} catch (SocietaConDipendentiException e) {
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

		if (quantiRecordPresentiAttualmente + 1 != dipendenteService.listAllDipendenti().size())
			throw new RuntimeException("testInserimentoDipendente: FALLITO inserimento non riuscito");

		System.out.println("----------testInserimentoDipendente PASSED------------");
	}

	public void testAggiornamentoDipendente() {
		System.out.println("----------tesAggiornamentoDipendente START------------");

		if (dipendenteService.listAllDipendenti().size() < 1)
			throw new RuntimeException("tesAggiornamentoDipendente: FALLITO impossibile aggiornare un DB vuoto");

		Dipendente dipendenteDaAggiornare = dipendenteService
				.caricaSingoloDipendente(dipendenteService.listAllDipendenti().get(0).getId());
		dipendenteDaAggiornare.setNome("Luigi");

		dipendenteService.aggiorna(dipendenteDaAggiornare);

		dipendenteService.rimuovi(dipendenteDaAggiornare);
		/*
		 * elimino la societa implementata nel metedo sopra, la quale non potevo
		 * elimanre perchè legata a un dipendente il quale mi è servito per fare il test
		 * di aggiornamento del dipendente
		 */
		societaService.rimuovi(societaService.listAllSocieta().get(0));

		System.out.println("----------tesAggiornamentoDipendente PASSED------------");
	}

	public void testCercaLeSocietaConAlmenoUnDipendenteConRalSopraA() throws ParseException {
		System.out.println("----------testCercaLeSocietaConAlmenoUnDipendenteConRalSopraA START------------");

		Societa nuovaSocieta = new Societa("societa1", "Via sole", new Date());
		societaService.inserisci(nuovaSocieta);
		Societa nuovaSocieta2 = new Societa("societa1", "Via sole", new Date());
		societaService.inserisci(nuovaSocieta2);

		Dipendente nuovoDipendente = new Dipendente("Mario", "Olla",
				new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/25"), 50000, nuovaSocieta);
		dipendenteService.inserisci(nuovoDipendente);

		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		societaService.aggiorna(nuovaSocieta2);

		Integer ral = 30000;
		if (societaService.cercaLeSocietaConAlmenoUnDipendenteConRalSopraA(ral).size() != 1)
			throw new RuntimeException(
					"testCercaLeSocietaConAlmenoUnDipendenteConRalSopraA: FALLITO ricerca non riuscita");

		// dipendenteService.rimuovi(nuovoDipendente);
		// societaService.rimuovi(nuovaSocieta);
		// societaService.rimuovi(nuovaSocieta2);

		System.out.println("----------testCercaLeSocietaConAlmenoUnDipendenteConRalSopraA PASSED------------");
	}

	public void testCercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990() throws ParseException {
		System.out.println(
				"----------testCercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990 START------------");

		Societa nuovaSocieta = new Societa("societa1", "Via sole",
				new SimpleDateFormat("yyyy/MM/dd").parse("1980/11/25"));
		societaService.inserisci(nuovaSocieta);

		Dipendente nuovoDipendente = new Dipendente("Mario", "Olla",
				new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/25"), 50000, nuovaSocieta);
		Dipendente nuovoDipendente2 = new Dipendente("Pippo", "Baudo",
				new SimpleDateFormat("yyyy/MM/dd").parse("1999/11/25"), 50000, nuovaSocieta);
		dipendenteService.inserisci(nuovoDipendente);
		dipendenteService.inserisci(nuovoDipendente2);

		nuovaSocieta.getDipendenti().add(nuovoDipendente);
		nuovaSocieta.getDipendenti().add(nuovoDipendente2);
		societaService.aggiorna(nuovaSocieta);

		Date dataInput = new SimpleDateFormat("yyyy/MM/dd").parse("1990/01/01");
		if (dipendenteService.cercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990(dataInput) == null)
			throw new RuntimeException(
					"testCercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990: FALLITO ricerca non riuscita");

		System.out.println(
				"----------testCercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990 PASSED------------");
	}
}
