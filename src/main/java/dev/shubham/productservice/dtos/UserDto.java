package dev.shubham.productservice.dtos;

import dev.shubham.productservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
}
