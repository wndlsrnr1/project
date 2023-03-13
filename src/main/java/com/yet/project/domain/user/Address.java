package com.yet.project.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Address {
    Long uid;
    String country;
    String _do;
    String si;
    String streetAddress;
    String details;
}
