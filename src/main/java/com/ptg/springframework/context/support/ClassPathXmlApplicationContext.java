package com.ptg.springframework.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
    }

    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
