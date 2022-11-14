package it.prova.gestionesocieta.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService{

	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@Override
	public List<Dipendente> listAllDipendenti() {
		return (List<Dipendente>)dipendenteRepository.findAll();
	}

	@Override
	public Dipendente caricaSingoloDipendente(Long id) {
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

	@Override
	public void inserisci(Dipendente dipendente) {
		dipendenteRepository.save(dipendente);
	}

	@Override
	public void rimuovi(Dipendente dipendente) {
		dipendenteRepository.delete(dipendente);
	}

	@Override
	public Dipendente cercaIlPiuAnzianoInTerminiLavorativiInSocietaFondatePrimaDel1990(Date dataFondazione) {
		return dipendenteRepository.findFirst1BySocieta_DataFondazioneBeforeOrderByDataAssunzioneAsc(dataFondazione);
	}
	
	

}
