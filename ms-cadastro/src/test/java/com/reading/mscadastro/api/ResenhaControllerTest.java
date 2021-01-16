package com.reading.mscadastro.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import com.reading.mscadastro.MsCadastroApplication;
import com.reading.mscadastro.application.dto.ResenhaPostDTO;
import com.reading.mscadastro.domain.model.Resenha;
import com.reading.mscadastro.domain.repository.ResenhaRepository;
import com.reading.mscadastro.infrastructure.exceptions.ResourceExceptionHandler;
import com.reading.mscadastro.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.validation.Validator;

@SpringBootTest(classes = { MsCadastroApplication.class })
@AutoConfigureMockMvc
class ResenhaControllerTest {

	private static final String API_V1_RESENHAS = "/api/v1/resenhas";

	private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";

	private static final String DEFAULT_TITULO = "AAAAAAAAAA";

	private static final Long DEFAULT_USUARIO = 1l;

	private static final Long DEFAULT_LIVRO = 1L;

	@Autowired
	private ResenhaRepository repository;

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
	private ResenhaV1Controller controller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		this.restMockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(handler)
				.setMessageConverters(jacksonMessageConverter).setValidator(validator).build();
	}

	public static ResenhaPostDTO criar(EntityManager em) {
		return new ResenhaPostDTO(DEFAULT_DESCRICAO, DEFAULT_TITULO, DEFAULT_USUARIO, DEFAULT_LIVRO);
	}

	@Test
	@DisplayName("Tenta salvar uma resenha")
	void teste01() throws Exception {
		int databaseSizeBeforeCreate = repository.findAll().size();
		ResenhaPostDTO post = criar(em);
		restMockMvc.perform(post(API_V1_RESENHAS)
				// .with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(post)))
				.andExpect(status().isCreated());

		List<Resenha> lista = repository.findAll();
		assertThat(lista).hasSize(databaseSizeBeforeCreate + 1);
		Resenha testResenha = lista.get(lista.size() - 1);
		assertThat(testResenha.getTitulo()).isEqualTo(DEFAULT_TITULO);
		assertThat(testResenha.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
		assertThat(testResenha.getLivro().getId()).isEqualTo(DEFAULT_LIVRO);
		assertThat(testResenha.getUsuario().getId()).isEqualTo(DEFAULT_USUARIO);
	}

	// @Test
	// @DisplayName("Tenta buscar todos as resenhas")
	// void teste03() throws Exception {
	// 	restMockMvc.perform(get(API_V1_RESENHAS)).andExpect(status().isOk());
	// }

	@Test
	@DisplayName("Tenta buscar uma resenha por um id inexistente")
	void teste04() throws Exception {
		restMockMvc.perform(get(API_V1_RESENHAS + "/{id}", 0)).andExpect(status().isNotFound());
	}

	// @Test
	// @DisplayName("Tenta buscar uma resenha pelo id")
	// void teste05() throws Exception {
	// 	Resenha resenha = repository.findAll().stream().findAny().get(); // NOSONAR

	// 	restMockMvc.perform(get(API_V1_RESENHAS + "/{id}", resenha.getId())).andExpect(status().isOk())
	// 			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	// 			.andExpect(jsonPath("$.id").value(resenha.getId().intValue()))
	// 			.andExpect(jsonPath("$.descricao").value(resenha.getDescricao()))
	// 			.andExpect(jsonPath("$.titulo").value(resenha.getTitulo()));
	// }

}
