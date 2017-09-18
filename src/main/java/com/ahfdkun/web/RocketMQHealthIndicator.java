package com.ahfdkun.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @Description http://localhost:8080/health/ ： 55 page
 *
 * @author zhaoyakun
 *
 * @date 2017年9月18日 下午11:17:52
 */
@Component
public class RocketMQHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		int code = check();
		if (check() != 0) {
			return Health.up().withDetail("Error code", code).build();
		}
		return Health.up().build();
	}

	private int check() {
		return 1;
	}
}
