package net.apexes.fetion4j.core.client;

import static org.junit.jupiter.api.Assertions.*;

import net.apexes.fetion4j.core.util.XmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CmccMobileValidatorTest {

    private CmccMobileValidator validator;

    @BeforeEach
    void setUp() {
        String xmlStr = "<r><c v=\"cmcc\">"
                + "<d s=\"13500000000\" e=\"13999999999\"/>"
                + "<d s=\"13400000000\" e=\"13489999999\"/>"
                + "<d s=\"15900000000\" e=\"15999999999\"/>"
                + "</c></r>";
        XmlElement xml = new XmlElement();
        xml.parseString(xmlStr);
        validator = new CmccMobileValidator(xml);
    }

    @Test
    void shouldValidateCmccNumberInRange() {
        assertTrue(validator.isCmccMobileNo(13800138000L));
    }

    @Test
    void shouldValidateCmccNumberAtRangeStart() {
        assertTrue(validator.isCmccMobileNo(13500000000L));
    }

    @Test
    void shouldValidateCmccNumberAtRangeEnd() {
        assertTrue(validator.isCmccMobileNo(13999999999L));
    }

    @Test
    void shouldRejectNonCmccNumber() {
        assertFalse(validator.isCmccMobileNo(13000138000L));
    }

    @Test
    void shouldValidate134Range() {
        assertTrue(validator.isCmccMobileNo(13400000001L));
    }

    @Test
    void shouldValidate159Range() {
        assertTrue(validator.isCmccMobileNo(15912345678L));
    }

    @Test
    void shouldRejectNumberBelowRange() {
        assertFalse(validator.isCmccMobileNo(1340000000L));
    }

    @Test
    void shouldRejectNumberAboveRange() {
        assertFalse(validator.isCmccMobileNo(16000000000L));
    }
}
