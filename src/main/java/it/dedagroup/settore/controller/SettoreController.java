package it.dedagroup.settore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settore")
@AllArgsConstructor
@Tag(name="Endpoint microservizio Settore",
		description = "Questo controller gestisce il microservizio del Settore e interagisce sul database solo sulla tabella Settore")
@Validated
public class SettoreController {

	private final SettoreServiceImplementation settoreService;
	private final SettoreMapper settoreMapper;

	/**
	 * Questo Endpoint serve a effettuare l'aggiunta di un nuovo settore nel database.
	 * @param request Richiede in input, tramite JSON, un oggetto di tipo {@link SettoreRequest} <br>
	 *                che contiene i dati necessari alla creazione dell'oggetto {@link Settore}.
	 * @return {@link Settore} Ritorna un oggetto DTO con all'interno tutti i dati del settore necessari.
	 */

    @PostMapping("/aggiungiSettore")
    public ResponseEntity<Settore> aggiungiSettore(@Valid @RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.ok().body(newSettore);
    }
    

    @GetMapping("/findById")
    public ResponseEntity<Settore> findById(@Positive(message = "Il campo ID deve essere un numero positivo")
												@RequestParam("id") long id ){
        Settore s = settoreService.findById(id);
        return ResponseEntity.ok().body(s);
    }
    


    @GetMapping("/findByIdEsistenti")
    public ResponseEntity<Settore> findByIdAndIsCancellatoFalse(@Positive(message = "Il campo ID deve essere un numero positivo")
																	@RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.ok().body(s);
    }
    


    @GetMapping("/allByIds")
	public ResponseEntity<List<Settore>> findAllByIds(@NotEmpty(message = "La lista deve contenere almeno un ID")
														  @RequestBody List<Long> ids){
		return ResponseEntity.ok(settoreService.findAllByIds(ids));
	}
    
	 
	@GetMapping("/allByPosti/{posti}")
	public ResponseEntity<List<Settore>> findAllByPosti(@Positive(message = "Il campo posti deve essere un numero positivo")
															@PathVariable("posti") int posti){
		List<Settore> settori = settoreService.findAllByCapienza(posti);
		return ResponseEntity.ok(settori);
	}
    

	@GetMapping("/allByNome/{nome}")
	public ResponseEntity<List<Settore>> findAllByNomeAndIsCancellatoFalse(@NotBlank(message = "Il campo nome non può essere lasciato vuoto")
																			   @PathVariable("nome") String nome){
		List<Settore> settori = settoreService.findAllByNomeAndIsCancellatoFalse(nome);
		return ResponseEntity.ok(settori);
	}
    
	
	 @PutMapping("/deleteSettore/{id}")
	public ResponseEntity<String> deleteSettore(@Positive(message = "Il campo ID deve essere un numero positivo")
													@PathVariable("id") long id){
		settoreService.deleteSettore(id);
		return ResponseEntity.ok("Settore eliminato");
	}

    
	@GetMapping("/findByIdLuogo")
	public ResponseEntity<List<Settore>> findByIdLuogo(@Positive(message = "Il campo ID deve essere un numero positivo")
														   @RequestParam("idLuogo") long idLuogo){
		return ResponseEntity.ok().body(settoreService.findAllByIdLuogo(idLuogo));
	}


	@GetMapping("/findAll")
	public ResponseEntity<List<Settore>> findAll(){
		return ResponseEntity.ok().body(settoreService.findAll());
	}
	 
	 
	 @PostMapping("/findAllByListIdLuogo")
		public ResponseEntity<List<Settore>> findAllByListIdLuogo(@RequestBody List<Long> idLuogo){
			return ResponseEntity.ok(settoreService.findAllByListIdLuogo(idLuogo));
		}
}
