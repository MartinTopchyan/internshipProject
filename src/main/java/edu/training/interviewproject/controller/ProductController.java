package edu.training.interviewproject.controller;

import edu.training.interviewproject.model.Product;
import edu.training.interviewproject.service.BaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final BaseService baseService;

    @Autowired
    public ProductController(BaseService baseService){
        this.baseService = baseService;
    }

    @PostMapping("/api/data")
    public ResponseEntity<String> addProducts(@RequestParam String link){
        this.baseService.add(link);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @GetMapping("/api/data")
    public ResponseEntity<List<Product>> search(@RequestParam String query) {
        List<Product> products = this.baseService.search(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
