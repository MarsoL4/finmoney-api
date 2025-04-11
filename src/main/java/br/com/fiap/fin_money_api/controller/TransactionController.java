package br.com.fiap.fin_money_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Transaction;
import br.com.fiap.fin_money_api.repository.TransactionRepository;
import br.com.fiap.fin_money_api.specification.TransactionSpecification;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("transactions")
@Slf4j
public class TransactionController {

    public record TransactionFilters(String description, LocalDate startDate, LocalDate endDate) {}

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public Page<Transaction> index(
        TransactionFilters filters,
        @PageableDefault(size = 10, sort = "date", direction = Direction.DESC) Pageable pageable 
        // @RequestParam(required = false) String description,
        // @RequestParam(required = false) LocalDate date
    ){
        //Forma 1:
        // log.info("Buscando transações com descrição: {} e date {}", description, date);

        // if (description != null && date != null) return repository.findByDescriptionContainingIgnoringCaseAndDate(description, date,  pageable);

        // if (description != null ) return repository.findByDescriptionContainingIgnoringCase(description, pageable);

        // if (date != null) return repository.findByDate(date, pageable);

        //Forma 2:
        // var probe = Transaction.builder()
        //                 .description(description)
        //                 .date(date)
        //                 .build();  
        
        // var matcher = ExampleMatcher.matchingAll()
        //                 .withIgnoreCase()
        //                 .withStringMatcher(StringMatcher.CONTAINING);
        
        // var example = Example.of(probe, matcher);

        var specification = TransactionSpecification.withFilters(filters);

        return repository.findAll(specification, pageable);
    }

}