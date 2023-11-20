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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
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
	@ApiResponse(	description = "Il settore è stato aggiunto",
					responseCode = "CREATED(201)",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class)))

    @PostMapping("/aggiungiSettore")
    public ResponseEntity<Settore> aggiungiSettore(@Valid @RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.status(CREATED).body(newSettore);
    }
    
	@Operation(summary = "Cerca un settore per id",description = "Serve a effettuare la ricerca di un settore nel database,tramite l'id passato al metodo. Se viene trovato risponderà 302, se non viene trovato viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece l'id è minore di 1 è un id non valido e quindi risponderà con un codice 400")
	@ApiResponses(value={
			@ApiResponse(description = "Il settore è stato trovato",
						responseCode = "FOUND(302)",
						content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
			@ApiResponse(description = "Il settore non è stato stato trovato per l'id passato al metodo",
						responseCode = "NOTFOUND(404)",
						content = @Content(mediaType = MediaType.ALL_VALUE)),
			@ApiResponse(description = "l'ID deve essere un numero positivo",
			responseCode = "BADREQUEST(400)",
			content = @Content(mediaType = MediaType.ALL_VALUE))
			
	})
    @GetMapping("/findById")
    public ResponseEntity<Settore> findById(@Positive(message = "Il campo ID deve essere un numero positivo")
												@RequestParam("id") long id ){
        Settore s = settoreService.findById(id);
        return ResponseEntity.status(FOUND).body(s);
    }
    
	@Operation(summary = "Trova un settore non cancellato per ID", description = "Serve a effettuare la ricerca di un settore non cancellato nel database,tramite l'id passato al metodo. Se viene trovato risponderà 302, se non viene trovato viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece l'id è minore di 1 è un id non valido e quindi risponderà con un codice 400")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "FOUND(302)", description = "Il settore non cancellato è stato trovato", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
            @ApiResponse(responseCode = "NOTFOUND(404)", description = "Settore non trovato per l'ID fornito", content = @Content(mediaType = MediaType.ALL_VALUE)),
            @ApiResponse(responseCode = "BADREQUEST(400)", description = "l'ID deve essere un numero positivo",content = @Content(mediaType = MediaType.ALL_VALUE))
    })
    @GetMapping("/findByIdEsistenti")
    public ResponseEntity<Settore> findByIdAndIsCancellatoFalse(@Positive(message = "Il campo ID deve essere un numero positivo")
																	@RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.status(FOUND).body(s);
    }
    
	 @Operation(summary = "Ricerca dei settori tramite lista di ID",description = "Serve a effettuare la ricerca di una lista di settori nel database,tramite la lista di id passata al metodo, in formato json. Se la lista viene trovata risponderà con codice 200, se almeno uno degli id non viene trovato viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece la lista passata al metodo è vuota risponderà con un codice 400")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "SUCCESS(200)", description = "la lista dei settori è stata trovata", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
	            @ApiResponse(responseCode = "NOTFOUND(400)", description = "la lista dei settori non è stata trovata per gli ID forniti")
	    })
    @GetMapping("/allByIds")
	public ResponseEntity<List<Settore>> findAllByIds(@NotEmpty(message = "La lista deve contenere almeno un ID")
														  @RequestBody List<Long> ids){
		return ResponseEntity.ok(settoreService.findAllByIds(ids));
	}
    
	 @Operation(summary = "Restituisce una lista di settori in base al numero dei posti(capienza)",description = "Serve a effettuare la ricerca di una lista di settori nel database,tramite il numero dei posti passato al metodo. Se viene trovata risponderà 200, se non viene trovata viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece il numero dei posti è minore di 1 risponderà con un codice 400")
		@ApiResponses(value={
				@ApiResponse(description = "la lista di settori è stata trovata",
							responseCode = "SUCCESS(200)",
							content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
				@ApiResponse(description = "La lista di settori non è stata stata trovata per il numero di posti passato al metodo",
							responseCode = "NOTFOUND(404)",
							content = @Content(mediaType = MediaType.ALL_VALUE)),
				@ApiResponse(description = "il numero dei posti deve essere un numero positivo",
				responseCode = "BADREQUEST(400)",
				content = @Content(mediaType = MediaType.ALL_VALUE))
				
		})
	@GetMapping("/allByPosti/{posti}")
	public ResponseEntity<List<Settore>> findAllByPosti(@Positive(message = "Il campo posti deve essere un numero positivo")
															@PathVariable("posti") int posti){
		List<Settore> settori = settoreService.findAllByCapienza(posti);
		return ResponseEntity.ok(settori);
	}
    
	 @Operation(summary = "Restituisce una lista di settori non cancellati in base ad un nome",description = "Serve a effettuare la ricerca di una lista di settori nel database,tramite un nome passato come parametro al metodo. Se viene trovata risponderà 302, se non viene trovata viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece il nome è una stringa vuota risponderà con un codice 400")
		@ApiResponses(value={
				@ApiResponse(description = "la lista di settori è stata trovata",
							responseCode = "FOUND(302)",
							content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
				@ApiResponse(description = "La lista di settori non è stata stata trovata per il nome passato al metodo",
							responseCode = "NOTFOUND(404)",
							content = @Content(mediaType = MediaType.ALL_VALUE)),
				@ApiResponse(description = "il nome non può essere una stringa vuota",
				responseCode = "BADREQUEST(400)",
				content = @Content(mediaType = MediaType.ALL_VALUE))
		})
	@GetMapping("/allByNome/{nome}")
	public ResponseEntity<List<Settore>> findAllByNomeAndIsCancellatoFalse(@NotBlank(message = "Il campo nome non può essere lasciato vuoto")
																			   @PathVariable("nome") String nome){
		List<Settore> settori = settoreService.findAllByNomeAndIsCancellatoFalse(nome);
		return ResponseEntity.ok(settori);
	}
    
	 @Operation(summary = "Elimina un settore per id(setta la variabile isCancellato a true)",description = "Serve a simulare l'eliminazione di un settore nel database,tramite l'id passato al metodo. Se viene eliminato risponderà 200, se non viene trovato viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece l'id è minore di 1 è un id non valido e quindi risponderà con un codice 400")
		@ApiResponses(value={
				@ApiResponse(description = "Il settore è stato eliminato",
							responseCode = "SUCCESS(200)",
							content = @Content(mediaType = MediaType.ALL_VALUE)),
				@ApiResponse(description = "Il settore non è stato stato eliminato a causa dell'id passato al metodo",
							responseCode = "NOTFOUND(404)",
							content = @Content(mediaType = MediaType.ALL_VALUE)),
				@ApiResponse(description = "l'ID deve essere un numero positivo",
				responseCode = "BADREQUEST(400)",
				content = @Content(mediaType = MediaType.ALL_VALUE))
				
		})
	@PutMapping("/deleteSettore/{id}")
	public ResponseEntity<String> deleteSettore(@Positive(message = "Il campo ID deve essere un numero positivo")
													@PathVariable("id") long id){
		settoreService.deleteSettore(id);
		return ResponseEntity.ok("Settore eliminato");
	}

    
	 @Operation(summary = "Trova una lista di settori per id di un luogo",description = "Serve a cercare una lista di settori nel database,tramite l'id del luogo passato al metodo. Se viene trovata risponderà 200, se non viene trovata viene lanciata un'eccezione e l'endpoint risponderà con status code 404, se invece l'id è minore di 1 è un id non valido e quindi risponderà con un codice 400")
		@ApiResponses(value={
				@ApiResponse(description = "la lista di settori è stata trovata",
							responseCode = "FOUND(400)",
							content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Settore.class))),
				@ApiResponse(description = "Non esiste nessun settore con questo idLuogo",
							responseCode = "NOTFOUND(404)",
							content = @Content(mediaType = MediaType.ALL_VALUE)),
				@ApiResponse(description = "l'idLuogo deve essere un numero positivo",
				responseCode = "BADREQUEST(400)",
				content = @Content(mediaType = MediaType.ALL_VALUE))
				
		})
	@GetMapping("/findByIdLuogo")
	public ResponseEntity<List<Settore>> findByIdLuogo(@Positive(message = "Il campo ID deve essere un numero positivo")
														   @RequestParam("idLuogo") long idLuogo){
		return ResponseEntity.status(FOUND).body(settoreService.findAllByIdLuogo(idLuogo));
	}

	 @Operation(summary = "Trova la lista completa dei settori nel db",description = "Serve a restituire la lista completa dei settori presenti nel db. Anche se non ci sono settori nel db il metodo restitueerà una lista vuota e risponderà con status code 302")
	 	@ApiResponse(description = "la lista di settori è stata trovata",
	 				responseCode = "FOUND(302)",
	 				content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = Settore.class)))
	@GetMapping("/findAll")
	public ResponseEntity<List<Settore>> findAll(){
		return ResponseEntity.status(FOUND).body(settoreService.findAll());
	}
}
