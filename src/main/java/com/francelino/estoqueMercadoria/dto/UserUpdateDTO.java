package com.francelino.estoqueMercadoria.dto;

import com.francelino.estoqueMercadoria.services.validation.UserUpdateValid;

import java.io.Serializable;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

}
