package com.reading.msleitura.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import com.reading.msleitura.MsLeituraApplication;
import com.reading.msleitura.application.dto.LeituraRequest;
import com.reading.msleitura.domain.model.Leitura;
import com.reading.msleitura.domain.model.Leitura.NotasLivro;
import com.reading.msleitura.domain.model.Leitura.StatusLeitura;
import com.reading.msleitura.domain.repository.LeituraRepository;
import com.reading.msleitura.infrastructure.exceptions.ResourceExceptionHandler;
import com.reading.msleitura.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

/**
 * Integration tests for the {@link LeituraResource} REST controller.
 */
@SpringBootTest(classes = MsLeituraApplication.class)
@AutoConfigureMockMvc
public class LeituraControllerTest {

    private static final String API_LEITURAS = "/api/leituras";

    private static final Long DEFAULT_ID_USUARIO = 1L;

    private static final String DEFAULT_NOME_USUARIO = "AAAAAAAAAA";

    private static final Long DEFAULT_ID_LIVRO = 1L;

    private static final String DEFAULT_TITULO_LIVRO = "AAAAAAAAAA";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.now();

    private static final LocalDate DEFAULT_DATA_FIM = LocalDate.now();

    private static final Long DEFAULT_PAGINA_ATUAL = 1L;
    private static final Long UPDATED_PAGINA_ATUAL = 2L;

    private static final NotasLivro DEFAULT_NOTA = NotasLivro.UM;
    private static final NotasLivro UPDATED_NOTA = NotasLivro.DOIS;

    private static final StatusLeitura STATUS_LIDO = StatusLeitura.LIDO;
    private static final StatusLeitura STATUS_LENDO = StatusLeitura.LENDO;
    private static final StatusLeitura STATUS_A_LER = StatusLeitura.A_LER;

    @Autowired
    private LeituraRepository leituraRepository;

    @Autowired
    private MockMvc restMockMvc;

    @Autowired
    private Validator validator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ResourceExceptionHandler handler;

    @Autowired
    private LeituraController leituraController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(leituraController)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(handler)
                .setMessageConverters(jacksonMessageConverter).setValidator(validator).build();
    }

    public static LeituraRequest criarDTO() {
        return new LeituraRequest(DEFAULT_ID_USUARIO, DEFAULT_NOME_USUARIO, DEFAULT_ID_LIVRO, DEFAULT_TITULO_LIVRO);
    }

    public static Leitura criarEntidade() {
        return new Leitura(DEFAULT_ID_USUARIO, DEFAULT_NOME_USUARIO, DEFAULT_ID_LIVRO, DEFAULT_TITULO_LIVRO);
    }

    @Test
    @Transactional
    public void createLeitura() throws Exception {
        int databaseSizeBeforeCreate = leituraRepository.findAll().size();
        // Create the Leitura
        LeituraRequest leituraDTO = criarDTO();
        restMockMvc.perform(post(API_LEITURAS).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(leituraDTO))).andExpect(status().isCreated());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeCreate + 1);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
    }

    @Test
    @Transactional
    public void checkNomeUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = leituraRepository.findAll().size();

        // Create the Leitura, which fails.
        LeituraRequest leituraDTO = criarDTO();

        // set the field null
        leituraDTO.setNomeUsuario(null);
        leituraDTO.setIdLivro(null);
        leituraDTO.setTituloLivro(null);

        restMockMvc
                .perform(post(API_LEITURAS).contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(leituraDTO)))
                .andExpect(status().isUnprocessableEntity());

        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeituras() throws Exception {
        restMockMvc.perform(get(API_LEITURAS)).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getLeitura() throws Exception {
        // Initialize the database
        Leitura leitura = leituraRepository.saveAndFlush(criarEntidade());

        // Get the leitura
        restMockMvc.perform(get(API_LEITURAS + "/{id}", leitura.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(leitura.getId().intValue()))
                .andExpect(jsonPath("$.nomeUsuario").value(DEFAULT_NOME_USUARIO))
                .andExpect(jsonPath("$.idLivro").value(DEFAULT_ID_LIVRO.intValue()))
                .andExpect(jsonPath("$.tituloLivro").value(DEFAULT_TITULO_LIVRO));
    }

    @Test
    @Transactional
    public void getNonExistingLeitura() throws Exception {
        // Get the leitura
        restMockMvc.perform(get(API_LEITURAS + "/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void atualizarNota() throws Exception {
        Leitura leituraASerSalva = criarEntidade();
        ReflectionTestUtils.setField(leituraASerSalva, "nota", DEFAULT_NOTA);

        // Initialize the database
        Leitura leitura = leituraRepository.saveAndFlush(leituraASerSalva);

        int databaseSizeBeforeUpdate = leituraRepository.findAll().size();

        restMockMvc.perform(
                put(API_LEITURAS + "/atualizar-nota/{idLivro}/livro/{nota}/nota", leitura.getIdLivro(), UPDATED_NOTA))
                .andExpect(status().isNoContent());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeUpdate);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
        assertThat(testLeitura.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    public void iniciarLeitura() throws Exception {
        // Initialize the database
        Leitura leitura = leituraRepository.saveAndFlush(criarEntidade());

        int databaseSizeBeforeUpdate = leituraRepository.findAll().size();

        restMockMvc.perform(put(API_LEITURAS + "/iniciar-leitura/{idLivro}/livro", leitura.getIdLivro()))
                .andExpect(status().isNoContent());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeUpdate);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
        assertThat(testLeitura.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testLeitura.getStatus()).isEqualTo(STATUS_LENDO);
    }

    @Test
    @Transactional
    public void incluirNaListaParaLer() throws Exception {
        // Initialize the database
        Leitura leitura = leituraRepository.saveAndFlush(criarEntidade());

        int databaseSizeBeforeUpdate = leituraRepository.findAll().size();

        restMockMvc.perform(put(API_LEITURAS + "/incluir/{idLivro}/livro", leitura.getIdLivro()))
                .andExpect(status().isNoContent());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeUpdate);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
        assertThat(testLeitura.getStatus()).isEqualTo(STATUS_A_LER);
    }

    @Test
    @Transactional
    public void finalizarLeitura() throws Exception {
        // Initialize the database
        Leitura leituraASerSalva = criarEntidade();
        ReflectionTestUtils.setField(leituraASerSalva, "dataInicio", LocalDate.now());
        ReflectionTestUtils.setField(leituraASerSalva, "nota", DEFAULT_NOTA);

        Leitura leitura = leituraRepository.saveAndFlush(leituraASerSalva);

        int databaseSizeBeforeUpdate = leituraRepository.findAll().size();

        restMockMvc.perform(put(API_LEITURAS + "/finalizar-leitura/{idLivro}/livro", leitura.getIdLivro()))
                .andExpect(status().isNoContent());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeUpdate);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
        assertThat(testLeitura.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testLeitura.getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
        assertThat(testLeitura.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testLeitura.getStatus()).isEqualTo(STATUS_LIDO);
    }

    @Test
    @Transactional
    public void atualizarPagina() throws Exception {
        // Initialize the database
        Leitura leituraASerSalva = criarEntidade();
        ReflectionTestUtils.setField(leituraASerSalva, "dataInicio", LocalDate.now());
        ReflectionTestUtils.setField(leituraASerSalva, "paginaAtual", DEFAULT_PAGINA_ATUAL);

        Leitura leitura = leituraRepository.saveAndFlush(leituraASerSalva);

        int databaseSizeBeforeUpdate = leituraRepository.findAll().size();

        restMockMvc.perform(put(API_LEITURAS + "/atualizar-pagina/{idLivro}/livro/{pagina}/pagina",
                leitura.getIdLivro(), UPDATED_PAGINA_ATUAL)).andExpect(status().isNoContent());

        // Validate the Leitura in the database
        List<Leitura> leituraList = leituraRepository.findAll();
        assertThat(leituraList).hasSize(databaseSizeBeforeUpdate);
        Leitura testLeitura = leituraList.get(leituraList.size() - 1);
        assertThat(testLeitura.getNomeUsuario()).isEqualTo(DEFAULT_NOME_USUARIO);
        assertThat(testLeitura.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
        assertThat(testLeitura.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
        assertThat(testLeitura.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testLeitura.getPaginaAtual()).isEqualTo(UPDATED_PAGINA_ATUAL);
    }

}
