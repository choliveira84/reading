package com.reading.msnotificacao.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import com.reading.msnotificacao.MsNotificacaoApplication;
import com.reading.msnotificacao.application.dto.NotificacaoLivroPublicadoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoResenhaDTO;
import com.reading.msnotificacao.domain.model.Notificacao;
import com.reading.msnotificacao.domain.model.Notificacao.TiposNotificacao;
import com.reading.msnotificacao.domain.repository.NotificacaoReposity;
import com.reading.msnotificacao.infrastructure.exceptions.ResourceExceptionHandler;
import com.reading.msnotificacao.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

@SpringBootTest(classes = { MsNotificacaoApplication.class })
@AutoConfigureMockMvc
class NotificacaoControllerTest {

	private static final Long DEFAULT_ID_LIVRO = 1L;

	private static final String DEFAULT_TITULO_LIVRO = "AAAAAAAAAA";

	private static final TiposNotificacao DEFAULT_TIPO_RESENHA = TiposNotificacao.RESENHA_PUBLICADA;

	private static final TiposNotificacao DEFAULT_TIPO_LIVRO = TiposNotificacao.LIVRO_PUBLICADO;

	private static final Long DEFAULT_ID_USUARIO = 1L;

	private static final Long DEFAULT_ID_RESENHA = 1L;

	private static final String DEFAULT_TITULO_RESENHA = "AAAAAAAAAA";

	private static final String DEFAULT_CAPA = "http://www.capa.com";

	private static final String DEFAULT_AUTOR = "AAAAAAAAAA";

	private static final String DEFAULT_NOME_USUARIO = "AAAAAAAAAA";

	private static final String DEFAULT_ISBN = "AAAAAAAAAA";

	private static final Long DEFAULT_PAGINA = 1l;

	private static final String DEFAULT_ANO_LANCAMENTO = "AAAAAAAAAA";

	@Autowired
	private NotificacaoReposity repository;

	@Autowired
	private EntityManager em;

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
	private NotificacaoController controller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		this.restMockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(handler)
				.setMessageConverters(jacksonMessageConverter).setValidator(validator).build();
	}

	public static NotificacaoLivroPublicadoDTO criarLivro(EntityManager em) {
		return new NotificacaoLivroPublicadoDTO(DEFAULT_ID_LIVRO, DEFAULT_CAPA, DEFAULT_TITULO_LIVRO, DEFAULT_AUTOR,
				DEFAULT_ISBN, DEFAULT_PAGINA, DEFAULT_ANO_LANCAMENTO);
	}

	public NotificacaoResenhaDTO criarResenha(EntityManager em) {
		return new NotificacaoResenhaDTO(DEFAULT_ID_RESENHA, DEFAULT_ID_USUARIO, DEFAULT_NOME_USUARIO,
				DEFAULT_TITULO_RESENHA, DEFAULT_ID_LIVRO, DEFAULT_TITULO_LIVRO);
	}

	@Test
	@Transactional
	public void createNotificacaoLivro() throws Exception {
		int databaseSizeBeforeCreate = repository.findAll().size();
		// Create the Notificacao
		NotificacaoLivroPublicadoDTO notificacaoDTO = criarLivro(em);
		restMockMvc.perform(post("/api/notificacoes/livro").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(notificacaoDTO))).andExpect(status().isCreated());

		// Validate the Notificacao in the database
		List<Notificacao> notificacaoList = repository.findAll();
		assertThat(notificacaoList).hasSize(databaseSizeBeforeCreate + 1);
		Notificacao testNotificacao = notificacaoList.get(notificacaoList.size() - 1);
		assertThat(testNotificacao.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
		assertThat(testNotificacao.getTituloLivro()).isEqualTo(DEFAULT_TITULO_LIVRO);
		assertThat(testNotificacao.getTipo()).isEqualTo(DEFAULT_TIPO_LIVRO);
	}

	@Test
	@Transactional
	public void createNotificacao() throws Exception {
		int databaseSizeBeforeCreate = repository.findAll().size();
		// Create the Notificacao
		NotificacaoResenhaDTO notificacaoDTO = criarResenha(em);
		restMockMvc.perform(post("/api/notificacoes/resenha").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(notificacaoDTO))).andExpect(status().isCreated());

		// Validate the Notificacao in the database
		List<Notificacao> notificacaoList = repository.findAll();
		assertThat(notificacaoList).hasSize(databaseSizeBeforeCreate + 1);
		Notificacao testNotificacao = notificacaoList.get(notificacaoList.size() - 1);
		assertThat(testNotificacao.getIdLivro()).isEqualTo(DEFAULT_ID_LIVRO);
		assertThat(testNotificacao.getTipo()).isEqualTo(DEFAULT_TIPO_RESENHA);
		assertThat(testNotificacao.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
		assertThat(testNotificacao.getIdResenha()).isEqualTo(DEFAULT_ID_RESENHA);
		assertThat(testNotificacao.getTituloResenha()).isEqualTo(DEFAULT_TITULO_RESENHA);
	}

	@Test
	@Transactional
	public void getAllnotificacoes() throws Exception {
		// Initialize the database
		repository.saveAndFlush(new Notificacao(DEFAULT_TITULO_LIVRO, DEFAULT_ID_LIVRO));

		// Get all the notificacaoList
		restMockMvc.perform(get("/api/notificacoes?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	@Transactional
	public void getNotificacaoResenha() throws Exception {
		// Initialize the database
		Notificacao notificacao = repository.saveAndFlush(new Notificacao(DEFAULT_TITULO_RESENHA, DEFAULT_ID_LIVRO,
				DEFAULT_ID_RESENHA, DEFAULT_ID_USUARIO, DEFAULT_NOME_USUARIO));

		// Get the notificacao
		restMockMvc.perform(get("/api/notificacoes/{id}", notificacao.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(notificacao.getId().intValue()))
				.andExpect(jsonPath("$.idLivro").value(DEFAULT_ID_LIVRO.intValue()))
				.andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO_RESENHA.toString()))
				.andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO.intValue()))
				.andExpect(jsonPath("$.idResenha").value(DEFAULT_ID_RESENHA.intValue()))
				.andExpect(jsonPath("$.tituloResenha").value(DEFAULT_TITULO_RESENHA));
	}

	@Test
	@Transactional
	public void getNotificacaoLivro() throws Exception {
		// Initialize the database
		Notificacao notificacao = repository.saveAndFlush(new Notificacao(DEFAULT_TITULO_LIVRO, DEFAULT_ID_LIVRO));

		// Get the notificacao
		restMockMvc.perform(get("/api/notificacoes/{id}", notificacao.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(notificacao.getId().intValue()))
				.andExpect(jsonPath("$.idLivro").value(DEFAULT_ID_LIVRO.intValue()))
				.andExpect(jsonPath("$.tituloLivro").value(DEFAULT_TITULO_LIVRO))
				.andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO_LIVRO.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingNotificacao() throws Exception {
		// Get the notificacao
		restMockMvc.perform(get("/api/notificacoes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void deleteNotificacao() throws Exception {
		// Initialize the database
		Notificacao notificacao = repository.saveAndFlush(new Notificacao(DEFAULT_TITULO_RESENHA, DEFAULT_ID_LIVRO,
				DEFAULT_ID_RESENHA, DEFAULT_ID_USUARIO, DEFAULT_NOME_USUARIO));

		int databaseSizeBeforeDelete = repository.findAll().size();

		// Delete the notificacao
		restMockMvc.perform(delete("/api/notificacoes/{id}", notificacao.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Notificacao> notificacaoList = repository.findAll();
		assertThat(notificacaoList).hasSize(databaseSizeBeforeDelete - 1);
	}

}
