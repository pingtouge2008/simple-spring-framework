package com.ptg.springframework.test.event;

import com.ptg.springframework.context.ApplicationListener;
import com.ptg.springframework.context.event.ApplicationContextEvent;
import com.ptg.springframework.context.event.ContextClosedEvent;
import com.ptg.springframework.context.event.ContextRefreshedEvent;
import com.ptg.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.Date;

public class EventTests {
    @Test
    public void customEventTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_event.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));
        applicationContext.registerShutdownHook();
    }


    public static class CustomEvent extends ApplicationContextEvent {
        private Long id;
        private String message;

        public CustomEvent(Object source, Long id, String message) {
            super(source);
            this.id = id;
            this.message = message;
        }
    }

    public static class CustomEventListener implements ApplicationListener<CustomEvent> {
        @Override
        public void onApplicationEvent(CustomEvent event) {
            System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());
            System.out.println("消息：" + event.id + ":" + event.message);
        }
    }

    public static class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            System.out.println("刷新事件：" + this.getClass().getName());
        }
    }

    public static class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.out.println("关闭事件：" + this.getClass().getName());
        }
    }

}
