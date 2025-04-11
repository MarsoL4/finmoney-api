package br.com.fiap.fin_money_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_money_api.model.Category;
import br.com.fiap.fin_money_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("categories")
@Slf4j
public class CategoryController {
    
    //Injeta o repositório de categorias
    @Autowired
    private CategoryRepository repository;

    @GetMapping
    @Operation(
        summary = "Listar Categorias",
        description = "Retorna um array com todas as Categorias"
    )
    @Cacheable("categories") //Deixa essa requisição salva em memória para evitar repetição de SELECT e melhorar o desempenho da API
    public List<Category> index() {
        return repository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "categories", allEntries = true) //Invalida o cache salvo de categorias da última requisição GET e faz uma nova após o POST (Cadastro de uma Nova Categoria)
    @Operation(
        summary = "Cadastrar uma nova Categoria",
        //deprecated = true, //O endpoint aparece mas não é mais utilizado
        //hidden = true //O endpoint não aparece
        responses = @ApiResponse(responseCode = "400", description = "Validação Falhou")
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public Category create(@RequestBody @Valid Category category) {
        log.info("Cadastrando categoria " + category.getName());
        return repository.save(category);
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Buscar uma Categoria pelo ID",
        description = "Retorna os dados de uma Category",
        responses = @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    )
    public ResponseEntity<Category> get(@PathVariable Long id) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id));
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Deletar uma Categoria pelo ID",
        responses = @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    )
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        log.info("Deletando categoria " + id);
        repository.delete(getCategory(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(
        summary = "Atualizar uma Categoria pelo ID",
        responses = @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    )
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        log.info("Atualizando categoria " + id + " com " + category);

        getCategory(id);
        category.setId(id);
        repository.save(category);
        return ResponseEntity.ok(category);
    }

    //Valida e retorna a categoria existente ou lança erro 404
    private Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
                );
    }

}
