package it.prova.gestionesocieta.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaRepository extends CrudRepository<Societa, Long>{
	List<Societa> findAllDistinctByDipendenti_ReditoAnnuoLordoGreaterThan(Integer input);
}
