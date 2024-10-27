package pe.com.cibertec.TiendaVideojuego.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	private Integer cantidad;
	private Integer videojuegosId;
}
