package com.revencoft.hystrix_webservice.aspect.hystrix;

import java.io.IOException;
import java.util.Properties;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.revencoft.hystrix_webservice.service.hystrix.command.CommandProperty;
import com.revencoft.hystrix_webservice.service.hystrix.command.WebServiceCommand;

/**
 * @author mengqingyan
 * @version 
 */

//@Component
@Aspect
public class WebServiceHystrixAspect {
	
	private final Logger log = Logger.getLogger(getClass());

	
	private Setter commandConfig;
	private boolean isolateThreadPool = true;
	
	private Properties hystrixProperties;
	
	@Pointcut("@annotation(com.revencoft.hystrix_webservice.annotation.Hystrix)")
	public void wsHystrixPoint(){}
	
	@Around("wsHystrixPoint()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("begin Hystrix command.");
		WebServiceCommand command = new WebServiceCommand(commandConfig);
		command.setJoinPoint(joinPoint);
		return command.execute();
	}
	
	
	public void setHystrixProperties(Resource hystrixProperties) throws IOException {
		this.hystrixProperties = PropertiesLoaderUtils.loadProperties(hystrixProperties);
	}

	@PostConstruct
	public void init() throws Exception {

		CommandProperty props = new CommandProperty(hystrixProperties);
		
		commandConfig = Setter.withGroupKey(
				HystrixCommandGroupKey.Factory.asKey(props.groupKey))
				.andCommandKey(
						HystrixCommandKey.Factory.asKey(props.commandKey));

		HystrixCommandProperties.Setter commandProperties = HystrixCommandProperties
				.Setter();

		commandConfig.andCommandPropertiesDefaults(commandProperties);
		// 设置短路规则 //
		// 设置短路后的保护时间 ，默认为5秒，改为20秒方便演示
		commandProperties.withCircuitBreakerSleepWindowInMilliseconds(props.protectionTimeWhenShortCircuit)
				// 多少百分比的失败在rolling windows内发生，计算为短路。默认为50%，无改变.
				.withCircuitBreakerErrorThresholdPercentage(props.failPercentageForShortCircuit)
				// rolling windows 长度，默认为20秒，改为120秒方便演示。同时相应改变桶的数量.
				.withMetricsRollingStatisticalWindowInMilliseconds(props.rollingWindowInMilliseconds)
				.withMetricsRollingStatisticalWindowBuckets(props.rollingWindowBuckets)
				// 至少多少请求在rolling window内发生，才开始触发短路的计算，默认为20, 设为3方便演示.
				.withCircuitBreakerRequestVolumeThreshold(props.thresholdForShortCircuit);

		if (isolateThreadPool) {
			// 超时时间
			commandProperties.withExecutionTimeoutInMilliseconds(props.executionTimeoutInMilliseconds);
			// 线程池属性， 线程池大小，默认为10，无改变。待执行队列的大小，默认为5，无改变.
			commandConfig
					.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
							.Setter().withCoreSize(props.threadPoolCoreSize)
							.withQueueSizeRejectionThreshold(props.threadPoolQueneSize));
		} else {
			commandProperties.withExecutionIsolationStrategy(
					ExecutionIsolationStrategy.SEMAPHORE)
					.withExecutionIsolationSemaphoreMaxConcurrentRequests(props.executionIsolationSemaphoreMaxConcurrentRequests);
		}

	}

}
