package edu.training.interviewproject.repository;

import edu.training.interviewproject.model.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;


@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Integer> {

}
