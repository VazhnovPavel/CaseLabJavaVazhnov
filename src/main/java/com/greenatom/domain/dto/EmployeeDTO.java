package com.greenatom.domain.dto;

import com.greenatom.domain.enums.JobPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the Employee.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Описание модели \"Сотрудник\"")
public class EmployeeDTO {

    @Schema(description = "Id сотрудника", example = "1")
    private Long id;

    @Schema(description = "Имя сотрудника", example = "Дмитрий")
    private String name;

    @Schema(description = "Фамилия сотрудника", example = "Пучков")
    private String surname;

    @Schema(description = "Отчество сотрудника", example = "Юрьевич")
    private String patronymic;

    @Schema(description = "Должность сотрудника", example = "MANAGER")
    private JobPosition jobPosition;

    @Schema(description = "Зарплата сотрудника", example = "100000")
    private String salary;

    @Schema(description = "Адресс сотрудника", example = "г. Москва")
    private String address;

    @Schema(description = "Номер телефона сотрудника", example = "895436848")
    private String phoneNumber;

    @Schema(description = "Пороль сотрудника", example = "Qwer123as")
    private String password;

    @Schema(description = "Имя пользователя сотрудника", example = "Puchkov_D_Y_1")
    private String username;

    @Schema(description = "Роль сотрудника", example = "ROLE_MANAGER")
    private RoleDto role;
}

