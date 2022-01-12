package com.devcamp.Project.EsRepository;

import com.devcamp.Project.entity.Contract;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractElasticSearchRepository extends ElasticsearchRepository<Contract, Long> {
}
