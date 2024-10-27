package pe.com.cibertec.TiendaVideojuego.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.TiendaVideojuego.model.entity.DetallePedidoEntity;
import pe.com.cibertec.TiendaVideojuego.model.entity.Pedido;
import pe.com.cibertec.TiendaVideojuego.model.entity.UsuarioEntity;
import pe.com.cibertec.TiendaVideojuego.model.entity.VideojuegoEntity;
import pe.com.cibertec.TiendaVideojuego.service.UsuarioService;
import pe.com.cibertec.TiendaVideojuego.service.VideojuegoService;
import pe.com.cibertec.TiendaVideojuego.service.impl.PdfService;



@Controller
@RequiredArgsConstructor
public class VideojuegoController {

    private final VideojuegoService videojuegoService;
    private final UsuarioService usuarioService;
    private final PdfService pdfService;

    @GetMapping("/menu")
    public String mostrarMenu(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String correoSesion = session.getAttribute("usuario").toString();
        UsuarioEntity usuarioEncontrado = usuarioService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("foto", usuarioEncontrado.getUrlImagen());

        List<VideojuegoEntity> listaVideojuego = videojuegoService.buscarTodosVideojuegos();
        model.addAttribute("videojuego", listaVideojuego);
        
        List<Pedido>videojuegoSesion = null;
        if(session.getAttribute("carrito") == null) {
        	videojuegoSesion = new ArrayList<Pedido>();
    	}else {
    		videojuegoSesion = (List<Pedido>)session.getAttribute("carrito");
    	}
        model.addAttribute("cant_carrito", videojuegoSesion.size());
        
        List<DetallePedidoEntity>detallePedidoEntity = new ArrayList<DetallePedidoEntity>();
        Double totalPedido = 0.0;
        
        for(Pedido ped: videojuegoSesion) {
        	DetallePedidoEntity detPed = new DetallePedidoEntity();
        	VideojuegoEntity videojuegoEntity = videojuegoService.buscarVideojuegopPorId(ped.getVideojuegosId());
        	detPed.setVideojuegoEntity(videojuegoEntity);
        	detPed.setCantidad(ped.getCantidad());
        	detallePedidoEntity.add(detPed);
        	totalPedido += ped.getCantidad() * videojuegoEntity.getPrecio();
        }
        model.addAttribute("carrito", detallePedidoEntity);
        model.addAttribute("total", totalPedido);

        return "menu";
    }

    @GetMapping("/ver_detalle/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        VideojuegoEntity videojuego = videojuegoService.buscarVideojuegopPorId(id);
        model.addAttribute("videojuego", videojuego);
        return "detalle_videojuego";
    }

    @GetMapping("/editar_juego/{id}")
    public String mostrarEditarJuego(@PathVariable Integer id, Model model) {
        VideojuegoEntity videojuego = videojuegoService.buscarVideojuegopPorId(id);
        model.addAttribute("videojuego", videojuego);
        return "editar_videojuego";
    }

    @PostMapping("/editar_juego")
    public String editarJuego(@ModelAttribute VideojuegoEntity videojuego) {
        videojuegoService.guardarVideojuego(videojuego);
        return "redirect:/menu";
    }

    @GetMapping("/eliminar_juego/{id}")
    public String eliminarJuego(@PathVariable Integer id) {
        videojuegoService.eliminarVideojuego(id);
        return "redirect:/menu";
    }

    @GetMapping("/agregar_videojuego")
    public String mostrarAgregarJuego(Model model) {
        model.addAttribute("videojuego", new VideojuegoEntity());
        return "agregar_videojuego";
    }

    @PostMapping("/agregar_videojuego")
    public String agregarVideojuego(@ModelAttribute("video") VideojuegoEntity videojuego, Model model) {
       videojuegoService.crearVideojuego(videojuego);
       
       return "redirect:/menu";
    }

    
    @PostMapping("/agregar_producto")
    public String agregarProducto(HttpSession sesion, @RequestParam("videoId")String videoId,
    		@RequestParam("cant") String cant) {
    	
    	List<Pedido>videojuegoSesion = null;
    	if(sesion.getAttribute("carrito") == null) {
    		videojuegoSesion = new ArrayList<Pedido>();
    	}else {
    		videojuegoSesion = (List<Pedido>)sesion.getAttribute("carrito");
    	}
    	
    	Integer cantidad = Integer.parseInt(cant);
    	Integer videojuegosId = Integer.parseInt(videoId);
    	Pedido pedidoNuevo = new Pedido(cantidad, videojuegosId);
    	videojuegoSesion.add(pedidoNuevo);
    	sesion.setAttribute("carrito", videojuegoSesion);
    	return "redirect:/menu";
    }
    
    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource>generarPDf(HttpSession sesion) throws IOException{
    	// formar los datos para pasarle al pdf
    	List<Pedido>videojuegoSesion = null;
    	if(sesion.getAttribute("carrito")  == null) {
    		videojuegoSesion = new ArrayList<Pedido>();
    	}else {
    		videojuegoSesion = (List<Pedido>)sesion.getAttribute("carrito");
    	}
    	List<DetallePedidoEntity>detallePedidoEntities = new ArrayList<DetallePedidoEntity>();
    	Double totalPedido = 0.0;
    	
    	for(Pedido ped: videojuegoSesion) {
    		DetallePedidoEntity det = new DetallePedidoEntity();
    		VideojuegoEntity videojuegoEntity = videojuegoService.buscarVideojuegopPorId(ped.getVideojuegosId());
    		det.setVideojuegoEntity(videojuegoEntity);
    		det.setCantidad(ped.getCantidad());
    		detallePedidoEntities.add(det);
    		totalPedido += videojuegoEntity.getPrecio() * ped.getCantidad();
    	}
    	
    	Map<String, Object> datosPdf = new HashMap<String, Object>();
    	datosPdf.put("factura", detallePedidoEntities);
    	datosPdf.put("precio_total", totalPedido);
    	
    	ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
    	
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Disposition", "inline; filename=productos.pdf");
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.contentType(MediaType.APPLICATION_PDF)
    			.body(new InputStreamResource(pdfBytes));
    
}
}