package moim_today.domain.schedule;

import lombok.Builder;
import moim_today.global.error.InternalServerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

import static moim_today.global.constant.EveryTimeConstant.*;
import static moim_today.global.constant.exception.EveryTimeExceptionConstant.*;

@Builder
public record TimeTableParser(
        String timeTableXML
) {

    public static TimeTableParser toDomain(final String timeTableXML) {
        return TimeTableParser.builder()
                .timeTableXML(timeTableXML)
                .build();
    }

    public NodeList getSubjects() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new InternalServerException(PARSER_CONFIG_ERROR.value());
        }

        try (StringReader reader = new StringReader(timeTableXML)) {
            Document doc = builder.parse(new InputSource(reader));
            return doc.getElementsByTagName(EVERYTIME_SUBJECT.value());
        }  catch (SAXException | IOException e) {
            throw new InternalServerException(SAX_IO_ERROR.value());
        }
    }
}
