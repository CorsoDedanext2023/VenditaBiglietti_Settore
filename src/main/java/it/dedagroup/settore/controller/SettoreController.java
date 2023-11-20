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


    @GetMapping("/findById")
    public ResponseEntity<Settore> findById(@Positive(message = "Il campo ID deve essere un numero positivo")
												@RequestParam("id") long id ){
        Settore s = settoreService.findById(id);
        return ResponseEntity.status(FOUND).body(s);
    }


    @GetMapping("/findByIdEsistenti")
    public ResponseEntity<Settore> findByIdAndIsCancellatoFalse(@Positive(message = "Il campo ID deve essere un numero positivo")
																	@RequestParam("id") long id ){
        Settore s = settoreService.findByIdAndIsCancellatoFalse(id);
        return ResponseEntity.status(FOUND).body(s);
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


	@PostMapping("/deleteSettore/{id}")
	public ResponseEntity<String> deleteSettore(@Positive(message = "Il campo ID deve essere un numero positivo")
													@PathVariable("id") long id){
		settoreService.deleteSettore(id);
		return ResponseEntity.ok("Settore eliminato");
	}


	@GetMapping("/findAllByIdLuogo")
	public ResponseEntity<List<Settore>> findAllByIdLuogo(@Min(value= 1,message = "Il campo ID deve essere un numero positivo")
														   @RequestParam("idLuogo") long idLuogo){
		return ResponseEntity.status(FOUND).body(settoreService.findAllByIdLuogo(idLuogo));
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Settore>> findAll(){
		return ResponseEntity.status(FOUND).body(settoreService.findAll());
	}
}
