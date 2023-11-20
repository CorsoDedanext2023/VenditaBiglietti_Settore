package it.dedagroup.settore.mapper;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.model.Settore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettoreMapper {

    public Settore toSettoreFromRequest(SettoreRequest request){
        Settore newSettore = new Settore();
        newSettore.setNome(request.getNome());
        newSettore.setCapienza(request.getPosti());
        newSettore.setIdLuogo(request.getIdLuogo());
        newSettore.setCancellato(false);
        newSettore.setVersion(newSettore.getVersion());
        return newSettore;
    }
}