package co.edu.udea.casilda.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa una Persona en el sistema CASILDA.
 * Contiene información demográfica personal requerida para los casos.
 */
@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primernombre", nullable = false, length = 100)
    private String primerNombre;

    @Column(name = "segundonombre", length = 100)
    private String segundoNombre;

    @Column(name = "primerapellido", nullable = false, length = 100)
    private String primerApellido;

    @Column(name = "segundoapellido", length = 100)
    private String segundoApellido;

    @Column(name = "numerodocumento", nullable = false, length = 50, unique = true)
    private String numeroDocumento;

    @Column(name = "fechanacimiento")
    private LocalDate fechaNacimiento;

    // Relaciones Many-to-One
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtipoidentificacion", nullable = false)
    private TipoIdentificacion tipoIdentificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsexo")
    private Sexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idetnia")
    private Etnia etnia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpaisnacimiento")
    private Pais paisNacimiento;

    // Relaciones Many-to-Many
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DiscapacidadPersona> discapacidades = new HashSet<>();

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CorreoPersona> correos = new HashSet<>();

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TelefonoPersona> telefonos = new HashSet<>();

    // Método para obtener nombre completo
    public String getNombreCompleto() {
        return primerNombre + " " + (segundoNombre != null ? segundoNombre + " " : "") + 
               primerApellido + " " + (segundoApellido != null ? segundoApellido : "");
    }
}
