package com.cadastro.cliente.apicadastrocliente.dto;

import com.cadastro.cliente.apicadastrocliente.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) {

}
