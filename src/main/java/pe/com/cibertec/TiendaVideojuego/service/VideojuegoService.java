package pe.com.cibertec.TiendaVideojuego.service;

import java.util.List;
import pe.com.cibertec.TiendaVideojuego.model.entity.VideojuegoEntity;

public interface VideojuegoService {

	void crearVideojuego(VideojuegoEntity videojuegoEntity);
    List<VideojuegoEntity> buscarTodosVideojuegos();
    VideojuegoEntity buscarVideojuegopPorId(Integer id);
    void guardarVideojuego(VideojuegoEntity videojuego);
    void eliminarVideojuego(Integer id);
}
