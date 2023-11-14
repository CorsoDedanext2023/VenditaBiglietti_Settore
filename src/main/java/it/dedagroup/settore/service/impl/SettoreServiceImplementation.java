package it.dedagroup.settore.service.impl;

import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.repository.SettoreRepository;
import it.dedagroup.settore.service.def.SettoreServiceDefinition;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class SettoreServiceImplementation implements SettoreServiceDefinition {

    @Autowired
    SettoreRepository settoreRepository;

    @Override
    public List<Settore> findAllByIds(List<Long> ids) {
        return settoreRepository.findAllById(ids);
    }

    @Override
    public Settore findByIdAndIsCancellatoFalse(long id) {
        return null;
    }

    @Override
    public List<Settore> findAllByNomeAndIsCancellatoFalse(String nome) {
        return settoreRepository.findAllByNomeAndIsCancellatoFalse(nome);
    }

    @Override
    public List<Settore> findAllByPosti(int posti) {
        return settoreRepository.findAllByPosti(posti);
    }

    @Override
    public Settore saveSettore(Settore settore){
        return settoreRepository.save(settore);
    }

	@Override
	public Settore updateSettore(Settore settore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSettore(long id) {
		Settore settoreDaCancellare=settoreRepository.findById(id).orElseThrow(()-> new RuntimeException("non esiste nessun settore con questo id"));
		settoreDaCancellare.setCancellato(true);
		settoreRepository.save(settoreDaCancellare);
		
	}

   
}
