//package org.example.douyin.converter;
//
//import org.example.douyin.entity.dto.RegisterDTO;
//import org.example.douyin.entity.Register;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @author bgmyangzhu
// * @date 2025/4/1 15:40
// */
//@Mapper(componentModel = "spring")
//public interface RegisterConverter {
//    
//    @Mapping(target = "encryptedPassword", expression = "java(passwordEncoder.encode(vo.getPassword()))")
//    RegisterDTO voToDto(Register vo, PasswordEncoder passwordEncoder);
//    
//}
