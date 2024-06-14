package com.threec.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class ProdApplication.
 * <p>
 * 生产者
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 14/6/24
 */
@SpringBootApplication
public class ProdApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdApplication.class,args);
    }
}
