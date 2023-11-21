package it.dedagroup.settore.service.impl;

import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.repository.SettoreRepository;
import it.dedagroup.settore.service.def.SettoreServiceDefinition;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class SettoreServiceImplementation implements SettoreServiceDefinition {

	private final SettoreRepository settoreRepository;

	@Override
	public List<Settore> findAllByIds(List<Long> ids) {
		List<Settore> lista = settoreRepository.findAllById(ids);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con ID: " +ids.toString());
		}
		return settoreRepository.findAllById(ids);
	}
	
	public Settore findById(Long id) {
		return settoreRepository.findById(id)
				.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: "+id));
	}

    @Override
    public Settore findByIdAndIsCancellatoFalse(long id) {
        if(id>0){
            return settoreRepository.findByIdAndIsCancellatoFalse(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: " +id));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ID deve essere maggiore di 0");
        }
    }

	@Override
	public List<Settore> findAllByNomeAndIsCancellatoFalse(String nome) {
		List<Settore> lista = settoreRepository.findAllByNomeAndIsCancellatoFalse(nome);
		if(lista.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nessun settore trovato con nome: " +nome);
		}
		return lista;
	}

	@Override
	public List<Settore> findAllByPosti(int posti) {
		return settoreRepository.findAllByPosti(posti);
	}

	@Override
	public List<Settore> findAllByIdLuogo(long idLuogo) {
		return settoreRepository.findAllByIdLuogo(idLuogo);
	}

	@Override
	public List<Settore> findAll() {
		return settoreRepository.findAll();
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
    public Settore saveSettore(Settore settore){
        settoreRepository.save(settore);
        return settore;
    }

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Settore updateSettore(Settore settore) {
		if(settoreRepository.findById(settore.getId()).isPresent()){
			Settore s = settoreRepository.findById(settore.getId()).get();
			s.setNome(settore.getNome());
			s.setPosti(settore.getPosti());
			s.setIdLuogo(settore.getIdLuogo());
			s.setCancellato(settore.isCancellato());
			return settoreRepository.save(s);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: " +settore.getId());
	}

	/**
	 * Questo metodo serve simula la cancellazione del settore dal database, tramite ID del settore,<br>
	 * impostando la variabile "isCancellato" a true.
	 * @param id Richiede in input, tramite path variable, un ID.
	 * @return Ritorna una stringa in caso di "cancellazione" effettuata.
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteSettore(long id) {
		Settore settoreDaCancellare=settoreRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"non esiste nessun settore con questo id"));
		settoreDaCancellare.setCancellato(true);
		settoreRepository.save(settoreDaCancellare);

	}

	@Override
	public List<Settore> findAllByListIdLuogo(List<Long> idLuogo) {
		return settoreRepository.findAllByIdLuogoIn(idLuogo);
	}



}
