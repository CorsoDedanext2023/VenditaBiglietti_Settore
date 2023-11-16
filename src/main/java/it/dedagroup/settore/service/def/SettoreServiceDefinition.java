package it.dedagroup.settore.service.def;

import it.dedagroup.settore.model.Settore;

import java.util.List;

public interface SettoreServiceDefinition {
	
    List<Settore> findAllByIds(List<Long> id);
    Settore findById(Long id);
    Settore findByIdAndIsCancellatoFalse(long id);
    List<Settore> findAllByNomeAndIsCancellatoFalse(String nome);
    List<Settore> findAllByPosti(int posti);
    List<Settore>findAllByIdLuogo(long idLuogo);
    List<Settore>findAll();
    Settore saveSettore(Settore settore);
    Settore updateSettore(Settore settore);
    void deleteSettore(long id);
}
