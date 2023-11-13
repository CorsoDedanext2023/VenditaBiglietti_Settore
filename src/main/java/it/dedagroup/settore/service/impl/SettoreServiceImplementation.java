package it.dedagroup.settore.service.impl;

import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.repository.SettoreRepository;
import it.dedagroup.settore.service.def.SettoreServiceDefinition;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SettoreServiceImplementation implements SettoreServiceDefinition {

    private final SettoreRepository settoreRepository;

    @Override
    public List<Settore> findAllById(Long id) {
        return settoreRepository.findAllById(id);
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
        return null;
    }

    @Override
    public Settore saveSettore(Settore settore){
        return settoreRepository.save(settore);
    }

    @Override
    public Settore updateSettore(Settore settore) {
        return null;
    }
}
