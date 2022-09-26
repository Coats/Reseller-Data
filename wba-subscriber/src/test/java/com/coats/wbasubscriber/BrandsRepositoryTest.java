package com.coats.wbasubscriber;


import com.coats.wbasubscriber.model.Brands;
import com.coats.wbasubscriber.repository.BrandsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigInteger;

@DataJpaTest
@AutoConfigureTestDatabase
public class BrandsRepositoryTest {

    @Autowired
    private BrandsRepository brandsRepository;

    @Test
    public void saveBrands(){
        Brands brand = Brands.builder()
                .brand_name("TestUnit")
                .build();

        brandsRepository.save(brand);

        Assertions.assertThat(brand.getId()).isGreaterThan(BigInteger.valueOf(0));
    }
}
