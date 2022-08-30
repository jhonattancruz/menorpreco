package com.menorvalor.menorpreco;

import com.menorvalor.menorpreco.service.MenorPrecoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MenorprecoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenorprecoApplication.class, args);

        MenorPrecoService menorPrecoService = new MenorPrecoService();
        menorPrecoService.executar();
    }
}
