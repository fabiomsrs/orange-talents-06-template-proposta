package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.AvisoViagemRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.BiometriaRegisterDto;
import br.com.zupacademy.fabiano.proposta.modelo.AvisoViagem;
import br.com.zupacademy.fabiano.proposta.modelo.Biometria;
import br.com.zupacademy.fabiano.proposta.modelo.BloqueioCartao;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.repository.AvisoViagemRepository;
import br.com.zupacademy.fabiano.proposta.repository.BiometriaRepository;
import br.com.zupacademy.fabiano.proposta.repository.BloqueioCartaoRepository;
import br.com.zupacademy.fabiano.proposta.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
    @Autowired
    BiometriaRepository biometriaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    BloqueioCartaoRepository bloqueioCartaoRepository;

    @Autowired
    AvisoViagemRepository avisoViagemRepository;

    @Value("${cartao.host}")
    private String urlSistemaCartao;

    @Value("${sistema.nome}")
    private String nomeSistema;

    @PostMapping("/{id}/biometrias")
    public ResponseEntity<?> criarBoemetria(@PathVariable("id") Long id, @RequestBody @Valid BiometriaRegisterDto dto, UriComponentsBuilder uriBuilder){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if(optionalCartao.isPresent()){
            Biometria biometria = dto.converter(optionalCartao.get());
            biometriaRepository.save(biometria);
            URI uri = uriBuilder.path("/{id}/biometrias").buildAndExpand(biometria.getId()).toUri();
            return ResponseEntity.created(uri).body(biometria);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/bloqueios")
    public ResponseEntity<?> criarBloqueio(@PathVariable("id") Long id, HttpServletRequest request, @RequestHeader(value = "User-Agent") String userAgent, UriComponentsBuilder uriBuilder){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if(optionalCartao.isPresent()){
            Cartao cartao = optionalCartao.get();
            if(bloqueioCartaoRepository.findByCartao(cartao).isEmpty()){
                RestTemplate restTemplate = new RestTemplate();

                Map<String, Object> body = new HashMap<>();
                body.put("sistemaResponsavel", this.nomeSistema);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

                try {
                    restTemplate.postForEntity(this.urlSistemaCartao + "/" + cartao.getNumeroCartao() + "/bloqueios", httpEntity, String.class);
                    BloqueioCartao bloqueioCartao = new BloqueioCartao(userAgent,request.getRemoteAddr(),cartao);
                    bloqueioCartaoRepository.save(bloqueioCartao);
                    URI uri = uriBuilder.path("/{id}/bloqueios").buildAndExpand(bloqueioCartao.getId()).toUri();
                    return ResponseEntity.created(uri).body(bloqueioCartao);
                }catch (Exception e){
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "entidade ja cadastrada");
                }
            }
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "entidade ja cadastrada");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/avisos-viagem")
    public ResponseEntity<?> criarAvisoViagem(@PathVariable("id") Long id,
                                              HttpServletRequest request,
                                              @RequestHeader(value = "User-Agent") String userAgent,
                                              @RequestBody @Valid AvisoViagemRegisterDto dto,
                                              UriComponentsBuilder uriBuilder){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if(optionalCartao.isPresent()){
            Cartao cartao = optionalCartao.get();
            AvisoViagem avisoViagem = dto.converter(userAgent,request.getRemoteAddr(),cartao);
            avisoViagemRepository.save(avisoViagem);
            URI uri = uriBuilder.path("/{id}/avisos-viagem").buildAndExpand(avisoViagem.getId()).toUri();
            return ResponseEntity.created(uri).body(avisoViagem);
        }
        return ResponseEntity.notFound().build();
    }
}
