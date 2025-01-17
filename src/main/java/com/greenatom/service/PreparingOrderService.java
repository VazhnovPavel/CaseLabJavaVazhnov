package com.greenatom.service;

import com.greenatom.domain.dto.employee.EntityPage;
import com.greenatom.domain.dto.preparing_order.PreparingOrderResponseDTO;
import com.greenatom.domain.dto.preparing_order.PreparingOrderSearchCriteria;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PreparingOrderService {

    List<PreparingOrderResponseDTO> findAll(EntityPage entityPage,
                                            PreparingOrderSearchCriteria preparingOrderSearchCriteria);

    PreparingOrderResponseDTO findOne(Long id);

    void appointCollector(@Nullable Long employeeId , Long preparingOrderId);

    void finishPreparingOrder(@Nullable Long employeeId, Long preparingOrderId);
}
