package org.example.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class BeanConfigTest {
    private XML loadXmlFromFile(String filePath) throws IOException {
        InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
        return new XMLDocument(inputStream.readAllBytes());
    }
    private ApplicationContext createContext() {
        return new AnnotationConfigApplicationContext(BeanConfig.class);
    }
    @Test
    void testProducerXmlBean() {
        ApplicationContext context = createContext();

        XML producerXml = context.getBean(XML.class);

        assertNotNull(producerXml);
    }
    @Test
    void testProducerXmlContent() throws IOException {
        String filePath = "/kafka/producer.xml";
        XML expectedXml = loadXmlFromFile(filePath);

        ApplicationContext context = createContext();

        XML actualXml = context.getBean("producerXml", XML.class);

        assertEquals(expectedXml.toString(), actualXml.toString());
    }
    @Test
    void testProducerXmlBean_IOException() {
        ApplicationContext context = mock(ApplicationContext.class);

        doAnswer(invocation -> {
            throw new IOException("Simulating IOException");
        }).when(context).getBean(eq("producerXml"), eq(XML.class));

        assertThrows(IOException.class, () -> context.getBean("producerXml", XML.class));
    }
}
