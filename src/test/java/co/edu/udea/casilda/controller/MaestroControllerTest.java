package co.edu.udea.casilda.controller;

import co.edu.udea.casilda.dto.response.MaestroDTO;
import co.edu.udea.casilda.service.MaestroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MaestroControllerTest {

    private MockMvc mockMvc;

    private MaestroService catalogoService;

    @BeforeEach
    void setUp() {
        catalogoService = mock(MaestroService.class);
        MaestroController maestroController = new MaestroController(catalogoService);
        mockMvc = MockMvcBuilders.standaloneSetup(maestroController).build();
    }

    @Test
    void debeRetornarLugaresEntrevistaParaCampoLugarEntrevista() throws Exception {
        List<MaestroDTO> lugaresEntrevista = List.of(
                MaestroDTO.builder().id(1L).codigo(null).nombre("Presencial").build(),
                MaestroDTO.builder().id(2L).codigo(null).nombre("Virtual").build(),
                MaestroDTO.builder().id(3L).codigo(null).nombre("Telefonica").build()
        );

        when(catalogoService.obtenerLugaresEntrevista()).thenReturn(lugaresEntrevista);

        mockMvc.perform(get("/maestros/lugares-entrevista"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Presencial"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Virtual"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nombre").value("Telefonica"));
    }
}
