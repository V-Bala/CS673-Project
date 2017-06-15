package co.metcsprojectone.web.rest;

import com.codahale.metrics.annotation.Timed;
import co.metcsprojectone.domain.Tmember;

import co.metcsprojectone.repository.TmemberRepository;
import co.metcsprojectone.repository.search.TmemberSearchRepository;
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
 * REST controller for managing Tmember.
 */
@RestController
@RequestMapping("/api")
public class TmemberResource {

    private final Logger log = LoggerFactory.getLogger(TmemberResource.class);

    private static final String ENTITY_NAME = "tmember";

    private final TmemberRepository tmemberRepository;

    private final TmemberSearchRepository tmemberSearchRepository;

    public TmemberResource(TmemberRepository tmemberRepository, TmemberSearchRepository tmemberSearchRepository) {
        this.tmemberRepository = tmemberRepository;
        this.tmemberSearchRepository = tmemberSearchRepository;
    }

    /**
     * POST  /tmembers : Create a new tmember.
     *
     * @param tmember the tmember to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tmember, or with status 400 (Bad Request) if the tmember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tmembers")
    @Timed
    public ResponseEntity<Tmember> createTmember(@RequestBody Tmember tmember) throws URISyntaxException {
        log.debug("REST request to save Tmember : {}", tmember);
        if (tmember.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tmember cannot already have an ID")).body(null);
        }
        Tmember result = tmemberRepository.save(tmember);
        tmemberSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tmembers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tmembers : Updates an existing tmember.
     *
     * @param tmember the tmember to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tmember,
     * or with status 400 (Bad Request) if the tmember is not valid,
     * or with status 500 (Internal Server Error) if the tmember couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tmembers")
    @Timed
    public ResponseEntity<Tmember> updateTmember(@RequestBody Tmember tmember) throws URISyntaxException {
        log.debug("REST request to update Tmember : {}", tmember);
        if (tmember.getId() == null) {
            return createTmember(tmember);
        }
        Tmember result = tmemberRepository.save(tmember);
        tmemberSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tmember.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tmembers : get all the tmembers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tmembers in body
     */
    @GetMapping("/tmembers")
    @Timed
    public List<Tmember> getAllTmembers() {
        log.debug("REST request to get all Tmembers");
        return tmemberRepository.findAll();
    }

    /**
     * GET  /tmembers/:id : get the "id" tmember.
     *
     * @param id the id of the tmember to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tmember, or with status 404 (Not Found)
     */
    @GetMapping("/tmembers/{id}")
    @Timed
    public ResponseEntity<Tmember> getTmember(@PathVariable Long id) {
        log.debug("REST request to get Tmember : {}", id);
        Tmember tmember = tmemberRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tmember));
    }

    /**
     * DELETE  /tmembers/:id : delete the "id" tmember.
     *
     * @param id the id of the tmember to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tmembers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTmember(@PathVariable Long id) {
        log.debug("REST request to delete Tmember : {}", id);
        tmemberRepository.delete(id);
        tmemberSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tmembers?query=:query : search for the tmember corresponding
     * to the query.
     *
     * @param query the query of the tmember search
     * @return the result of the search
     */
    @GetMapping("/_search/tmembers")
    @Timed
    public List<Tmember> searchTmembers(@RequestParam String query) {
        log.debug("REST request to search Tmembers for query {}", query);
        return StreamSupport
            .stream(tmemberSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
