package br.com.fiap.fin_money_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.fin_money_api.model.Category;
import br.com.fiap.fin_money_api.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DataBaseSeeder { //Semeia o Banco de Dados com inserções padrões em toda inicialização do projeto
    
    @Autowired 
    private CategoryRepository categoryRepository;

    @PostConstruct //Será executado depois da injeção de dependências e inicialização do projeto
    public void init(){
        categoryRepository.saveAll(List.of(
            Category.builder().name("Educação").icon("Book").build(),
            Category.builder().name("Lazer").icon("Dices").build(),
            Category.builder().name("Transporte").icon("Bus").build()
        ));
    }

}
