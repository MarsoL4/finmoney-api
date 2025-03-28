package br.com.fiap.fin_money_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data //Vai gerar getters, setters, etc para essa classe (lombok)
public class Category {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //Define o id como PK e que será gerado automaticamente
    private Long id;

    @NotBlank //Não pode estar em branco
    @Size(min = 3) //Mínimo de Caracteres
    private String name;

    @NotBlank(message = "Não Ppode estar em branco")
    @Pattern(regexp = "^[A-Z].*$", message = "Deve começar com letra maiúscula") //Deve iniciar com letra Maiúscula
    private String icon;
    
}
