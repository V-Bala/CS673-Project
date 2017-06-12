package co.metcsprojectone.web.rest;

import co.metcsprojectone.ProjectoneApp;

import co.metcsprojectone.domain.Tmember;
import co.metcsprojectone.repository.TmemberRepository;
import co.metcsprojectone.repository.search.TmemberSearchRepository;
import co.metcsprojectone.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TmemberResource REST controller.
 *
 * @see TmemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectoneApp.class)
public class TmemberResourceIntTest {

    private static final String DEFAULT_MEMBERNAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERNAME = "BBBBBBBBBB";

    @Autowired
    private TmemberRepository tmemberRepository;

    @Autowired
    private TmemberSearchRepository tmemberSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTmemberMockMvc;

    private Tmember tmember;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TmemberResource tmemberResource = new TmemberResource(tmemberRepository, tmemberSearchRepository);
        this.restTmemberMockMvc = MockMvcBuilders.standaloneSetup(tmemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tmember createEntity(EntityManager em) {
        Tmember tmember = new Tmember()
            .membername(DEFAULT_MEMBERNAME);
        return tmember;
    }

    @Before
    public void initTest() {
        tmemberSearchRepository.deleteAll();
        tmember = createEntity(em);
    }

    @Test
    @Transactional
    public void createTmember() throws Exception {
        int databaseSizeBeforeCreate = tmemberRepository.findAll().size();

        // Create the Tmember
        restTmemberMockMvc.perform(post("/api/tmembers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tmember)))
            .andExpect(status().isCreated());

        // Validate the Tmember in the database
        List<Tmember> tmemberList = tmemberRepository.findAll();
        assertThat(tmemberList).hasSize(databaseSizeBeforeCreate + 1);
        Tmember testTmember = tmemberList.get(tmemberList.size() - 1);
        assertThat(testTmember.getMembername()).isEqualTo(DEFAULT_MEMBERNAME);

        // Validate the Tmember in Elasticsearch
        Tmember tmemberEs = tmemberSearchRepository.findOne(testTmember.getId());
        assertThat(tmemberEs).isEqualToComparingFieldByField(testTmember);
    }

    @Test
    @Transactional
    public void createTmemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tmemberRepository.findAll().size();

        // Create the Tmember with an existing ID
        tmember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTmemberMockMvc.perform(post("/api/tmembers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tmember)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tmember> tmemberList = tmemberRepository.findAll();
        assertThat(tmemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTmembers() throws Exception {
        // Initialize the database
        tmemberRepository.saveAndFlush(tmember);

        // Get all the tmemberList
        restTmemberMockMvc.perform(get("/api/tmembers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tmember.getId().intValue())))
            .andExpect(jsonPath("$.[*].membername").value(hasItem(DEFAULT_MEMBERNAME.toString())));
    }

    @Test
    @Transactional
    public void getTmember() throws Exception {
        // Initialize the database
        tmemberRepository.saveAndFlush(tmember);

        // Get the tmember
        restTmemberMockMvc.perform(get("/api/tmembers/{id}", tmember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tmember.getId().intValue()))
            .andExpect(jsonPath("$.membername").value(DEFAULT_MEMBERNAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTmember() throws Exception {
        // Get the tmember
        restTmemberMockMvc.perform(get("/api/tmembers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTmember() throws Exception {
        // Initialize the database
        tmemberRepository.saveAndFlush(tmember);
        tmemberSearchRepository.save(tmember);
        int databaseSizeBeforeUpdate = tmemberRepository.findAll().size();

        // Update the tmember
        Tmember updatedTmember = tmemberRepository.findOne(tmember.getId());
        updatedTmember
            .membername(UPDATED_MEMBERNAME);

        restTmemberMockMvc.perform(put("/api/tmembers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTmember)))
            .andExpect(status().isOk());

        // Validate the Tmember in the database
        List<Tmember> tmemberList = tmemberRepository.findAll();
        assertThat(tmemberList).hasSize(databaseSizeBeforeUpdate);
        Tmember testTmember = tmemberList.get(tmemberList.size() - 1);
        assertThat(testTmember.getMembername()).isEqualTo(UPDATED_MEMBERNAME);

        // Validate the Tmember in Elasticsearch
        Tmember tmemberEs = tmemberSearchRepository.findOne(testTmember.getId());
        assertThat(tmemberEs).isEqualToComparingFieldByField(testTmember);
    }

    @Test
    @Transactional
    public void updateNonExistingTmember() throws Exception {
        int databaseSizeBeforeUpdate = tmemberRepository.findAll().size();

        // Create the Tmember

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTmemberMockMvc.perform(put("/api/tmembers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tmember)))
            .andExpect(status().isCreated());

        // Validate the Tmember in the database
        List<Tmember> tmemberList = tmemberRepository.findAll();
        assertThat(tmemberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTmember() throws Exception {
        // Initialize the database
        tmemberRepository.saveAndFlush(tmember);
        tmemberSearchRepository.save(tmember);
        int databaseSizeBeforeDelete = tmemberRepository.findAll().size();

        // Get the tmember
        restTmemberMockMvc.perform(delete("/api/tmembers/{id}", tmember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tmemberExistsInEs = tmemberSearchRepository.exists(tmember.getId());
        assertThat(tmemberExistsInEs).isFalse();

        // Validate the database is empty
        List<Tmember> tmemberList = tmemberRepository.findAll();
        assertThat(tmemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTmember() throws Exception {
        // Initialize the database
        tmemberRepository.saveAndFlush(tmember);
        tmemberSearchRepository.save(tmember);

        // Search the tmember
        restTmemberMockMvc.perform(get("/api/_search/tmembers?query=id:" + tmember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tmember.getId().intValue())))
            .andExpect(jsonPath("$.[*].membername").value(hasItem(DEFAULT_MEMBERNAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tmember.class);
        Tmember tmember1 = new Tmember();
        tmember1.setId(1L);
        Tmember tmember2 = new Tmember();
        tmember2.setId(tmember1.getId());
        assertThat(tmember1).isEqualTo(tmember2);
        tmember2.setId(2L);
        assertThat(tmember1).isNotEqualTo(tmember2);
        tmember1.setId(null);
        assertThat(tmember1).isNotEqualTo(tmember2);
    }
}
