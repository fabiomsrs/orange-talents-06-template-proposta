package br.com.zupacademy.fabiano.proposta.controller;

import br.com.zupacademy.fabiano.proposta.dto.BiometriaRegisterDto;
import br.com.zupacademy.fabiano.proposta.dto.ProdutoDetalheDto;
import br.com.zupacademy.fabiano.proposta.modelo.Biometria;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.repository.BiometriaRepository;
import br.com.zupacademy.fabiano.proposta.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
}
