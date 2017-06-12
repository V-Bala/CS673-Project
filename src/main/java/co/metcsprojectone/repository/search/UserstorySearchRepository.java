package co.metcsprojectone.repository.search;

import co.metcsprojectone.domain.Userstory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Userstory entity.
 */
public interface UserstorySearchRepository extends ElasticsearchRepository<Userstory, Long> {
}
