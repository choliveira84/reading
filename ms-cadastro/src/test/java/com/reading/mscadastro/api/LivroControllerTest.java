package com.reading.mscadastro.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import com.reading.mscadastro.MsCadastroApplication;
import com.reading.mscadastro.application.dto.LivroPostDTO;
import com.reading.mscadastro.domain.model.Livro;
import com.reading.mscadastro.domain.repository.LivroRepository;
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
class LivroControllerTest {

	private static final String API_V1_LIVROS = "/api/livros";

	private static final String DEFAULT_NOME = "AAAAAAAAAA";

	private static final String DEFAULT_CAPA = "http://www.capa.com";

	private static final String DEFAULT_TITULO = "AAAAAAAAAA";

	private static final String DEFAULT_AUTOR = "AAAAAAAAAA";

	private static final String DEFAULT_ISBN = "AAAAAAAAAA";

	private static final Long DEFAULT_PAGINA = 1l;

	private static final String DEFAULT_ANO_LANCAMENTO = "AAAAAAAAAA";

	@Autowired
	private LivroRepository repository;

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
	private LivroController livroController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		this.restMockMvc = MockMvcBuilders.standaloneSetup(livroController)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(handler)
				.setMessageConverters(jacksonMessageConverter).setValidator(validator).build();
	}

	public static LivroPostDTO criar(EntityManager em) {
		return new LivroPostDTO(DEFAULT_CAPA, DEFAULT_TITULO, DEFAULT_ISBN, DEFAULT_AUTOR, DEFAULT_PAGINA,
				DEFAULT_ANO_LANCAMENTO);
	}

	@Test
	@DisplayName("Tenta salvar um livro")
	void teste01() throws Exception {
		int databaseSizeBeforeCreate = repository.findAll().size();
		LivroPostDTO post = criar(em);
		restMockMvc.perform(post(API_V1_LIVROS)
				// .with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(post)))
				.andExpect(status().isCreated());

		List<Livro> livroList = repository.findAll();
		assertThat(livroList).hasSize(databaseSizeBeforeCreate + 1);
		Livro testLivro = livroList.get(livroList.size() - 1);
		assertThat(testLivro.getTitulo()).isEqualTo(DEFAULT_NOME);
		assertThat(testLivro.getCapa()).isEqualTo(DEFAULT_CAPA);
		assertThat(testLivro.getAutor()).isEqualTo(DEFAULT_AUTOR);
		assertThat(testLivro.getIsbn()).isEqualTo(DEFAULT_ISBN);
		assertThat(testLivro.getNumeroPagina()).isEqualTo(DEFAULT_PAGINA);
		assertThat(testLivro.getAnoLancamento()).isEqualTo(DEFAULT_ANO_LANCAMENTO);
	}

	@Test
	@DisplayName("Tenta salvar um livro com um isbn já criado")
	void teste02() throws Exception {
		LivroPostDTO post = criar(em);
		restMockMvc.perform(post(API_V1_LIVROS)
				// .with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(post)))
				.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("Tenta buscar todos os livros")
	void teste03() throws Exception {
		restMockMvc.perform(get(API_V1_LIVROS)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Tenta buscar o livro por um id inexistente")
	void teste04() throws Exception {
		restMockMvc.perform(get(API_V1_LIVROS + "/{id}", 0)).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Tenta buscar o livro pelo id")
	void teste05() throws Exception {
		Livro livro = repository.findAll().stream().findAny().get(); // NOSONAR

		restMockMvc.perform(get(API_V1_LIVROS + "/{id}", livro.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(livro.getId().intValue()))
				.andExpect(jsonPath("$.anoLancamento").value(livro.getAnoLancamento()))
				.andExpect(jsonPath("$.autor").value(livro.getAutor()))
				.andExpect(jsonPath("$.capa").value(livro.getCapa()))
				.andExpect(jsonPath("$.isbn").value(livro.getIsbn()))
				.andExpect(jsonPath("$.numeroPagina").value(livro.getNumeroPagina()))
				.andExpect(jsonPath("$.titulo").value(livro.getTitulo()));
	}

}
