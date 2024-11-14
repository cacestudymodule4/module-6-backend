package com.example.module_6_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    private String q = "";
    private int page = 0;
    private int size = 10;
    private String sort = "id";
    private String sortDir = "asc";
}
