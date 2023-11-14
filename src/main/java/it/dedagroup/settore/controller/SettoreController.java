package it.dedagroup.settore.controller;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.DTO.response.SettoreResponse;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import java.util.List;
import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settore")
@AllArgsConstructor
public class SettoreController {

	private final SettoreServiceImplementation settoreService;
	private final SettoreMapper settoreMapper;

	@PostMapping("/aggiungiSettore")
	public ResponseEntity<SettoreResponse> aggiungiSettore(@RequestBody SettoreRequest request){
		Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
		return ResponseEntity.status(HttpStatus.OK).body(settoreMapper.toSettoreResponseFromSettore(newSettore));
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
	
	@GetMapping("/allByPosti/{nome}")
	public ResponseEntity<List<SettoreResponse>> findAllByNomeAndIsCancellatoFalsedAllByPosti(@PathVariable("nome") String nome){
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
