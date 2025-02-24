package br.com.fiap.fin_money_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {


    @RequestMapping(produces = "application/json", path = "/categories", method = {RequestMethod.GET})
    public String index(){
        return "ok";
    }

}
