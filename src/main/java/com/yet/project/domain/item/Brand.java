package com.yet.project.domain.item;

import lombok.Data;

@Data
public class Brand {
    Long id;
    String name;
    String nameKor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
