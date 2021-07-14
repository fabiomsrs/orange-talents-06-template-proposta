package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.AvisoViagemRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.BiometriaRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.CarteiraRegisterDto;
import br.com.zupacademy.fabiano.proposta.modelo.*;
import br.com.zupacademy.fabiano.proposta.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.time.format.DateTimeFormatter;
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
    CarteiraRepository carteiraRepository;

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
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("destino", avisoViagem.getDestinho());
            body.put("validoAte", avisoViagem.getTerminoViagem().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

            try {
                restTemplate.postForEntity(this.urlSistemaCartao + "/" + cartao.getNumeroCartao() + "/avisos", httpEntity, String.class);
                avisoViagemRepository.save(avisoViagem);
                URI uri = uriBuilder.path("/{id}/avisos-viagem").buildAndExpand(avisoViagem.getId()).toUri();
                return ResponseEntity.created(uri).body(avisoViagem);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "requisição no sistema externo de cartões deu errado, tente novamente");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/carteiras")
    public ResponseEntity<?> criarCarteira(@PathVariable("id") Long id,
                                          @RequestBody @Valid CarteiraRegisterDto dto,
                                          UriComponentsBuilder uriBuilder){
        Optional<Cartao> optionalCartao = cartaoRepository.findById(id);

        if(optionalCartao.isPresent()){
            Cartao cartao = optionalCartao.get();
            Carteira carteira = dto.converter(cartao);
            if(!carteiraRepository.findByCartaoAndTipoCarteira(cartao, carteira.getTipoCarteira()).isEmpty()){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "cartão ja cadastrado com essa carteira");
            }

            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("email", carteira.getEmail());
            body.put("carteira", carteira.getTipoCarteira());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

            try {
                restTemplate.postForEntity(this.urlSistemaCartao + "/" + cartao.getNumeroCartao() + "/carteiras", httpEntity, String.class);
                carteiraRepository.save(carteira);
                URI uri = uriBuilder.path("/{id}/carteiras").buildAndExpand(carteira.getId()).toUri();
                return ResponseEntity.created(uri).body(carteira);
            }catch (Exception e){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "requisição no sistema externo de cartões deu errado, tente novamente");
            }
        }
        return ResponseEntity.notFound().build();
    }
}
