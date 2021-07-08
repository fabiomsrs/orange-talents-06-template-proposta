package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.PropostaRegisterDto;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    @Autowired
    PropostaRepository repository;

    @PostMapping
    public ResponseEntity<Proposta> criar(@RequestBody @Valid PropostaRegisterDto dto, UriComponentsBuilder uriBuilder){
        Proposta proposta = dto.converter();
        repository.save(proposta);
        URI uri = uriBuilder.path("/apostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(proposta);
    }
}
