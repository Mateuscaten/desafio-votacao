package desafio.votacao.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class DesafioVotacaoService {

    public static void main(String[] args) {
        SpringApplication.run(DesafioVotacaoService.class, args);
    }

}
