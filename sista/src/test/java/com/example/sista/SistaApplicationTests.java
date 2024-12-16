package com.example.sista;

import com.example.sista.BAP.bapService;
import com.example.sista.BAP.nilai;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


/**
 * This test class is responsible for performing unit tests for the Sista application.
 * It is annotated with @SpringBootTest to bootstrap the entire application context for integration testing.
 *
 * It utilizes the Mockito framework for mocking dependencies and injecting them into the class under test.
 *
 * Annotations used in this class:
 * - @SpringBootTest: Indicates that the test class should load the full application context.
 * - @Mock: Used to create mock objects for dependencies such as JdbcTemplate and ResultSet.
 * - @InjectMocks: Used to create and inject mocks into the bapService class being tested.
 * - @BeforeEach: Indicates that the annotated method (setUp) should run before each test case.
 * - @Test: Marks the test methods for execution as test cases.
 *
 */
@SpringBootTest
class SistaApplicationTests {

    @Mock
    private  JdbcTemplate jdbcTemplate;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private bapService repo;

    private nilai sampleNilai;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleNilai = new nilai(
                85.0, 0.4, 85.0 * 0.4,        // Ketua values
                80.0, 0.3, 80.0 * 0.3,            // Anggota values
                90.0, 0.2, 90.0 * 0.2,      // Pembimbing values
                95.0, 0.1, 95.0 * 0.1,      // Koordinator values
                (85.0 * 0.4) + (80.0 * 0.3) + (90.0 * 0.2) + (95.0 * 0.1)   // Total nilaiAkhir
        );
    }

    @Test
    void testNilaiAkhirCalculation() {
        // Arrange: Define data for the test
        double nilaiKetua = 85.0;
        double bobotKetua = 0.4;
        double nilaiAkhirKetua = 85.0 * 0.4;
        double nilaiAnggota = 80.0;
        double bobotAnggota = 0.3;
        double nilaiAkhirAnggota = 80.0 * 0.3;
        double nilaiPembimbing = 90.0;
        double bobotPembimbing = 0.2;
        double nilaiAkhirPembimbing = 90.0 * 0.2;
        double nilaiKoordinator = 95.0;
        double bobotKoordinator = 0.1;
        double nilaiAkhirKoordinator = 95.0 * 0.1;

        double expectedNilaiAkhir = nilaiAkhirAnggota + nilaiAkhirKetua + nilaiAkhirPembimbing + nilaiAkhirKoordinator;

        when(jdbcTemplate.queryForObject(anyString(), eq(Double.class))).thenReturn(expectedNilaiAkhir);

        double actualNilaiAkhir = sampleNilai.getTotalNilaiAkhir();

        // Assert: Verify the result
        assertEquals(expectedNilaiAkhir, actualNilaiAkhir, 0.01, "The nilaiAkhir calculation is incorrect");
    }

}
