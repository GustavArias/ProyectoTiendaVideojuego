package pe.com.cibertec.TiendaVideojuego.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.TiendaVideojuego.model.entity.DetallePedidoEntity;
import pe.com.cibertec.TiendaVideojuego.model.entity.Pedido;
import pe.com.cibertec.TiendaVideojuego.model.entity.PedidoEntity;
import pe.com.cibertec.TiendaVideojuego.model.entity.UsuarioEntity;
import pe.com.cibertec.TiendaVideojuego.model.entity.VideojuegoEntity;
import pe.com.cibertec.TiendaVideojuego.service.PedidoService;


@Controller
@RequiredArgsConstructor
public class PedidoController {
	
private final PedidoService pedidoService;
	
	@GetMapping("/guardar_factura")
	public String guardarFactura(HttpSession session) {
		String correoUsuario = session.getAttribute("usuario").toString();
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setCorreo(correoUsuario);
		
		
		PedidoEntity pedidoEntity = new PedidoEntity();
		pedidoEntity.setFechaCompra(LocalDate.now());
		pedidoEntity.setUsuarioEntity(usuarioEntity);
		
		
		List<DetallePedidoEntity>detallePedidoEntityList = new ArrayList<DetallePedidoEntity>();
		
		List<Pedido>videojuegoSesion = null;
		if(session.getAttribute("carrito") == null) {
			videojuegoSesion = new ArrayList<Pedido>();
		}else {
			videojuegoSesion = (List<Pedido>)session.getAttribute("carrito");
		}
	
		for(Pedido ped: videojuegoSesion) {
			DetallePedidoEntity detallePedidoEntity = new DetallePedidoEntity();
			VideojuegoEntity videojuegoEntity = new VideojuegoEntity();
			videojuegoEntity.setVideojuegoId(ped.getVideojuegosId());
			
			detallePedidoEntity.setVideojuegoEntity(videojuegoEntity);
			detallePedidoEntity.setCantidad(ped.getCantidad());
			detallePedidoEntity.setPedidoEntity(pedidoEntity);
			detallePedidoEntityList.add(detallePedidoEntity);
		}
		
		pedidoEntity.setDetallePedido(detallePedidoEntityList);
		pedidoService.crearPedido(pedidoEntity);
		session.removeAttribute("carrito");
		return "redirect:/menu";
		
	}
	

}
