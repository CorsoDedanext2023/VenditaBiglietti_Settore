package it.dedagroup.settore.service.def;

import it.dedagroup.settore.model.Settore;

import java.util.List;

public interface SettoreServiceDefinition {
    List<Settore> findAllById(Long id);
    List<Settore> findAllByNome(String nome);
    Settore saveSettore(Settore settore);
    Settore updateSettore(Settore settore);
}
