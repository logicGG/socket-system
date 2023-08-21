package club.ovelya.socketsystem.config;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {


  @Bean(name = "asyncPoolTaskExecutor")
  public ThreadPoolTaskExecutor executor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    //核心线程数量
    taskExecutor.setCorePoolSize(10);
    //线程池维护线程的最大数量
    taskExecutor.setMaxPoolSize(100);
    //缓存队列
    taskExecutor.setQueueCapacity(50);
    //允许的核心线程外的线程的空闲时间
    taskExecutor.setKeepAliveSeconds(200);
    taskExecutor.setThreadNamePrefix("async-");
    //拒绝策略重试添加当前的任务，自动重复调用 execute() 方法，直到成功
    taskExecutor.setRejectedExecutionHandler(new CallerRunsPolicy());
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new SpringAsyncExceptionHandler();
  }

  static class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
      log.info(throwable.getMessage());
    }
  }

}
