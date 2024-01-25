package org.example.config.textXPath;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public final class TextXPath {
    // клас, який використовує XPath для вилучення текстового вмісту вузла з XML-документа
    private final XML xml;
    private final String node;

    @Override
    public String toString() {
        return this.xml.nodes(this.node)
                .get(0)
                .xpath("text()")
                .get(0);
        //this.xml.nodes(this.node) знаходить усі вузли, які відповідають XPath-виразу(this.node)
        //get(0) отримує перший вузол зі списку вузлів
        //.xpath("text()") застосовує XPath-вираз "text()" для отримання текстового вмісту обраного вузла
        // get(0) отримує перший елемент зі списку текстового вмісту
        // та повертає отриманий текстоваий вміст у вигляді рядка
    }
}
