package br.com.fiap.fin_money_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Category;

@RestController
public class CategoryController {

    private List<Category> repository = new ArrayList<>();

    //Busca todos registros
    @GetMapping(path = "/categories")
    public List<Category> index(){
        return repository;
    }

    //Insere um registro manual (via body)
    @PostMapping("/categories")
    @ResponseStatus(code = HttpStatus.CREATED) //Status que será passado em caso de sucesso ao Postman
    public Category create(@RequestBody Category category){
        System.out.println("Cadastrando categoria " + category.getName());
        repository.add(category);
        return category;
    }

    //Busca um registro específico pelo id
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id){
        System.out.println("Buscando Categoria " + id);
        var category = repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (category.isEmpty()){ //se estiver vazia
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(category.get()); //se existir
    }
    


}
