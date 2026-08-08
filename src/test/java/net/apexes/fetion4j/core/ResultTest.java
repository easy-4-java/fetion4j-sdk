package net.apexes.fetion4j.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void shouldCreateResultWithAllFields() {
        Result result = new Result(200, "OK", Result.Type.SUCCESS, "Operation succeeded");
        assertEquals(200, result.getStatus());
        assertEquals("OK", result.getStatusMessage());
        assertEquals(Result.Type.SUCCESS, result.getType());
        assertEquals("Operation succeeded", result.getDescribe());
    }

    @Test
    void shouldCreateFailureResult() {
        Result result = new Result(500, "Error", Result.Type.FAILURE, "Something went wrong");
        assertEquals(Result.Type.FAILURE, result.getType());
    }

    @Test
    void shouldHaveCorrectTypeEnum() {
        assertEquals(Result.Type.SUCCESS, Result.Type.valueOf("SUCCESS"));
        assertEquals(Result.Type.FAILURE, Result.Type.valueOf("FAILURE"));
    }

    @Test
    void shouldReturnMeaningfulToString() {
        Result result = new Result(200, "OK", Result.Type.SUCCESS, "done");
        String str = result.toString();
        assertTrue(str.contains("200"));
        assertTrue(str.contains("OK"));
        assertTrue(str.contains("done"));
    }
}
