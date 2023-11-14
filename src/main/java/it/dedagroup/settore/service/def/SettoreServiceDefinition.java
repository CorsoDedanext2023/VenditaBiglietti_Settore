package it.dedagroup.settore.service.def;

import it.dedagroup.settore.model.Settore;

import java.util.List;
import java.util.Optional;

public interface SettoreServiceDefinition {
    Settore findById(Long id);
    Settore findByIdAndIsCancellatoFalse(long id);
    List<Settore> findAllByNomeAndIsCancellatoFalse(String nome);
    List<Settore> findAllByPosti(int posti);
    Settore saveSettore(Settore settore);
    Settore updateSettore(Settore settore);
}
