package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.PropostaDetalheDto;
import br.com.zupacademy.fabiano.proposta.dto.PropostaRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.SolicitacaoDto;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.modelo.StatusProposta;
import br.com.zupacademy.fabiano.proposta.repository.PropostaRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    @Autowired
    PropostaRepository repository;

    @Value("${solicitacao.host}")
    private String urlSistemaSolicitacao;

    private final Tracer tracer;

    public PropostaController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<Proposta> criar(@RequestBody @Valid PropostaRegisterDto dto, UriComponentsBuilder uriBuilder){
        tracer.activeSpan();
        Proposta proposta = dto.converter();
        repository.save(proposta);
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("documento", proposta.getDocumento());
        body.put("nome", proposta.getNome());
        body.put("idProposta", proposta.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<SolicitacaoDto> response = restTemplate.postForEntity(this.urlSistemaSolicitacao,httpEntity,SolicitacaoDto.class);
            SolicitacaoDto solicitacaoDto = response.getBody();
            proposta.setStatus(StatusProposta.converter(solicitacaoDto.getResultadoSolicitacao()));
            repository.save(proposta);
        }catch (Exception e){
            System.out.println(e);
        }
        URI uri = uriBuilder.path("/apostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).body(proposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalheProposta(@PathVariable("id") String id){
        tracer.activeSpan();
        Optional<Proposta> optionalProposta = repository.findById(id);

        if(optionalProposta.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PropostaDetalheDto(optionalProposta.get()));
    }
}
