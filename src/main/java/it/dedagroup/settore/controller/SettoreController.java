package it.dedagroup.settore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import static org.springframework.http.HttpStatus.*;
import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
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
	@Operation(summary = "Aggiunge un nuovo settore",description = "Serve a effettuare l'aggiunta di un nuovo settore nel database.")
	@ApiResponses(value={
			@ApiResponse(description = "Il settore è stato aggiunto",
					responseCode = "CREATED(201)",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class)))
	})
    @PostMapping("/aggiungiSettore")
    public ResponseEntity<Settore> aggiungiSettore(@Valid @RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.status(CREATED).body(newSettore);
    }

    /**
     * Questo Endpoint serve a cercare nel database un oggetto di tipo {@link Settore} tramite il suo ID, anche tra i settori cancellati.<br>
     * @param id Richiede in input, tramite RequestParam, un numero intero corrispondente all' ID del settore da cercare.
     * @return {@link Settore} Ritorna un oggetto con all'interno tutti i dati del settore.
     */
    @GetMapping("/findById")
    public ResponseEntity<Settore> findById(@Positive(message = "Il campo ID deve essere un numero positivo")
												@RequestParam("id") long id ){
        Settore s = settoreService.findById(id);
        return ResponseEntity.status(FOUND).body(s);
    }

    /**
     * Questo Endpoint serve a cercare nel database un oggetto di tipo {@link Settore} non cacellato tramite il suo ID.<br>
     * @param id  Richiede in input, tramite RequestParam, un numero intero corrispondente all' ID del settore da cercare.
     * @return {@link Settore} Ritorna un oggetto con all'interno tutti i dati del settore.
     */
    @GetMapping("/findByIdEsistenti")
    public ResponseEntity<Settore> findByIdAndIsCancellatoFalse(@Positive(message = "Il campo ID deve essere un numero positivo")
																	@RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.status(FOUND).body(s);
    }

	/**
	 * Questo Enpoint serve per la ricerca di tutti i settori in base a una lista di ID.
	 * @param ids Richiede in input, tramite JSON, una list di numeri interi corrispondenti agli ID dei settori da cercare.
	 * @return
	 */
	@GetMapping("/allByIds")
	public ResponseEntity<List<Settore>> findAllByIds(@NotEmpty(message = "La lista deve contenere almeno un ID")
														  @RequestBody List<Long> ids){
		return ResponseEntity.ok(settoreService.findAllByIds(ids));
	}

	/**
	 * Questo Endpoint serve a cercare tutti i settori che hanno un numero di posti unguale a quello inserito in input.
	 * @param posti Richiede in input, tramite PathVariable, un numero intero corrispondente ai posti.
	 * @return Ritorna una lista di settori col numero di posti inserito in input.
	 */
	@GetMapping("/allByPosti/{posti}")
	public ResponseEntity<List<Settore>> findAllByPosti(@Positive(message = "Il campo posti deve essere un numero positivo")
															@PathVariable("posti") int posti){
		List<Settore> settori = settoreService.findAllByPosti(posti);
		return ResponseEntity.ok(settori);
	}

	/**
     * Questo Endpoint serve a cercare nel database tutti i settori con lo stesso nome.
     * @param nome Richiede in input, tramite RequestParam una stringa corrispondente al nome da cercare.
     * @return Ritorna una lista di settori.
     */
	@GetMapping("/allByNome/{nome}")
	public ResponseEntity<List<Settore>> findAllByNomeAndIsCancellatoFalse(@NotBlank(message = "Il campo nome non può essere lasciato vuoto")
																			   @PathVariable("nome") String nome){
		List<Settore> settori = settoreService.findAllByNomeAndIsCancellatoFalse(nome);
		return ResponseEntity.ok(settori);
	}

	/**
	 * Questo Endpoint serve simula la cancellazione del settore dal database, tramite ID del settore,<br>
	 * impostando la variabile "isCancellato" a true.
	 * @param id Richiede in input, tramite PathVariable, un numero intero corrispondente all' ID del settore da cercare.
	 * @return Ritorna una stringa in caso di "cancellazione" effettuata.
	 */
	@PostMapping("/deleteSettore/{id}")
	public ResponseEntity<String> deleteSettore(@Positive(message = "Il campo ID deve essere un numero positivo")
													@PathVariable("id") long id){
		settoreService.deleteSettore(id);
		return ResponseEntity.ok("Settore eliminato");
	}

	/**
	 * Questo Endpoint serve per la ricerca dei settori in base all'ID del luogo.
	 * @param idLuogo Prende in input, tramite RequestParam, l'ID di un luogo.
	 * @return Ritorna una lista di settori appartenenti a quel luogo.
	 */
	@GetMapping("/findByIdLuogo")
	public ResponseEntity<List<Settore>> findByIdLuogo(@Positive(message = "Il campo ID deve essere un numero positivo")
														   @RequestParam("idLuogo") long idLuogo){
		return ResponseEntity.status(FOUND).body(settoreService.findAllByIdLuogo(idLuogo));
	}
}
