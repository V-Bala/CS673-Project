package co.metcsprojectone.service;

import com.codahale.metrics.annotation.Timed;
import co.metcsprojectone.domain.*;
import co.metcsprojectone.repository.*;
import co.metcsprojectone.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private CommentSearchRepository commentSearchRepository;

    @Inject
    private IssueRepository issueRepository;

    @Inject
    private IssueSearchRepository issueSearchRepository;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectSearchRepository projectSearchRepository;

    @Inject
    private RequirementRepository requirementRepository;

    @Inject
    private RequirementSearchRepository requirementSearchRepository;

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private TaskSearchRepository taskSearchRepository;

    @Inject
    private UserstoryRepository userstoryRepository;

    @Inject
    private UserstorySearchRepository userstorySearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Comment.class, commentRepository, commentSearchRepository);
        reindexForClass(Issue.class, issueRepository, issueSearchRepository);
        reindexForClass(Project.class, projectRepository, projectSearchRepository);
        reindexForClass(Requirement.class, requirementRepository, requirementSearchRepository);
        reindexForClass(Task.class, taskRepository, taskSearchRepository);
        reindexForClass(Userstory.class, userstoryRepository, userstorySearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T> void reindexForClass(Class<T> entityClass, JpaRepository<T, Long> jpaRepository,
                                                          ElasticsearchRepository<T, Long> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
