package com.reading.mscadastro.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.reading.mscadastro.MsCadastroApplication;
import com.reading.mscadastro.domain.model.Usuario;
import com.reading.mscadastro.domain.repository.UsuarioRepository;
import com.reading.mscadastro.infrastructure.exceptions.ResourceExceptionHandler;

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
class UsuarioControllerTest {

	private static final String API_V1_USUARIOS = "/api/usuarios";

	private static final String DEFAULT_NOME = "AAAAAAAAAA";

	private static final String DEFAULT_CIDADE = "AAAAAAAAAA";

	private static final String DEFAULT_EMAIL = "choliveira84@gmail.com";

	private static final String DEFAULT_PAIS = "AAAAAAAAAA";

	private static final String DEFAULT_FOTO = "http://placehold.it/50x50";

	@Autowired
	private UsuarioRepository repository;

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
	private UsuarioController controller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		this.restMockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(handler)
				.setMessageConverters(jacksonMessageConverter).setValidator(validator).build();
	}

	@Test
	@DisplayName("Tenta buscar o usuário pelo id")
	void teste01() throws Exception {
		Usuario usuario = repository
				.save(new Usuario(null, DEFAULT_NOME, DEFAULT_CIDADE, DEFAULT_EMAIL, DEFAULT_PAIS, DEFAULT_FOTO));

		restMockMvc.perform(get(API_V1_USUARIOS + "/{id}", usuario.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
				.andExpect(jsonPath("$.cidade").value(usuario.getCidade()))
				.andExpect(jsonPath("$.email").value(usuario.getEmail()))
				.andExpect(jsonPath("$.foto").value(usuario.getFoto()))
				.andExpect(jsonPath("$.nome").value(usuario.getNome()))
				.andExpect(jsonPath("$.pais").value(usuario.getPais()));
	}

	@Test
	@DisplayName("Tenta buscar o usuário por um email inexistente")
	void teste02() throws Exception {
		restMockMvc.perform(get(API_V1_USUARIOS + "/{id}", 0)).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Tenta buscar todos os usuários")
	void teste03() throws Exception {
		restMockMvc.perform(get(API_V1_USUARIOS)).andExpect(status().isOk());
	}

}
