package com.projeto.plataforma.web.dto;


import lombok.*;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private List<DisciplinaDTO> disciplinasRelacionadas;
    private Boolean ativo;

}
