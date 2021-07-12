package br.com.zupacademy.fabiano.proposta.utils;

import br.com.zupacademy.fabiano.proposta.dto.CartaoDto;
import br.com.zupacademy.fabiano.proposta.modelo.Cartao;
import br.com.zupacademy.fabiano.proposta.modelo.Proposta;
import br.com.zupacademy.fabiano.proposta.modelo.StatusProposta;
import br.com.zupacademy.fabiano.proposta.repository.CartaoRepository;
import br.com.zupacademy.fabiano.proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableAsync
@EnableScheduling
public class CartaoPropostaSchedule {
    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    @Value("${conta.host}")
    private String urlSistemaConta;
    /*
    * 1 - Faz um get na url de contas requisitando todos os cartões
    * 2 - Busca todas as propostas aprovadas sem cartão
    * 3 - Para cada proposta aprovada sem cartão, executar um filter na lista de cartão pelo id de propostas
    * salvando esse cartão e atribuindo-o à proposta
    * 4 - salvando todas as propostas
    * */
    @Scheduled(fixedDelay=60000)
    public void servicoAtribuicaoCartao() {
        WebClient client = WebClient.create();
        List<CartaoDto> cartoes = client.get().uri(this.urlSistemaConta).retrieve().bodyToFlux(CartaoDto.class).buffer().blockFirst();
        List<Proposta> propostas = propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);

        if(!propostas.isEmpty()){
            propostas.forEach(proposta -> {
                Optional<CartaoDto> dto = cartoes.stream().filter(cartao -> proposta.getId().equals(cartao.getIdProposta())).findAny();
                if(dto.isPresent()){
                    Cartao cartao = dto.get().converter();
                    cartaoRepository.save(cartao);
                    proposta.setCartao(cartao);
                }
            });
            propostaRepository.saveAll(propostas);
        }

    }
}
