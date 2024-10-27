package pe.com.cibertec.TiendaVideojuego.service;

import org.springframework.web.multipart.MultipartFile;

import pe.com.cibertec.TiendaVideojuego.model.entity.UsuarioEntity;

public interface UsuarioService {

	void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto);
	boolean validarUsuario(UsuarioEntity usuarioEntity);
	UsuarioEntity buscarUsuarioPorCorreo(String correo);
}
