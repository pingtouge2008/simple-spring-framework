package com.ptg.springframework.context.event;

import com.ptg.springframework.context.ApplicationEvent;
import com.ptg.springframework.context.ApplicationListener;

/**
 * 应用程序事件多播器
 * 通俗的理解: 用来广播事件
 */
public interface ApplicationEventMulticaster {
    /**
     * Add a listener to be notified of all events.
     *
     * @param listener the listener to add
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener the listener to remove
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners.
     *
     * @param event the event to multicast
     */
    void multicastEvent(ApplicationEvent event);
}
