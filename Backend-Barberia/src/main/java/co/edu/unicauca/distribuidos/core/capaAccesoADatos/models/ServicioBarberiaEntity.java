package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServicioBarberiaEntity {
	private Integer id;
	private String nombre;
	private String descripcion;
	private Float precio;
	private Integer duracion;
	private String imagenUrl;
	private Boolean estado;

	private CategoriaEntity objCategoria;

	public ServicioBarberiaEntity() {
	}
}