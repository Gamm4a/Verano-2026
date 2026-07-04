package com.example.ResorceHub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recurso {
    private Long id;
    private String titulo;
    private String url;
    private String tipo;
    private String categoria;


}
