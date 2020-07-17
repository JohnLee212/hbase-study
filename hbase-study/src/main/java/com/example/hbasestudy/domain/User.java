package com.example.hbasestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liwenxing
 * @Version 1.0
 * @Description ces
 * @date 2020/7/15 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private String age;

    private String birthDay;
}
