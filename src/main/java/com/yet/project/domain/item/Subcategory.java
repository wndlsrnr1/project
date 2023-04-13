package com.yet.project.domain.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Subcategory {
    Long id;
    String name;
    String nameKor;
}
