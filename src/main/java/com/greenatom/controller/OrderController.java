package com.greenatom.controller;

import com.greenatom.config.swagger.annotation.AccessDeniedResponse;
import com.greenatom.controller.api.OrderApi;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Этот код представляет собой контроллер API для управления заявками. Он предоставляет набор методов
 * для выполнения различных операций с заявками:
 *
 * <p>– GET /get/{id}: Получение информации о запросе с указанным ID.
 * <p>– PUT /update: Обновление информации о запросе, используя данные из тела запроса.
 * <p>– POST /add: Создание нового запроса, используя данные из тела запроса.
 *
 * <p>Все эти операции выполняются с использованием сервиса OrderService, который реализует бизнес-логику
 * управления заявками.
 * @author Максим Быков
 * @version 1.0
 */

@RestController
@AccessDeniedResponse
@RequestMapping(value = "/api/orders")
public class OrderController implements OrderApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_SUPERVISOR')")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(@RequestParam(required = false, defaultValue = "0") Integer limit,
                                                            @RequestParam(required = false, defaultValue = "10") Integer offset,
                                                            @RequestParam(required = false, defaultValue = "orderDate") String sortField,
                                                            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                                                            @RequestParam(required = false) String orderStatus,
                                                            @RequestParam(required = false) String linkToFolder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService
                        .findByPaginationAndFilters(PageRequest.of(limit, offset, sort), orderStatus, linkToFolder));
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.findOne(id));
    }

    @GetMapping(value = "/employee/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER','ROLE_SUPERVISOR')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@Param("position") Integer pagePosition,
                                               @Param("length") Integer pageLength,
                                               @PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.findAll(pagePosition, pageLength, id));
    }

    @PostMapping(value = "/draft")
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> addDraftOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.createDraft(orderRequestDTO));
    }

    @PostMapping(value = "{id}/generate", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> generateOrder(@PathVariable Long id) {
        orderService.generateOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "{id}/finish-order", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> finishOrder(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.finishOrder(id));
    }

    @DeleteMapping(value = "/{id}/delete-empty",
            produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
