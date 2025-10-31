
package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IServicioBarberiaService;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IServicioBarberiaService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ServicioRestController {

	@Autowired	//Otra forma de inyectar un objeto del contenedor de Spring
	private IServicioBarberiaService servicioBarberiaService;

	@GetMapping("/Servicios")
	public List<ServicioDTORespuesta> listarServicios() {			
		return servicioBarberiaService.findAll();
	}

	@GetMapping("/Servicios/{id}")
	public ServicioDTORespuesta consultarServicio(@PathVariable Integer id) {
		ServicioDTORespuesta objServicio = null;
		objServicio = servicioBarberiaService.findById(id);
		return objServicio;
	}

	@PostMapping("/Servicios")
	public ServicioDTORespuesta crearServicio(@RequestBody ServicioDTOPeticion servicio) {
		ServicioDTORespuesta objServicio = null;
		objServicio = servicioBarberiaService.save(servicio);
		return objServicio;
	}

	@PutMapping("/Servicios/editar/{id}")
	public ServicioDTORespuesta actualizarServicio(@RequestBody ServicioDTOPeticion servicio, @PathVariable Integer id) {
		ServicioDTORespuesta objServicio = null;
		ServicioDTORespuesta servicioActual = servicioBarberiaService.findById(id);
		if (servicioActual != null) {
			objServicio = servicioBarberiaService.update(id, servicio);
		}
		return objServicio;
	}

	@DeleteMapping("/Servicios/{id}")
	public Boolean eliminarServicio(@PathVariable Integer id) {
		Boolean bandera = false;
		ServicioDTORespuesta servicioActual = servicioBarberiaService.findById(id);
		if (servicioActual != null) {
			bandera = servicioBarberiaService.delete(id);
		}
		return bandera;
	}
	
}
