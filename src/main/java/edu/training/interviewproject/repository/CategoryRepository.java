package edu.training.interviewproject.repository;

import edu.training.interviewproject.model.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoryRepository extends ElasticsearchRepository<Category, Integer> {

}
