package co.edu.udea.casilda.service;

import co.edu.udea.casilda.dto.request.CompromisoPersonaAtendidaRequest;
import co.edu.udea.casilda.dto.request.CompromisoProfesionalRequest;
import co.edu.udea.casilda.dto.response.CompromisoPersonaAtendidaResponse;
import co.edu.udea.casilda.dto.response.CompromisoProfesionalResponse;
import co.edu.udea.casilda.exception.ResourceNotFoundException;
import co.edu.udea.casilda.model.entity.*;
import co.edu.udea.casilda.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar compromisos de persona atendida y compromisos profesionales.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompromisoService {

    private final CompromisoPersonaAtendidaRepository compromisoPersonaRepository;
    private final CompromisoProfesionalRepository compromisoProfesionalRepository;
    private final AtencionRepository atencionRepository;
    private final TipoCompromisoRepository tipoCompromisoRepository;
    private final GrupoAtencionRepository grupoAtencionRepository;

    // ─── Compromisos Persona Atendida ─────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<CompromisoPersonaAtendidaResponse> listarCompromisoPersonaPorAtencion(Long idatencion) {
        log.info("Listando compromisos de persona atendida para atención ID: {}", idatencion);
        return compromisoPersonaRepository.findByIdatencion(idatencion).stream()
            .map(c -> CompromisoPersonaAtendidaResponse.builder()
                .idatencion(c.getIdatencion())
                .fechacompromiso(c.getFechacompromiso())
                .idtipocompromiso(c.getIdtipocompromiso())
                .nombreTipoCompromiso(c.getTipoCompromiso() != null ? c.getTipoCompromiso().getNombre() : null)
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public CompromisoPersonaAtendidaResponse crearCompromisoPersona(CompromisoPersonaAtendidaRequest req) {
        log.info("Creando compromiso de persona atendida para atención ID: {}", req.getIdatencion());

        if (!atencionRepository.existsById(req.getIdatencion())) {
            throw new ResourceNotFoundException("Atención no encontrada con ID: " + req.getIdatencion());
        }
        TipoCompromiso tipo = tipoCompromisoRepository.findById(req.getIdtipocompromiso())
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de compromiso no encontrado con ID: " + req.getIdtipocompromiso()));

        CompromisoPersonaAtendida entity = new CompromisoPersonaAtendida();
        entity.setIdatencion(req.getIdatencion());
        entity.setIdtipocompromiso(req.getIdtipocompromiso());
        entity.setFechacompromiso(req.getFechacompromiso());

        CompromisoPersonaAtendida saved = compromisoPersonaRepository.save(entity);
        return CompromisoPersonaAtendidaResponse.builder()
            .idatencion(saved.getIdatencion())
            .fechacompromiso(saved.getFechacompromiso())
            .idtipocompromiso(saved.getIdtipocompromiso())
            .nombreTipoCompromiso(tipo.getNombre())
            .build();
    }

    @Transactional
    public void eliminarCompromisoPersona(Long idatencion, Integer idtipocompromiso) {
        log.info("Eliminando compromiso de persona atendida: atención={}, tipo={}", idatencion, idtipocompromiso);
        CompromisoPersonaAtendidaId id = new CompromisoPersonaAtendidaId(idatencion, idtipocompromiso);
        if (!compromisoPersonaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Compromiso de persona atendida no encontrado");
        }
        compromisoPersonaRepository.deleteById(id);
    }

    // ─── Compromisos Profesional ──────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<CompromisoProfesionalResponse> listarCompromisoProfesionalPorAtencion(Long idatencion) {
        log.info("Listando compromisos profesionales para atención ID: {}", idatencion);
        return compromisoProfesionalRepository.findByIdatencion(idatencion).stream()
            .map(c -> CompromisoProfesionalResponse.builder()
                .idatencion(c.getIdatencion())
                .fechacompromiso(c.getFechacompromiso())
                .idgrupoprofesional(c.getIdgrupoprofesional())
                .nombreGrupoProfesional(c.getGrupoAtencion() != null ? c.getGrupoAtencion().getNombre() : null)
                .idtipocompromiso(c.getIdtipocompromiso())
                .nombreTipoCompromiso(c.getTipoCompromiso() != null ? c.getTipoCompromiso().getNombre() : null)
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    public CompromisoProfesionalResponse crearCompromisoProfesional(CompromisoProfesionalRequest req) {
        log.info("Creando compromiso profesional para atención ID: {}", req.getIdatencion());

        if (!atencionRepository.existsById(req.getIdatencion())) {
            throw new ResourceNotFoundException("Atención no encontrada con ID: " + req.getIdatencion());
        }
        TipoCompromiso tipo = tipoCompromisoRepository.findById(req.getIdtipocompromiso())
            .orElseThrow(() -> new ResourceNotFoundException("Tipo de compromiso no encontrado con ID: " + req.getIdtipocompromiso()));
        GrupoAtencion grupo = grupoAtencionRepository.findById(req.getIdgrupoprofesional())
            .orElseThrow(() -> new ResourceNotFoundException("Grupo de atención no encontrado con ID: " + req.getIdgrupoprofesional()));

        CompromisoProfesional entity = new CompromisoProfesional();
        entity.setIdatencion(req.getIdatencion());
        entity.setIdtipocompromiso(req.getIdtipocompromiso());
        entity.setIdgrupoprofesional(req.getIdgrupoprofesional());
        entity.setFechacompromiso(req.getFechacompromiso());

        CompromisoProfesional saved = compromisoProfesionalRepository.save(entity);
        return CompromisoProfesionalResponse.builder()
            .idatencion(saved.getIdatencion())
            .fechacompromiso(saved.getFechacompromiso())
            .idgrupoprofesional(saved.getIdgrupoprofesional())
            .nombreGrupoProfesional(grupo.getNombre())
            .idtipocompromiso(saved.getIdtipocompromiso())
            .nombreTipoCompromiso(tipo.getNombre())
            .build();
    }

    @Transactional
    public void eliminarCompromisoProfesional(Long idatencion, Integer idtipocompromiso) {
        log.info("Eliminando compromiso profesional: atención={}, tipo={}", idatencion, idtipocompromiso);
        CompromisoProfesionalId id = new CompromisoProfesionalId(idatencion, idtipocompromiso);
        if (!compromisoProfesionalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Compromiso profesional no encontrado");
        }
        compromisoProfesionalRepository.deleteById(id);
    }
}
