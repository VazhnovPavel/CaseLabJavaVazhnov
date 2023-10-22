package com.greenatom.service.impl;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.domain.entity.Product;
import com.greenatom.domain.mapper.ProductMapper;
import com.greenatom.repository.ProductRepository;
import com.greenatom.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ProductServiceImpl является сервисом для работы со складом. Он использует ProductRepository для доступа к базе
 * данных, преобразует продукты в формат DTO и возвращает список товаров или конкретный товар по его ID.
 * @autor Максим Быков
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {
        log.debug("Request to get all Products");
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return Optional.ofNullable(productMapper.toDto(productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Request not found with id: " + id))));
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO product) {
        log.debug("Request to partially update Product : {}", product);
        return productRepository
                .findById(product.getId())
                .map(existingEvent -> {
                    productMapper.partialUpdate(existingEvent, product);

                    return existingEvent;
                })
                .map(productRepository::save)
                .map(productMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository
                .findById(id)
                .ifPresent(product -> {
                    productRepository.delete(product);
                    log.debug("Deleted Product: {}", product);
                });
    }
}