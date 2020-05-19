package com.example.demo.spring;

import lombok.Data;

import java.util.List;

/**
 * @author haitao.chen
 * @date 2020/5/18
 */
@Data
public class MappingTO {

    private String name;

    private List<Integer> tid;
}
