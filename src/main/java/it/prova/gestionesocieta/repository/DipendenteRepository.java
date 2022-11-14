package it.prova.gestionesocieta.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesocieta.model.Dipendente;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>{
	Dipendente findFirst1BySocieta_DataFondazioneBeforeOrderByDataAssunzioneAsc(Date dataFondazione);
}
