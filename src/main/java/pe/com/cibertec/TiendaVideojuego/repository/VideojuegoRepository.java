package pe.com.cibertec.TiendaVideojuego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.cibertec.TiendaVideojuego.model.entity.VideojuegoEntity;

@Repository
public interface VideojuegoRepository extends JpaRepository<VideojuegoEntity, Integer>{

	
}
