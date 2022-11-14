package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exception.SocietaConDipendentiException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {

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
		TypedQuery<Societa> query = entityManager
				.createQuery("select s from Societa s join fetch s.dipendenti d where s.id = :id", Societa.class)
				.setParameter("id", societa.getId());
		if (!query.getResultList().isEmpty())
			throw new SocietaConDipendentiException("CI SONO DEI FIGLI ELIMINAZIONE NON POSSIBILE");
		societaRepository.delete(societa);

	}

	@Override
	public List<Societa> findByExample(Societa societa) {
		String query = "from Societa s where s.id=s.id ";

		if (!societa.getRagioneSociale().isEmpty())
			query += " and s.ragioneSociale like '" + societa.getRagioneSociale() + "%' ";
		if (!societa.getIndirizzo().isEmpty())
			query += " and s.indirizzo like '" + societa.getIndirizzo() + "%' ";
		if (societa.getDataFondazione() != null)
			query += " and s.dataFondazione >= '" + societa.getDataFondazione().toInstant() + "' ";

		return entityManager.createQuery(query, Societa.class).getResultList();
	}

}
