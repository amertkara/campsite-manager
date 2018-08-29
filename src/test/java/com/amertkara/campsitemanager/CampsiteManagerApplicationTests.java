package com.amertkara.campsitemanager;

import com.amertkara.campsitemanager.config.DatasourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import(DatasourceConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CampsiteManagerApplicationTests {
	@Test
	public void contextLoads() {
	}
}
