package it.dedagroup.settore.controller;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.DTO.response.SettoreResponse;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import static org.springframework.http.HttpStatus.*;

import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settore")
@AllArgsConstructor
@Validated
public class SettoreController {

    private final SettoreServiceImplementation settoreService;

    private final SettoreMapper settoreMapper;

    /**
     * Questo Endpoint serve a effettuare l'aggiunta di un nuovo settore nel database.
     * @param request Richiede in input, tramite JSON, un oggetto di tipo {@link SettoreRequest} <br>
     *                che contiene i dati necessari alla creazione dell'oggetto {@link Settore}.
     * @return {@link SettoreResponse} Ritorna un oggetto DTO con all'interno tutti i dati del settore necessari.
     */
    @GetMapping("/aggiungiSettore")
    public ResponseEntity<SettoreResponse> aggiungiSettore(@Valid @RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.status(OK).body(settoreMapper.toSettoreResponseFromSettore(newSettore));
    }

    /**
     * Questo Endpoint serve a cercare nel database un oggetto di tipo {@link Settore} tramite il suo ID.<br>
     * @param id Richiede in input, tramite RequestParam un ID di tipo long.
     * @return {@link SettoreResponse} Ritorna un oggetto DTO con all'interno tutti i dati del settore necessari.
     */
    @GetMapping("/findById")
    public ResponseEntity<SettoreResponse> findById(@Valid @RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.status(FOUND).body(settoreMapper.toSettoreResponseFromSettore(s));
    }

    /**
     * Questo Endpoint serve a cercare nel database tutti i settori con lo stesso nome.
     * @param nome Richiede in input, tramite RequestParam un nome.
     * @return Ritorna una lista di settori.
     */
    @GetMapping("/findAllByNome")
    public ResponseEntity<List<Settore>>findAllByNome(@Valid @RequestParam("nome") String nome ){
        List<Settore> settoreList = settoreService.findAllByNomeAndIsCancellatoFalse(nome);
        return ResponseEntity.status(FOUND).body(settoreList);
    }

}
