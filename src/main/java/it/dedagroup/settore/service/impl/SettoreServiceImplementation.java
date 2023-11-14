package it.dedagroup.settore.service.impl;

import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.repository.SettoreRepository;
import it.dedagroup.settore.service.def.SettoreServiceDefinition;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class SettoreServiceImplementation implements SettoreServiceDefinition {

    private final SettoreRepository settoreRepository;

    @Override
    public Settore findById(Long id) {
        return settoreRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun settore trovato con ID: "+id));
    }

    @Override
    public Settore findByIdAndIsCancellatoFalse(long id) {
        if(id>0){
            return settoreRepository.findByIdAndIsCancellatoFalse(id)
                    .orElseThrow(()-> new OptimisticLockingFailureException("Errore durante la lettura del dato"));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'ID deve essere maggiore di ");
        }
    }

    @Override
    public List<Settore> findAllByNomeAndIsCancellatoFalse(String nome) {
        return settoreRepository.findAllByNomeAndIsCancellatoFalse(nome);
    }

    @Override
    public List<Settore> findAllByPosti(int posti) {
        return null;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public Settore saveSettore(Settore settore){
        settoreRepository.save(settore);
        return settore;
    }

    @Override
    public Settore updateSettore(Settore settore) {
        return null;
    }
}