package it.dedagroup.settore.mapper;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.DTO.response.SettoreResponse;
import it.dedagroup.settore.model.Settore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettoreMapper {
    public Settore RequestToSettore(SettoreRequest request){
        Settore newSettore = new Settore();
        newSettore.setNome(request.getNome());
        newSettore.setPosti(request.getPosti());
        return newSettore;
    }
    public Settore SettoreToResponse(Settore settore){
        SettoreResponse newResponse = new SettoreResponse();
        newResponse.setNome(settore.getNome());
        newResponse.setPosti(settore.getPosti());
        return settore;

    }
}
