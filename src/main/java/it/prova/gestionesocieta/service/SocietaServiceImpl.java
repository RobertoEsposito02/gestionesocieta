package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService{

	@Autowired
	private SocietaRepository societaRepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Societa> listAllSocieta() {
		return (List<Societa>) societaRepository.findAll();
	}

	@Override
	public Societa caricaSingolaSocieta(Long id) {
		return societaRepository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Societa societa) {
		societaRepository.save(societa);
	}

	@Override
	public void inserisci(Societa societa) {
		societaRepository.save(societa);
	}

	@Override
	public void rimuovi(Societa societa) {
		societaRepository.delete(societa);
	}

	@Override
	public List<Societa> findByExample(Societa societa) {
		// TODO Auto-generated method stub
		return null;
	}

}
