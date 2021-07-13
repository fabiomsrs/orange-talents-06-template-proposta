package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.BiometriaRegisterDto;
import br.com.zupacademy.fabiano.proposta.modelo.Biometria;
import br.com.zupacademy.fabiano.proposta.modelo.BloqueioCartao;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.repository.BiometriaRepository;
import br.com.zupacademy.fabiano.proposta.repository.BloqueioCartaoRepository;
import br.com.zupacademy.fabiano.proposta.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
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
            if(bloqueioCartaoRepository.findByCartao(optionalCartao.get()).isEmpty()){
                BloqueioCartao bloqueioCartao = new BloqueioCartao(userAgent,request.getRemoteAddr(),optionalCartao.get());
                bloqueioCartaoRepository.save(bloqueioCartao);
                URI uri = uriBuilder.path("/{id}/bloqueios").buildAndExpand(bloqueioCartao.getId()).toUri();
                return ResponseEntity.created(uri).body(bloqueioCartao);
            }
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "entidade ja cadastrada");
        }
        return ResponseEntity.notFound().build();
    }
}
