package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ServicioBarberiaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ServicioRepositoryBaseDatos;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ServicioDTORespuesta;

@Service
public class ServicioBarberiaServiceImpl implements IServicioBarberiaService {

    private ServicioRepositoryBaseDatos servicioAccesoBaseDatos;
    private ModelMapper modelMapper;
    private CategoriaRepository categoriaRepository;

    public ServicioBarberiaServiceImpl(ServicioRepositoryBaseDatos servicioAccesoBaseDatos,
                                       ModelMapper modelMapper,
                                       CategoriaRepository categoriaRepository) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<ServicioDTORespuesta> findAll() {
        List<ServicioDTORespuesta> listaRetornar;
        Optional<Collection<ServicioBarberiaEntity>> serviciosEntityOpt = this.servicioAccesoBaseDatos.findAll();

        if (serviciosEntityOpt.isEmpty()) {
            listaRetornar = List.of();
        } else {
            Collection<ServicioBarberiaEntity> serviciosEntity = serviciosEntityOpt.get();
            listaRetornar = this.modelMapper.map(serviciosEntity, new TypeToken<List<ServicioDTORespuesta>>() {}.getType());
        }

        return listaRetornar;
    }

    @Override
    public ServicioDTORespuesta findById(Integer id) {
        ServicioDTORespuesta servicioRetornar = null;
        Optional<ServicioBarberiaEntity> optionalServicio = this.servicioAccesoBaseDatos.findById(id);

        if (optionalServicio.isPresent()) {
            ServicioBarberiaEntity servicioEntity = optionalServicio.get();
            servicioRetornar = this.modelMapper.map(servicioEntity, ServicioDTORespuesta.class);
        }

        return servicioRetornar;
    }

    @Override
    public ServicioDTORespuesta save(ServicioDTOPeticion servicio) {
        ServicioBarberiaEntity servicioBarberiaEntity = this.modelMapper.map(servicio, ServicioBarberiaEntity.class);

        // Buscar y asignar la categoría existente
        if (servicio.getObjCategoria() != null && servicio.getObjCategoria().getId() != null) {
            Optional<CategoriaEntity> categoriaOpt = this.categoriaRepository.findById(servicio.getObjCategoria().getId());
            if (categoriaOpt.isPresent()) {
                servicioBarberiaEntity.setObjCategoria(categoriaOpt.get());
            } else {
                throw new RuntimeException("Categoría no encontrada con ID: " + servicio.getObjCategoria().getId());
            }
        }

        // ✅ Establecer estado por defecto si no viene
        if (servicioBarberiaEntity.getEstado() == null) {
            servicioBarberiaEntity.setEstado(true);
        }

        ServicioBarberiaEntity objServicioGuardado = this.servicioAccesoBaseDatos.save(servicioBarberiaEntity);
        System.out.println("Servicio guardado: " + objServicioGuardado);

        ServicioDTORespuesta servicioDTO = this.modelMapper.map(objServicioGuardado, ServicioDTORespuesta.class);
        return servicioDTO;
    }

    @Override
    public ServicioDTORespuesta update(Integer id, ServicioDTOPeticion serviciobarberia) {
        ServicioBarberiaEntity servicioBarberiaEntity = null;
        Optional<ServicioBarberiaEntity> servicioEntityOp = this.servicioAccesoBaseDatos.findById(id);

        if (servicioEntityOp.isPresent()) {
            ServicioBarberiaEntity objServicioDatosNuevos = servicioEntityOp.get();

            objServicioDatosNuevos.setNombre(serviciobarberia.getNombre());
            objServicioDatosNuevos.setDescripcion(serviciobarberia.getDescripcion());
            objServicioDatosNuevos.setPrecio(serviciobarberia.getPrecio());
            objServicioDatosNuevos.setDuracion(serviciobarberia.getDuracion());
            objServicioDatosNuevos.setImagenUrl(serviciobarberia.getImagenUrl());
            objServicioDatosNuevos.setEstado(serviciobarberia.getEstado()); // ✅ NUEVO

            if (serviciobarberia.getObjCategoria() != null) {
                if (objServicioDatosNuevos.getObjCategoria() == null) {
                    objServicioDatosNuevos.setObjCategoria(new CategoriaEntity());
                }
                objServicioDatosNuevos.getObjCategoria().setId(serviciobarberia.getObjCategoria().getId());
            }

            Optional<ServicioBarberiaEntity> optionalServicio = this.servicioAccesoBaseDatos.update(id, objServicioDatosNuevos);

            if (optionalServicio.isPresent()) {
                servicioBarberiaEntity = optionalServicio.get();
            }
        }

        return this.modelMapper.map(servicioBarberiaEntity, ServicioDTORespuesta.class);
    }

    @Override
    public boolean delete(Integer id) {
        return this.servicioAccesoBaseDatos.delete(id);
    }

    // ✅ NUEVO MÉTODO: Cambiar estado
    public boolean cambiarEstado(Integer id, Boolean nuevoEstado) {
        return this.servicioAccesoBaseDatos.cambiarEstado(id, nuevoEstado);
    }
}