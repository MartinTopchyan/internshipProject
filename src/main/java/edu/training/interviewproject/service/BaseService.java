package edu.training.interviewproject.service;

import com.google.gson.Gson;
import edu.training.interviewproject.config.ESConfig;
import edu.training.interviewproject.model.Category;
import edu.training.interviewproject.model.Product;
import edu.training.interviewproject.repository.CategoryRepository;
import edu.training.interviewproject.repository.ProductRepository;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class BaseService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  ESConfig esConfig;

    @Autowired
    public BaseService(ProductRepository productRepository, CategoryRepository categoryRepository,ESConfig esConfig) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.esConfig = esConfig;
    }


    public void add(String link) {
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        sendRequest(link, products, categories);

        products.forEach(product -> System.out.println(product.getDescription()));
        this.productRepository.saveAll(products);

        this.categoryRepository.saveAll(categories);
    }

    public List<Product> search(String query) {
        ElasticsearchOperations elasticsearchOperations = esConfig.elasticsearchTemplate();
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(query,"name","description");
        Query query2 = new NativeSearchQueryBuilder()
            .withQuery(multiMatchQuery)
            .build();

        List<Product> products = new ArrayList<>();

        SearchHits<Product> productSearchHits = elasticsearchOperations.search(query2, Product.class);
        productSearchHits.getSearchHits().forEach(searchHit -> products.add(searchHit.getContent()));
        return products;
    }

    private void sendRequest(String urlStr, List<Product> products, List<Category> categories){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream responseStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(responseStream));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            JSONObject obj  = XML.toJSONObject(content.toString()).getJSONObject("dc_catalog").getJSONObject("delivery_service");
            Product[] productArray = new Gson().fromJson(obj.getJSONObject("products").get("product").toString(), Product[].class);
            Category[] categoryArray = new Gson().fromJson(obj.getJSONObject("categories").get("category").toString(), Category[].class);

            products.addAll(Arrays.asList(productArray));
            categories.addAll(Arrays.asList(categoryArray));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
