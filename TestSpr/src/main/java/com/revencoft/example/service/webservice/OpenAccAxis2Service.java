/**
 * 
 */
package com.revencoft.example.service.webservice;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author mengqingyan
 * @version
 */
//@Service
public class OpenAccAxis2Service {

	private boolean isolateThreadPool = true;

	private Setter commandConfig;
	private Axis2Client axis2Client;

	

	public String checkAccount(String account) throws Exception {
		
		CheckAccountCommand ckAccCommand = new CheckAccountCommand(commandConfig, axis2Client, account);
		
		return ckAccCommand.execute();
	}

	@PostConstruct
	public void init() {
		
		System.setProperty("javax.net.ssl.trustStore",
				"D:/ssl/client.truststore");

		axis2Client = new Axis2Client();

		commandConfig = Setter.withGroupKey(
				HystrixCommandGroupKey.Factory.asKey("OpenAccWS_Group"))
				.andCommandKey(
						HystrixCommandKey.Factory.asKey("OpenAccCommand"));

		HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties
				.Setter();

		commandConfig.andCommandPropertiesDefaults(commandProperties);
		// 设置短路规则 //
		// 设置短路后的保护时间 ，默认为5秒，改为20秒方便演示
		commandProperties.withCircuitBreakerSleepWindowInMilliseconds(20000)
				// 多少百分比的失败在rolling windows内发生，计算为短路。默认为50%，无改变.
				.withCircuitBreakerErrorThresholdPercentage(50)
				// rolling windows 长度，默认为20秒，改为120秒方便演示。同时相应改变桶的数量.
				.withMetricsRollingStatisticalWindowInMilliseconds(120000)
				.withMetricsRollingStatisticalWindowBuckets(120)
				// 至少多少请求在rolling window内发生，才开始触发短路的计算，默认为20, 设为3方便演示.
				.withCircuitBreakerRequestVolumeThreshold(3);

		if (isolateThreadPool) {
			//超时时间
			commandProperties.withExecutionTimeoutInMilliseconds(5000);
			// 线程池属性， 线程池大小，默认为10，无改变。待执行队列的大小，默认为5，无改变.
			commandConfig
					.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
							.Setter().withCoreSize(10)
							.withQueueSizeRejectionThreshold(5));
		} else {
			commandProperties.withExecutionIsolationStrategy(
					ExecutionIsolationStrategy.SEMAPHORE)
					.withExecutionIsolationSemaphoreMaxConcurrentRequests(10);
		}

	}

	public void setIsolateThreadPool(boolean isolateThreadPool) {
		this.isolateThreadPool = isolateThreadPool;
	}

}
