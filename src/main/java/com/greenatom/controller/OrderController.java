package com.greenatom.controller;

import com.greenatom.config.swagger.annotation.AccessDeniedResponse;
import com.greenatom.controller.api.OrderApi;
import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import com.greenatom.domain.dto.order.OrderResponseDTO;
import com.greenatom.domain.dto.order.OrderSearchCriteria;
import com.greenatom.service.OrderService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
 *
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

    @Override
    @GetMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.findOne(id));
    }

    @Override
    @GetMapping(produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@RequestParam(defaultValue = "0") Integer pagePosition,
                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                               @RequestParam(required = false) String linkToFolder,
                                                               @RequestParam(required = false) Instant orderDate,
                                                               @RequestParam(required = false) String orderStatus,
                                                               @RequestParam(required = false) String deliveryType,
                                                               @RequestParam(required = false) Long client,
                                                               @RequestParam(required = false) Long employee,
                                                               @RequestParam(required = false, defaultValue = "id")
                                                                   String sortBy,
                                                               @RequestParam(required = false, defaultValue = "ASC")
                                                                   Sort.Direction sortDirection) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderService.findAll(new EntityPage(pagePosition, pageSize, sortDirection, sortBy),
                            new OrderSearchCriteria(
                                    0L,
                                    client,
                                    employee,
                                    linkToFolder,
                                    orderDate,
                                    orderStatus,
                                    deliveryType)));
    }

    @Override
    @PostMapping(value = "/draft")
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> addDraftOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.createDraft(orderRequestDTO));
    }

    @Override
    @PostMapping(value = "/assign", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> generateOrder(@RequestParam Long orderId, @RequestParam Long employeeId) {
        orderService.generateOrder(orderId, employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PostMapping(value = "/finish-order", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> finishOrder(@RequestParam Long orderId, @RequestParam Long employeeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.finishOrder(orderId, employeeId));
    }

    @Override
    @DeleteMapping(value = "/{id}/delete-empty",
            produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PatchMapping(value = "/{id}", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id,
                                                        @RequestBody OrderResponseDTO order) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.updateOrder(order, id));
    }
}
