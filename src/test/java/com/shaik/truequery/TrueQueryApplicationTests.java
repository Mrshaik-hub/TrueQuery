package com.shaik.truequery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrueQueryApplicationTests {


    @Test
    void contextLoads() {
    }
    @Test
    void sanityCheck() {
        String projectName = "TrueQuery";
        assertThat(projectName).isEqualTo("TrueQuery");
    }

}
