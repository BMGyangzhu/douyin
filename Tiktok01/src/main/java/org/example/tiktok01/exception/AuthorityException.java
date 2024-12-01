package org.example.tiktok01.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description:
 * @Author: Xhy
 * @CreateTime: 2023-10-24 15:43
 */
@Data
@AllArgsConstructor
public class AuthorityException extends Exception{

    private int code;

    private String msg;

    public AuthorityException(String msg){
        super(msg);
    }
}
