package co.metcsprojectone.web.rest;

import co.metcsprojectone.domain.Comment;
import com.codahale.metrics.annotation.Timed;
import co.metcsprojectone.domain.Userstory;

import co.metcsprojectone.repository.UserstoryRepository;
import co.metcsprojectone.repository.search.UserstorySearchRepository;
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
 * REST controller for managing Userstory.
 */
@RestController
@RequestMapping("/api")
public class UserstoryResource {

    private final Logger log = LoggerFactory.getLogger(UserstoryResource.class);

    private static final String ENTITY_NAME = "userstory";
        
    private final UserstoryRepository userstoryRepository;

    private final UserstorySearchRepository userstorySearchRepository;

    public UserstoryResource(UserstoryRepository userstoryRepository, UserstorySearchRepository userstorySearchRepository) {
        this.userstoryRepository = userstoryRepository;
        this.userstorySearchRepository = userstorySearchRepository;
    }

    /**
     * POST  /userstories : Create a new userstory.
     *
     * @param userstory the userstory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userstory, or with status 400 (Bad Request) if the userstory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/userstories")
    @Timed
    public ResponseEntity<Userstory> createUserstory(@RequestBody Userstory userstory) throws URISyntaxException {
        log.debug("REST request to save Userstory : {}", userstory);
        if (userstory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userstory cannot already have an ID")).body(null);
        }
        Userstory result = userstoryRepository.save(userstory);
        //userstorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/userstories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userstories : Updates an existing userstory.
     *
     * @param userstory the userstory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userstory,
     * or with status 400 (Bad Request) if the userstory is not valid,
     * or with status 500 (Internal Server Error) if the userstory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/userstories")
    @Timed
    public ResponseEntity<Userstory> updateUserstory(@RequestBody Userstory userstory) throws URISyntaxException {
        log.debug("REST request to update Userstory : {}", userstory);
        if (userstory.getId() == null) {
            return createUserstory(userstory);
        }
        Userstory result = userstoryRepository.save(userstory);
        //userstorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userstory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userstories : get all the userstories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userstories in body
     */
    @GetMapping("/userstories")
    @Timed
    public List<Userstory> getAllUserstories() {
        log.debug("REST request to get all Userstories");
        List<Userstory> userstories = userstoryRepository.findAll();
        return userstories;
    }

    /**
     * GET  /userstories/:id : get the "id" userstory.
     *
     * @param id the id of the userstory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userstory, or with status 404 (Not Found)
     */
    @GetMapping("/userstories/{id}")
    @Timed
    public ResponseEntity<Userstory> getUserstory(@PathVariable Long id) {
        log.debug("REST request to get Userstory : {}", id);
        Userstory userstory = userstoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userstory));
    }

    /**
     * DELETE  /userstories/:id : delete the "id" userstory.
     *
     * @param id the id of the userstory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/userstories/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserstory(@PathVariable Long id) {
        log.debug("REST request to delete Userstory : {}", id);
        userstoryRepository.delete(id);
        //userstorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/userstories?query=:query : search for the userstory corresponding
     * to the query.
     *
     * @param query the query of the userstory search 
     * @return the result of the search
     */
    @GetMapping("/_search/userstories")
    @Timed
    public List<Userstory> searchUserstories(@RequestParam String query) {
        log.debug("REST request to search Userstories for query {}", query);
        return StreamSupport
            .stream(userstorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping("/projus")
    @Timed
    public List<Userstory> getProjectUserstories(@RequestParam Long id) {
        log.debug("REST request to get Userstories for project: {}", id);
        List<Userstory> out = userstoryRepository.findByProjectId(id);
        return out;
    }

}
