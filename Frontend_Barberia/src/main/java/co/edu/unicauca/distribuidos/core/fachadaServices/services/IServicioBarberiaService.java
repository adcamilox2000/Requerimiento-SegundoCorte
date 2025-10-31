package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;

public interface IServicioBarberiaService {

	public List<ServicioDTORespuesta> findAll();

	public ServicioDTORespuesta findById(Integer id);

	public ServicioDTORespuesta save(ServicioDTOPeticion servicioBarberia);

	public ServicioDTORespuesta update(Integer id, ServicioDTOPeticion servicioBarberia);

	public boolean delete(Integer id);

	// ✅ NUEVO MÉTODO
	public boolean cambiarEstado(Integer id, Boolean nuevoEstado);
}