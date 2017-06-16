package co.metcsprojectone.repository.search;

import co.metcsprojectone.domain.Tmember;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tmember entity.
 */
public interface TmemberSearchRepository extends ElasticsearchRepository<Tmember, Long> {
}
