package co.metcsprojectone.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.metcsprojectone.domain.Requirement;

import co.metcsprojectone.repository.RequirementRepository;
import co.metcsprojectone.repository.search.RequirementSearchRepository;
import co.metcsprojectone.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Requirement.
 */
@RestController
@RequestMapping("/api")
public class RequirementResource {

    private final Logger log = LoggerFactory.getLogger(RequirementResource.class);

    private static final String ENTITY_NAME = "requirement";
        
    private final RequirementRepository requirementRepository;

    private final RequirementSearchRepository requirementSearchRepository;

    public RequirementResource(RequirementRepository requirementRepository, RequirementSearchRepository requirementSearchRepository) {
        this.requirementRepository = requirementRepository;
        this.requirementSearchRepository = requirementSearchRepository;
    }

    /**
     * POST  /requirements : Create a new requirement.
     *
     * @param requirement the requirement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requirement, or with status 400 (Bad Request) if the requirement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requirements")
    @Timed
    public ResponseEntity<Requirement> createRequirement(@RequestBody Requirement requirement) throws URISyntaxException {
        log.debug("REST request to save Requirement : {}", requirement);
        if (requirement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requirement cannot already have an ID")).body(null);
        }
        Requirement result = requirementRepository.save(requirement);
        requirementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requirements : Updates an existing requirement.
     *
     * @param requirement the requirement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requirement,
     * or with status 400 (Bad Request) if the requirement is not valid,
     * or with status 500 (Internal Server Error) if the requirement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requirements")
    @Timed
    public ResponseEntity<Requirement> updateRequirement(@RequestBody Requirement requirement) throws URISyntaxException {
        log.debug("REST request to update Requirement : {}", requirement);
        if (requirement.getId() == null) {
            return createRequirement(requirement);
        }
        Requirement result = requirementRepository.save(requirement);
        requirementSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requirement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requirements : get all the requirements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requirements in body
     */
    @GetMapping("/requirements")
    @Timed
    public List<Requirement> getAllRequirements() {
        log.debug("REST request to get all Requirements");
        List<Requirement> requirements = requirementRepository.findAll();
        return requirements;
    }

    /**
     * GET  /requirements/:id : get the "id" requirement.
     *
     * @param id the id of the requirement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requirement, or with status 404 (Not Found)
     */
    @GetMapping("/requirements/{id}")
    @Timed
    public ResponseEntity<Requirement> getRequirement(@PathVariable Long id) {
        log.debug("REST request to get Requirement : {}", id);
        Requirement requirement = requirementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requirement));
    }

    /**
     * DELETE  /requirements/:id : delete the "id" requirement.
     *
     * @param id the id of the requirement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requirements/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequirement(@PathVariable Long id) {
        log.debug("REST request to delete Requirement : {}", id);
        requirementRepository.delete(id);
        requirementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requirements?query=:query : search for the requirement corresponding
     * to the query.
     *
     * @param query the query of the requirement search 
     * @return the result of the search
     */
    @GetMapping("/_search/requirements")
    @Timed
    public List<Requirement> searchRequirements(@RequestParam String query) {
        log.debug("REST request to search Requirements for query {}", query);
        return StreamSupport
            .stream(requirementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping("/projreq")
    @Timed
    public List<Requirement> getProjectIssues(@RequestParam Long id) {
        log.debug("REST request to get Issues for project : {}", id);
        List<Requirement> out = requirementRepository.findAllByProjectId(id);
        return out;
    }


}
