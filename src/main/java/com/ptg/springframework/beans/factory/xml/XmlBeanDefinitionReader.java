package com.ptg.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.ptg.springframework.beans.BeansException;
import com.ptg.springframework.beans.PropertyValue;
import com.ptg.springframework.beans.factory.config.BeanDefinition;
import com.ptg.springframework.beans.factory.config.BeanReference;
import com.ptg.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.ptg.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.ptg.springframework.core.io.Resource;
import com.ptg.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import static cn.hutool.core.util.StrUtil.*;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinition(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();

        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            Node child = childNodes.item(i);
            if (!(child instanceof Element)) continue;;
            if (!"bean".equals(child.getNodeName())) continue;

            Element bean = (Element) child;
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");

            Class<?> clazz = Class.forName(className);

            // 优先级 id > name
            String beanName = isNotEmpty(id) ? id : name;
            if (isEmpty(beanName)) {
                beanName = lowerFirst(clazz.getSimpleName());
            }

            // 定义BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                Node item = bean.getChildNodes().item(j);
                if (!(item instanceof Element)) continue;
                if (!"property".equals(item.getNodeName())) continue;
                // 解析标签property

                Element property = (Element) item;
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

                // 创建属性信息
                PropertyValue pv = new PropertyValue(attrName, value);

                beanDefinition.getPropertyValues().addPropertyValue(pv);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        loadBeanDefinitions(getResourceLoader().getResource(location));
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

}
