package br.com.fiap.fin_money_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data //Vai gerar getters, setters, etc para essa classe (lombok)
public class Category {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //Define o id como PK e que será gerado automaticamente
    private Long id;
    private String name;
    private String icon;
    
    /*
    //Boilerplate - clichê:
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", icon=" + icon + "]";
    }
    */
    
}
