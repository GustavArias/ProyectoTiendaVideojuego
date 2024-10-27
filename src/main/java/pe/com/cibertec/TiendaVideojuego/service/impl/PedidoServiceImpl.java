package pe.com.cibertec.TiendaVideojuego.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.TiendaVideojuego.model.entity.PedidoEntity;
import pe.com.cibertec.TiendaVideojuego.repository.PedidoRepository;
import pe.com.cibertec.TiendaVideojuego.service.PedidoService;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

	
	private final PedidoRepository pedidoRepository;
	
	@Override
	public void crearPedido(PedidoEntity pedidoEntity) {
		
		pedidoRepository.save(pedidoEntity);
		
	}

}
