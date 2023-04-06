package com.johann.designPattern.designPatterns23.V_visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举遍历元素
 * @author Johann
 * @version 1.0
 * @see
 **/
public class ObjectStructure {
    private List<Element> elements;

    public ObjectStructure() {
        this.elements = new ArrayList<>();
    }

    public void attach(Element element) {
        elements.add(element);
    }

    public void detach(Element element) {
        elements.remove(element);
    }

    public void accept(Visitor visitor) {
        for (Element element : elements) {
            element.accept(visitor);
        }
    }
}
