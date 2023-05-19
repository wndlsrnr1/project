package com.yet.project.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;

@Data
@EqualsAndHashCode
public class Category {
    Long id;
    String name;
    String nameKor;
}
