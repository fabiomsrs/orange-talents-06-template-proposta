package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.PropostaRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.SolicitacaoDto;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.modelo.StatusProposta;
import br.com.zupacademy.fabiano.proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    @Autowired
    PropostaRepository repository;

    @PostMapping
    public ResponseEntity<Proposta> criar(@RequestBody @Valid PropostaRegisterDto dto, UriComponentsBuilder uriBuilder){
        Proposta proposta = dto.converter();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9999/api/solicitacao";

        Map<String, Object> body = new HashMap<>();
        body.put("documento", proposta.getDocumento());
        body.put("nome", proposta.getNome());
        body.put("idProposta", proposta.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<SolicitacaoDto> response = restTemplate.postForEntity(url,httpEntity,SolicitacaoDto.class);
            SolicitacaoDto solicitacaoDto = response.getBody();
            proposta.setStatus(StatusProposta.converter(solicitacaoDto.getResultadoSolicitacao()));
            repository.save(proposta);
        }catch (Exception e){
            System.out.println(e);
        }

        URI uri = uriBuilder.path("/apostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(proposta);
    }
}
