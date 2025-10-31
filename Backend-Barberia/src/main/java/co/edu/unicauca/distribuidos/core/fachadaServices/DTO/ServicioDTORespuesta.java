package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDTORespuesta {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private Integer duracion;
    private String imagenUrl;
    private Boolean estado;

    private CategoriaDTORespuesta objCategoria;
}