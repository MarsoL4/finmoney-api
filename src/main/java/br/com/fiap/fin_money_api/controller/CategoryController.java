package br.com.fiap.fin_money_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Category;

@RestController
public class CategoryController {

    private List<Category> repository = new ArrayList<>();

    @GetMapping(path = "/categories")
    public List<Category> index(){
        return repository;
    }

    @PostMapping("/categories")
    @ResponseStatus(code = HttpStatus.CREATED) //Status que será passado em caso de sucesso ao Postman
    public Category create(@RequestBody Category category){
        System.out.println("Cadastrando categoria " + category.getName());
        repository.add(category);
        return category;
    }


}
