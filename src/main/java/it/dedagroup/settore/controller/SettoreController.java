package it.dedagroup.settore.controller;

import it.dedagroup.settore.DTO.request.SettoreRequest;
import it.dedagroup.settore.DTO.response.SettoreResponse;
import it.dedagroup.settore.mapper.SettoreMapper;
import it.dedagroup.settore.model.Settore;
import static org.springframework.http.HttpStatus.*;

import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settore")
@NoArgsConstructor
public class SettoreController {
    @Autowired
    SettoreServiceImplementation settoreService;
    @Autowired
    SettoreMapper settoreMapper;
    @GetMapping("/aggiungiSettore")
    public ResponseEntity<SettoreResponse> aggiungiSettore(@RequestBody SettoreRequest request){
        Settore newSettore = settoreService.saveSettore(settoreMapper.toSettoreFromRequest(request));
        return ResponseEntity.status(HttpStatus.OK).body(settoreMapper.toSettoreResponseFromSettore(newSettore));
    }
}
