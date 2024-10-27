package pe.com.cibertec.TiendaVideojuego.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.TiendaVideojuego.model.entity.VideojuegoEntity;
import pe.com.cibertec.TiendaVideojuego.repository.VideojuegoRepository;
import pe.com.cibertec.TiendaVideojuego.service.VideojuegoService;

@Service
@RequiredArgsConstructor
public class VideojuegoServiceImpl implements VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    @Override
    public List<VideojuegoEntity> buscarTodosVideojuegos() {
        return videojuegoRepository.findAll();
    }

    @Override
    public VideojuegoEntity buscarVideojuegopPorId(Integer id) {
        return videojuegoRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarVideojuego(VideojuegoEntity videojuego) {
        if (videojuego.getVideojuegoId() != null && videojuegoRepository.existsById(videojuego.getVideojuegoId())) {
            // Actualizar videojuego existente
            VideojuegoEntity videojuegoExistente = videojuegoRepository.findById(videojuego.getVideojuegoId()).get();
            videojuegoExistente.setNombre(videojuego.getNombre());
            videojuegoExistente.setPrecio(videojuego.getPrecio());
            videojuegoExistente.setStock(videojuego.getStock());
            videojuegoExistente.setUrlImagen(videojuego.getUrlImagen());
            videojuegoRepository.save(videojuegoExistente);
        } else {
            // Crear nuevo videojuego
            videojuegoRepository.save(videojuego);
        }
    }

    @Override
    public void eliminarVideojuego(Integer id) {
        videojuegoRepository.deleteById(id);
    }

	@Override
	public void crearVideojuego(VideojuegoEntity videojuegoEntity) {
		
		videojuegoRepository.save(videojuegoEntity);
		
	}
}