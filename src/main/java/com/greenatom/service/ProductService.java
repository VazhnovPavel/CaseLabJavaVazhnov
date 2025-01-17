package com.greenatom.service;

import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.domain.dto.product.ProductRequestDTO;
import com.greenatom.domain.dto.product.ProductResponseDTO;
import com.greenatom.domain.dto.product.ProductSearchCriteria;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAll(EntityPage entityPage, ProductSearchCriteria productSearchCriteria);

    ProductResponseDTO findOne(Long id);

    ProductResponseDTO save(ProductRequestDTO product);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO product);

    void deleteProduct(Long id);
    List<ProductResponseDTO> findAll(Integer pagePosition, Integer pageLength, String name, Integer cost);
}
