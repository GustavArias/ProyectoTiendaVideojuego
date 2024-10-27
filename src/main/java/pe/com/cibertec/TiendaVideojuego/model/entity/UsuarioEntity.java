package pe.com.cibertec.TiendaVideojuego.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
	
	@Id
	@Column(name="correo", nullable = false, length = 50)
	private String correo;
	
	@Column(name ="nombre", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "celular", nullable = false, columnDefinition = "CHAR(9)", length = 9, unique = true)
	private String celular;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "url_imagen")
	private String urlImagen;

}
