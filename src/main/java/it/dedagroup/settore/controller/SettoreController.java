package it.dedagroup.settore.controller;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.DTO.response.SettoreResponse;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import java.util.List;
import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping("/aggiungiSettore")
    public ResponseEntity<SettoreResponse> aggiungiSettore(@Valid @RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.status(HttpStatus.OK).body(settoreMapper.toSettoreResponseFromSettore(newSettore));
    }

    /**
     * Questo Endpoint serve a cercare nel database un oggetto di tipo {@link Settore} tramite il suo ID.<br>
     * @param id Richiede in input, tramite RequestParam un ID di tipo long.
     * @return {@link SettoreResponse} Ritorna un oggetto DTO con all'interno tutti i dati del settore necessari.
     */
    @GetMapping("/findById")
    public ResponseEntity<SettoreResponse> findById(@Valid @RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(settoreMapper.toSettoreResponseFromSettore(s));
    }

   
	@GetMapping("/allByIds")
	public ResponseEntity<List<SettoreResponse>> findAllByIds(@RequestBody List<Long> ids){
		return ResponseEntity.ok(settoreMapper.toListSettoreResponseFromListSettore(settoreService.findAllByIds(ids)));
	}

	@GetMapping("/allByPosti/{posti}")
	public ResponseEntity<List<SettoreResponse>> findAllByPosti(@PathVariable("posti") int posti){
		List<Settore> settori = settoreService.findAllByPosti(posti);
		List<SettoreResponse> settoreResponses = settoreMapper.toListSettoreResponseFromListSettore(settori);
		return ResponseEntity.ok(settoreResponses);   
	}
	
	/**
     * Questo Endpoint serve a cercare nel database tutti i settori con lo stesso nome.
     * @param nome Richiede in input, tramite RequestParam un nome.
     * @return Ritorna una lista di settori.
     */
	@GetMapping("/allByNome/{nome}")
	public ResponseEntity<List<SettoreResponse>> findAllByNomeAndIsCancellatoFalse(@PathVariable("nome") String nome){
		List<Settore> settori = settoreService.findAllByNomeAndIsCancellatoFalse(nome);
		List<SettoreResponse> settoreResponses = settoreMapper.toListSettoreResponseFromListSettore(settori);
		return ResponseEntity.ok(settoreResponses);   
	}
	
	@PostMapping("/deleteSettore/{id}")
	public ResponseEntity<String> deleteSettore(@PathVariable("id") long id){
		settoreService.deleteSettore(id);
		return ResponseEntity.ok("Settore eliminato");
	}
   


}
