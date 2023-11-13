package it.dedagroup.settore.controller;

import it.dedagroup.settore.model.Settore;
import static org.springframework.http.HttpStatus.*;

import it.dedagroup.settore.service.impl.SettoreServiceImplementation;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settore")
@NoArgsConstructor
public class SettoreController {

    public static SettoreServiceImplementation settoreService;
}
