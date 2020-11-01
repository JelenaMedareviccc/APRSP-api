package com.example.project;

import com.google.common.collect.ImmutableSet;
import com.example.project.dtos.AddAuthuserDto;
import com.example.project.dtos.GetAuthuserDto;
import com.example.project.models.Authuser;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Set;

public class GenericModelConverter implements GenericConverter {
    /**
     * This method converts GetUserDto into User
     */
    private static Authuser convertGetUserDto(Object o) {
        GetAuthuserDto getUserDto = (GetAuthuserDto) o;
        System.out.println("Convert get user dto");
        return Authuser.builder()
                .id(getUserDto.getId())
                .firstName(getUserDto.getFirstName())
                .lastName(getUserDto.getLastName())
                .email(getUserDto.getEmail())
                .username(getUserDto.getUsername())
                .contact(getUserDto.getContact())
                .enabled(getUserDto.isEnabled())
                .build();
    }
    /**
     * This method converts User into GetUserDto
     */
    private static GetAuthuserDto convertUser(Object o) {
        Authuser user = (Authuser) o;
        System.out.println("GET user dto");
        return GetAuthuserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .contact(user.getContact())
                .enabled(user.isEnabled())
                .build();
    }

    /**
     * This method converts AddUserDto into User
     */
    private static Authuser convertAddUserDto(Object o) {
        AddAuthuserDto addUserDto = (AddAuthuserDto) o;

        return Authuser.builder()
                .id(addUserDto.getId())
                .firstName(addUserDto.getFirstName())
                .lastName(addUserDto.getLastName())
                .email(addUserDto.getEmail())
                .username(addUserDto.getUsername())
                .password(addUserDto.getPassword())
                .contact(addUserDto.getContact())
                .enabled(addUserDto.isEnabled())
                .build();
    }

    /**
     * This method is used to set types which can convert
     */
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        ConvertiblePair[] pairs = new ConvertiblePair[]{
                new ConvertiblePair(AddAuthuserDto.class, Authuser.class),
                new ConvertiblePair(Authuser.class, GetAuthuserDto.class),
                new ConvertiblePair(GetAuthuserDto.class, Authuser.class)
        };
        return ImmutableSet.copyOf(pairs);
    }

    /**
     * A method which converts models based on their types
     */
    @Override
    public Object convert(Object o, TypeDescriptor sourceDescriptor, TypeDescriptor targetDescriptor) {
        if (o instanceof Authuser) {
            return convertUser(o);
        } else if (o instanceof GetAuthuserDto) {
            return convertGetUserDto(o);
        } else if (o instanceof AddAuthuserDto) {
            return convertAddUserDto(o);
        } else {
            throw new IllegalArgumentException("Conversion arguments does not match to any converter");
        }
    }

}

