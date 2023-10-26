package com.greenatom.service.impl;

import com.greenatom.domain.dto.item.OrderItemDTO;
import com.greenatom.domain.mapper.OrderItemMapper;
import com.greenatom.repository.OrderItemRepository;
import com.greenatom.service.OrderItemService;
import com.greenatom.utils.exception.OrderItemException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * CartProductServiceImpl является сервисом для работы со списком покупок. Он использует различные репозитории для
 * доступа к данным и преобразует их с помощью mapper в формат DTO (Data Transfer Object).
 * findAll() - метод для получения всех покупок из заказа. Он регистрирует сообщение в логе и передает список
 * продуктов в mapper для преобразования в формат DTO.
 *
 * <p>findOne() - метод для получения конкретного товара из заказа по его ID. Он также регистрирует сообщение
 * в логе и преобразует найденный товар в формат DTO с помощью mapper. В случае, если товар не найден,
 * выбрасывается исключение.
 * @autor Максим Быков, Даниил Змаев
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final Logger log = LoggerFactory.getLogger(OrderItemService.class);
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDTO> findAll() {
        log.debug("Order to get all CartProducts");
        return orderItemMapper.toDto(orderItemRepository.findAll());
    }

    @Override
    public OrderItemDTO findOne(Long id) {
        log.debug("Order to get CartProduct : {}", id);
        return orderItemMapper.toDto(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER::get));
    }

    @Override
    public OrderItemDTO updateCartProduct(OrderItemDTO cartProduct) {
        log.debug("Order to partially update CartProduct : {}", cartProduct);
        return orderItemRepository
                .findById(cartProduct.getId())
                .map(existingEvent -> {
                    orderItemMapper.partialUpdate(existingEvent, cartProduct);

                    return existingEvent;
                })
                .map(orderItemRepository::save)
                .map(orderItemMapper::toDto).orElseThrow(
                        EntityNotFoundException::new);
    }

    @Override
    public void deleteCartProduct(Long id) {
        orderItemRepository.delete(orderItemRepository
                .findById(id)
                .orElseThrow(OrderItemException.CODE.NO_SUCH_ORDER_ITEM::get));
    }
}