package br.com.fiap.fin_money_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Category;

@RestController
public class CategoryController {


    @GetMapping(path = "/categories")
    public Category index(){
        return new Category(1L, "Educação", "book");
    }

    @PostMapping("/categories")
    public void create(){
        System.out.println("Cadastrando categoria");

    }

}
