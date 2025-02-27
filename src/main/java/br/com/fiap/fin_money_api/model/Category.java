package br.com.fiap.fin_money_api.model;

import java.util.Random;

public class Category {
    private long id;
    private String name;
    private String icon;

    public Category(long id, String name, String icon) {
        this.id = Math.abs(new Random().nextLong()); //Gera uma id aleatória automaticamente
        this.name = name;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
    
}
