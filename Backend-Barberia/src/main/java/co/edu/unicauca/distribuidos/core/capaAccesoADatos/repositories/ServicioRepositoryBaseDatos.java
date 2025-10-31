package co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ServicioBarberiaEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.conexion.ConexionBD;

@Repository
public class ServicioRepositoryBaseDatos {
    private final ConexionBD conexionABaseDeDatos;

    public ServicioRepositoryBaseDatos() {
        conexionABaseDeDatos = new ConexionBD();
    }

    public ServicioBarberiaEntity save(ServicioBarberiaEntity objServicio) {
        System.out.println("registrando servicio en base de datos");
        ServicioBarberiaEntity objServicioAlmacenado = null;
        int resultado = -1;

        try {
            conexionABaseDeDatos.conectar();

            PreparedStatement sentencia = null;
            // ✅ AGREGAR ESTADO
            String consulta = "INSERT INTO servicios(NOMBRE, DESCRIPCION, PRECIO, DURACION, IMAGENURL, IDCATEGORIA, ESTADO) VALUES(?,?,?,?,?,?,?)";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, objServicio.getNombre());
            sentencia.setString(2, objServicio.getDescripcion());
            sentencia.setDouble(3, objServicio.getPrecio());
            sentencia.setInt(4, objServicio.getDuracion());
            sentencia.setString(5, objServicio.getImagenUrl());

            if (objServicio.getObjCategoria() != null) {
                sentencia.setInt(6, objServicio.getObjCategoria().getId());
            } else {
                sentencia.setNull(6, java.sql.Types.INTEGER);
            }

            // ✅ ESTABLECER ESTADO (por defecto true si es null)
            sentencia.setBoolean(7, objServicio.getEstado() != null ? objServicio.getEstado() : true);

            resultado = sentencia.executeUpdate();

            ResultSet generatedKeys = sentencia.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idGenerado = generatedKeys.getInt(1);
                objServicio.setId(idGenerado);
                System.out.println("ID generado: " + idGenerado);
                if (resultado == 1) {
                    objServicioAlmacenado = this.findById(idGenerado).orElse(null);
                }
            } else {
                System.out.println("No se pudo obtener el ID generado.");
            }

            generatedKeys.close();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la inserción: " + e.getMessage());
        }

        return objServicioAlmacenado;
    }

    public Optional<Collection<ServicioBarberiaEntity>> findAll() {
        System.out.println("listando servicios de base de datos");
        Collection<ServicioBarberiaEntity> servicios = new LinkedList<ServicioBarberiaEntity>();

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "SELECT servicios.*, categorias.nombreCategoria as nombreCategoria " +
                    "FROM servicios " +
                    "LEFT JOIN categorias ON servicios.IDCATEGORIA = categorias.ID";

            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            ResultSet res = sentencia.executeQuery();

            while (res.next()) {
                ServicioBarberiaEntity objServicio = new ServicioBarberiaEntity();
                objServicio.setId(res.getInt("ID"));
                objServicio.setNombre(res.getString("NOMBRE"));
                objServicio.setDescripcion(res.getString("DESCRIPCION"));
                objServicio.setPrecio(res.getFloat("PRECIO"));
                objServicio.setDuracion(res.getInt("DURACION"));
                objServicio.setImagenUrl(res.getString("IMAGENURL"));
                objServicio.setEstado(res.getBoolean("ESTADO")); // ✅ NUEVO

                Integer idCategoria = res.getObject("IDCATEGORIA", Integer.class);
                if (idCategoria != null) {
                    CategoriaEntity categoria = new CategoriaEntity(
                            idCategoria,
                            res.getString("nombreCategoria")
                    );
                    objServicio.setObjCategoria(categoria);
                }

                servicios.add(objServicio);
            }

            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la consulta: " + e.getMessage());
        }

        return servicios.isEmpty() ? Optional.empty() : Optional.of(servicios);
    }

    public Optional<ServicioBarberiaEntity> findById(Integer idServicio) {
        System.out.println("consultar servicio de base de datos");
        ServicioBarberiaEntity objServicio = null;

        conexionABaseDeDatos.conectar();
        try {
            PreparedStatement sentencia = null;
            String consulta = "SELECT servicios.*, categorias.nombreCategoria as nombreCategoria " +
                    "FROM servicios " +
                    "LEFT JOIN categorias ON servicios.IDCATEGORIA = categorias.ID " +
                    "WHERE servicios.ID = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idServicio);
            ResultSet res = sentencia.executeQuery();

            if (res.next()) {
                System.out.println("servicio encontrado");
                objServicio = new ServicioBarberiaEntity();
                objServicio.setId(res.getInt("ID"));
                objServicio.setNombre(res.getString("NOMBRE"));
                objServicio.setDescripcion(res.getString("DESCRIPCION"));
                objServicio.setPrecio(res.getFloat("PRECIO"));
                objServicio.setDuracion(res.getInt("DURACION"));
                objServicio.setImagenUrl(res.getString("IMAGENURL"));
                objServicio.setEstado(res.getBoolean("ESTADO")); // ✅ NUEVO

                Integer idCategoria = res.getObject("IDCATEGORIA", Integer.class);
                if (idCategoria != null) {
                    objServicio.setObjCategoria(
                            new CategoriaEntity(idCategoria, res.getString("nombreCategoria"))
                    );
                }
            }

            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la consulta: " + e.getMessage());
        }

        return objServicio == null ? Optional.empty() : Optional.of(objServicio);
    }

    public Optional<ServicioBarberiaEntity> update(Integer idServicio, ServicioBarberiaEntity objServicio) {
        System.out.println("actualizar servicio de base de datos");
        ServicioBarberiaEntity objServicioActualizado = null;
        conexionABaseDeDatos.conectar();
        int resultado = -1;

        try {
            PreparedStatement sentencia = null;
            String consulta = "UPDATE servicios SET servicios.NOMBRE = ?, " +
                    "servicios.DESCRIPCION = ?, " +
                    "servicios.PRECIO = ?, " +
                    "servicios.DURACION = ?, " +
                    "servicios.IMAGENURL = ?, " +
                    "servicios.IDCATEGORIA = ?, " +
                    "servicios.ESTADO = ? " + // ✅ NUEVO
                    "WHERE servicios.ID = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);

            sentencia.setString(1, objServicio.getNombre());
            sentencia.setString(2, objServicio.getDescripcion());
            sentencia.setDouble(3, objServicio.getPrecio());
            sentencia.setInt(4, objServicio.getDuracion());
            sentencia.setString(5, objServicio.getImagenUrl());

            if (objServicio.getObjCategoria() != null) {
                sentencia.setInt(6, objServicio.getObjCategoria().getId());
            } else {
                sentencia.setNull(6, java.sql.Types.INTEGER);
            }

            // ✅ ESTABLECER ESTADO
            sentencia.setBoolean(7, objServicio.getEstado() != null ? objServicio.getEstado() : true);
            sentencia.setInt(8, idServicio);

            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la actualización: " + e.getMessage());
        }

        if (resultado == 1) {
            objServicioActualizado = this.findById(idServicio).orElse(null);
        }

        return objServicioActualizado == null ? Optional.empty() : Optional.of(objServicioActualizado);
    }

    public boolean delete(Integer idServicio) {
        System.out.println("eliminar servicio de base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;

        try {
            PreparedStatement sentencia = null;
            String consulta = "delete from servicios where servicios.id=?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setInt(1, idServicio);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error en la eliminación: " + e.getMessage());
        }

        return resultado == 1;
    }

    // ✅ MÉTODO ADICIONAL: Cambiar estado sin eliminar
    public boolean cambiarEstado(Integer idServicio, Boolean nuevoEstado) {
        System.out.println("cambiando estado del servicio en base de datos");
        conexionABaseDeDatos.conectar();
        int resultado = -1;

        try {
            PreparedStatement sentencia = null;
            String consulta = "UPDATE servicios SET servicios.ESTADO = ? WHERE servicios.ID = ?";
            sentencia = conexionABaseDeDatos.getConnection().prepareStatement(consulta);
            sentencia.setBoolean(1, nuevoEstado);
            sentencia.setInt(2, idServicio);
            resultado = sentencia.executeUpdate();
            sentencia.close();
            conexionABaseDeDatos.desconectar();

        } catch (SQLException e) {
            System.out.println("error al cambiar estado: " + e.getMessage());
        }

        return resultado == 1;
    }
}