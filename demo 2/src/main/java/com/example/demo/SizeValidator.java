package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SizeValidator {

    private static final List<String> VALID_SIZES = Arrays.asList("small", "medium", "large");

    public static boolean validateSize(String size) {
        return size != null && VALID_SIZES.contains(size.toLowerCase());
    }
}
