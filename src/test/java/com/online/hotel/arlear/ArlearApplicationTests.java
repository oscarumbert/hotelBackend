package com.online.hotel.arlear;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ArlearApplicationTests {

	@Test
	void contextLoads() {
		List strings = Arrays.asList("1", "2", "3", "4", "5");
		 
		// Comprobamos que contiene un determinado elemento en una determinada posición
		assertThat(strings).contains("1");
	}

}
