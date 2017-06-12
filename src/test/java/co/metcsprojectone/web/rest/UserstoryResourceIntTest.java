package co.metcsprojectone.web.rest;

import co.metcsprojectone.ProjectoneApp;

import co.metcsprojectone.domain.Userstory;
import co.metcsprojectone.repository.UserstoryRepository;
import co.metcsprojectone.repository.search.UserstorySearchRepository;
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

import co.metcsprojectone.domain.enumeration.Status;
import co.metcsprojectone.domain.enumeration.Priority;
/**
 * Test class for the UserstoryResource REST controller.
 *
 * @see UserstoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectoneApp.class)
public class UserstoryResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.NEW;
    private static final Status UPDATED_STATUS = Status.COMPLETE;

    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
    private static final Priority UPDATED_PRIORITY = Priority.MEDIUM;

    @Autowired
    private UserstoryRepository userstoryRepository;

    @Autowired
    private UserstorySearchRepository userstorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserstoryMockMvc;

    private Userstory userstory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserstoryResource userstoryResource = new UserstoryResource(userstoryRepository, userstorySearchRepository);
        this.restUserstoryMockMvc = MockMvcBuilders.standaloneSetup(userstoryResource)
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
    public static Userstory createEntity(EntityManager em) {
        Userstory userstory = new Userstory()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .comments(DEFAULT_COMMENTS)
            .status(DEFAULT_STATUS)
            .priority(DEFAULT_PRIORITY);
        return userstory;
    }

    @Before
    public void initTest() {
        userstorySearchRepository.deleteAll();
        userstory = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserstory() throws Exception {
        int databaseSizeBeforeCreate = userstoryRepository.findAll().size();

        // Create the Userstory
        restUserstoryMockMvc.perform(post("/api/userstories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userstory)))
            .andExpect(status().isCreated());

        // Validate the Userstory in the database
        List<Userstory> userstoryList = userstoryRepository.findAll();
        assertThat(userstoryList).hasSize(databaseSizeBeforeCreate + 1);
        Userstory testUserstory = userstoryList.get(userstoryList.size() - 1);
        assertThat(testUserstory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserstory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserstory.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testUserstory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserstory.getPriority()).isEqualTo(DEFAULT_PRIORITY);

        // Validate the Userstory in Elasticsearch
        Userstory userstoryEs = userstorySearchRepository.findOne(testUserstory.getId());
        assertThat(userstoryEs).isEqualToComparingFieldByField(testUserstory);
    }

    @Test
    @Transactional
    public void createUserstoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userstoryRepository.findAll().size();

        // Create the Userstory with an existing ID
        userstory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserstoryMockMvc.perform(post("/api/userstories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userstory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Userstory> userstoryList = userstoryRepository.findAll();
        assertThat(userstoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserstories() throws Exception {
        // Initialize the database
        userstoryRepository.saveAndFlush(userstory);

        // Get all the userstoryList
        restUserstoryMockMvc.perform(get("/api/userstories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userstory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    public void getUserstory() throws Exception {
        // Initialize the database
        userstoryRepository.saveAndFlush(userstory);

        // Get the userstory
        restUserstoryMockMvc.perform(get("/api/userstories/{id}", userstory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userstory.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserstory() throws Exception {
        // Get the userstory
        restUserstoryMockMvc.perform(get("/api/userstories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserstory() throws Exception {
        // Initialize the database
        userstoryRepository.saveAndFlush(userstory);
        userstorySearchRepository.save(userstory);
        int databaseSizeBeforeUpdate = userstoryRepository.findAll().size();

        // Update the userstory
        Userstory updatedUserstory = userstoryRepository.findOne(userstory.getId());
        updatedUserstory
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .comments(UPDATED_COMMENTS)
            .status(UPDATED_STATUS)
            .priority(UPDATED_PRIORITY);

        restUserstoryMockMvc.perform(put("/api/userstories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserstory)))
            .andExpect(status().isOk());

        // Validate the Userstory in the database
        List<Userstory> userstoryList = userstoryRepository.findAll();
        assertThat(userstoryList).hasSize(databaseSizeBeforeUpdate);
        Userstory testUserstory = userstoryList.get(userstoryList.size() - 1);
        assertThat(testUserstory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserstory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserstory.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testUserstory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserstory.getPriority()).isEqualTo(UPDATED_PRIORITY);

        // Validate the Userstory in Elasticsearch
        Userstory userstoryEs = userstorySearchRepository.findOne(testUserstory.getId());
        assertThat(userstoryEs).isEqualToComparingFieldByField(testUserstory);
    }

    @Test
    @Transactional
    public void updateNonExistingUserstory() throws Exception {
        int databaseSizeBeforeUpdate = userstoryRepository.findAll().size();

        // Create the Userstory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserstoryMockMvc.perform(put("/api/userstories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userstory)))
            .andExpect(status().isCreated());

        // Validate the Userstory in the database
        List<Userstory> userstoryList = userstoryRepository.findAll();
        assertThat(userstoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserstory() throws Exception {
        // Initialize the database
        userstoryRepository.saveAndFlush(userstory);
        userstorySearchRepository.save(userstory);
        int databaseSizeBeforeDelete = userstoryRepository.findAll().size();

        // Get the userstory
        restUserstoryMockMvc.perform(delete("/api/userstories/{id}", userstory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userstoryExistsInEs = userstorySearchRepository.exists(userstory.getId());
        assertThat(userstoryExistsInEs).isFalse();

        // Validate the database is empty
        List<Userstory> userstoryList = userstoryRepository.findAll();
        assertThat(userstoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserstory() throws Exception {
        // Initialize the database
        userstoryRepository.saveAndFlush(userstory);
        userstorySearchRepository.save(userstory);

        // Search the userstory
        restUserstoryMockMvc.perform(get("/api/_search/userstories?query=id:" + userstory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userstory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userstory.class);
        Userstory userstory1 = new Userstory();
        userstory1.setId(1L);
        Userstory userstory2 = new Userstory();
        userstory2.setId(userstory1.getId());
        assertThat(userstory1).isEqualTo(userstory2);
        userstory2.setId(2L);
        assertThat(userstory1).isNotEqualTo(userstory2);
        userstory1.setId(null);
        assertThat(userstory1).isNotEqualTo(userstory2);
    }
}
