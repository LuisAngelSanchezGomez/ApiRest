package com.demo.Product.Controller;


import com.demo.Product.Model.SubcategoryModel;
import com.demo.Product.Repository.SubcategoryRepository;
import com.demo.Product.Service.impl.DefaultSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subcategorias")
public class SubcategoryController {
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private DefaultSubcategoryService subcategoryService;

    @GetMapping
    public List<SubcategoryModel> getAllSubcategories() {
        return getSubcategoryService().getAllSubcategories();
    }

    @GetMapping("/{subcategoryCode}")
    public Optional<SubcategoryModel> getSubcategoryById(@PathVariable String code){
        return getSubcategoryService().getSubcategoryByCode(code);
    }

    @PostMapping
    public ResponseEntity createSubcategories(@RequestBody List<SubcategoryModel> subcategories){
        try {
            getSubcategoryService().createSubcategories(subcategories);
            return ResponseEntity.ok(subcategories);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{subcategoryCode}")
    public ResponseEntity<SubcategoryModel> updateSubcategory(@PathVariable String subcategoryCode, @RequestBody SubcategoryModel subcategoryData){
        try {
            Optional<SubcategoryModel> optionalSubcategoryModel= getSubcategoryService().updateSubcategory(subcategoryCode);
            if (optionalSubcategoryModel.isPresent()){
                SubcategoryModel subcategoryModel = optionalSubcategoryModel.get();
                subcategoryModel.setName(subcategoryData.getName());
                subcategoryModel.setCode(subcategoryData.getCode());
                getSubcategoryRepository().save(subcategoryModel);
                return ResponseEntity.ok(subcategoryModel);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{subcategoryCode}")
    public  ResponseEntity<Void> deleteSubCategory(@PathVariable String subcategoryCode){
        return getSubcategoryService().deleteSubcategoryByCode(subcategoryCode);
    }

    public SubcategoryRepository getSubcategoryRepository() {
        return subcategoryRepository;
    }

    public void setSubcategoryRepository(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public DefaultSubcategoryService getSubcategoryService() {
        return subcategoryService;
    }

    public void setSubcategoryService(DefaultSubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }
}
