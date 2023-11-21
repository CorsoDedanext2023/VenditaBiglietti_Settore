package it.dedagroup.settore.service.impl;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.repository.SettoreRepository;
import it.dedagroup.settore.service.def.SettoreServiceDefinition;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SettoreServiceImplementation implements SettoreServiceDefinition {

	private final SettoreRepository settoreRepository;

	/**
	 * Questo metodo serve per la ricerca di tutti i settori in base a una lista di ID.
	 * @param ids Richiede in input una list di numeri interi corrispondenti agli ID dei settori da cercare.
	 * @return Ritorna una List di Settore corrispondente agli ID di Settore dati in input.
	 */
	@Override
	public List<Settore> findAllByIds(List<Long> ids) {
		List<Settore> lista = settoreRepository.findAllById(ids);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con ID: " +ids.toString());
		}
		return settoreRepository.findAllById(ids);
	}

	/**
	 * Questo metodo serve a cercare nel database un oggetto di tipo {@link Settore} tramite il suo ID, anche tra i settori cancellati.<br>
	 * @param id Richiede in input, tramite RequestParam, un numero intero corrispondente all' ID del settore da cercare.
	 * @return Ritorna un oggetto {@link Settore} con all'interno tutti i dati.
	 */
	public Settore findById(Long id) {
		return settoreRepository.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: "+id));
	}

	/**
	 * Questo metodo serve a cercare nel database un oggetto di tipo {@link Settore} non cancellato tramite il suo ID.<br>
	 * @param id  Richiede in input, tramite RequestParam, un numero intero corrispondente all' ID del settore da cercare.
	 * @return Ritorna un oggetto {@link Settore} con all'interno tutti i dati.
	 */
    @Override
    public Settore findByIdAndIsCancellatoFalse(long id) {
        if(id>0){
            return settoreRepository.findByIdAndIsCancellatoFalse(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: " +id));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ID deve essere maggiore di 0");
        }
    }

	/**
	 * Questo {@link Settore} serve a cercare nel database tutti i settori con lo stesso nome.
	 * @param nome Richiede in input, tramite RequestParam una stringa corrispondente al nome da cercare.
	 * @return Ritorna una lista di {@link Settore}.
	 */
	@Override
	public List<Settore> findAllByNomeAndIsCancellatoFalse(String nome) {
		List<Settore> lista = settoreRepository.findAllByNomeAndIsCancellatoFalse(nome);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con nome: " +nome);
		}
		return lista;
	}

	/**
	 * Questo metodo serve a cercare tutti i settori che hanno un numero di posti uguale a quello inserito in input.
	 * @param posti Richiede in input un numero intero corrispondente ai posti.
	 * @return Ritorna una lista di {@link Settore} col numero di posti inserito in input.
	 */
	@Override
	public List<Settore> findAllByCapienza(int posti) {
		List<Settore> lista = settoreRepository.findAllByCapienza(posti);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con posti: " +posti);
		}
		return lista;
	}

	/**
	 * Questo metodo serve per la ricerca dei settori in base all'ID del luogo.
	 * @param idLuogo Prende in input, tramite RequestParam, l'ID di un luogo.
	 * @return Ritorna una lista di {@link Settore} appartenenti a quel luogo.
	 */
	@Override
	public List<Settore> findAllByIdLuogo(long idLuogo) {
		List<Settore>lista = settoreRepository.findAllByIdLuogo(idLuogo);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con ID Luogo: " +idLuogo);
		}
		return lista;
	}

	/**
	 * Questo metodo serve a trovare tutti i settori presenti nel database, compresi quelli con <br>
	 * la variabile "isCancellato" settata a true
	 * @return Ritorna una List di {@link Settore}.
	 */
	@Override
	public List<Settore> findAll() {
		List<Settore> lista = settoreRepository.findAll();
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato");
		}
		return lista;
	}

	/**
	 * Questo metodo serve a effettuare l'aggiunta di un nuovo settore nel database.
	 * @param settore Richiede in input, tramite JSON, un oggetto di tipo {@link SettoreRequest} <br>
	 *                che contiene i dati necessari alla creazione dell'oggetto {@link Settore}.
	 * @return {@link Settore} Ritorna un oggetto {@link Settore} con all'interno tutti i dati.
	 */
	@Override
    @Transactional(rollbackOn = Exception.class)
    public Settore saveSettore(Settore settore){
		try {
			settoreRepository.save(settore);
			return settore;
		}catch (OptimisticLockingFailureException e){
			throw new OptimisticLockingFailureException("Questo oggetto è stato modificato");
		}
    }

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Settore updateSettore(Settore settore) {
		try {
			if (settoreRepository.findById(settore.getId()).isPresent()) {
				Settore s = settoreRepository.findById(settore.getId()).get();
				s.setNome(settore.getNome());
				s.setCapienza(settore.getCapienza());
				s.setIdLuogo(settore.getIdLuogo());
				s.setCancellato(settore.isCancellato());
				return settoreRepository.save(s);
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: " + settore.getId());
		}catch (OptimisticLockingFailureException e){
			throw new OptimisticLockingFailureException("Questo oggetto è stato modificato");
		}
	}

	/**
	 * Questo metodo simula la cancellazione del settore dal database, tramite ID del settore,<br>
	 * impostando la variabile "isCancellato" a true.
	 * @param id Richiede in input, tramite path variable, un ID.
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteSettore(long id) {
		try{
			Settore settoreDaCancellare=settoreRepository.findById(id)
					.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"non esiste nessun settore con questo id"));
			settoreDaCancellare.setCancellato(true);
			settoreRepository.save(settoreDaCancellare);
		}catch (OptimisticLockingFailureException e){
			throw new OptimisticLockingFailureException("Questo oggetto è stato modificato");
		}
	}

	@Override
	public List<Settore> findAllByListIdLuogo(List<Long> idLuogo) {
		return settoreRepository.findAllByIdLuogoIn(idLuogo);
	}

}
