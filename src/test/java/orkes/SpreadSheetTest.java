package orkes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SpreadSheetTest {
    private SpreadSheet spreadsheet;

    @BeforeEach
    public void setUp() {
        spreadsheet = new SpreadSheet();
    }

    @Test
    public void shouldReturnCorrectValue() {
        spreadsheet.setCellValue("A5", 5);
        Assertions.assertEquals(5, spreadsheet.getCellValue("A5"));
    }

    @Test
    public void shouldReturnSumOfTwoCell() {
        spreadsheet.setCellValue("A1", -4);
        spreadsheet.setCellValue("A2", 6);
        spreadsheet.setCellValue("A3", "=A1+A2");
        spreadsheet.setCellValue("A4", "=A1+A2+A3");
        Assertions.assertEquals(2, spreadsheet.getCellValue("A3"));
        Assertions.assertEquals(4, spreadsheet.getCellValue("A4"));
    }

    @Test
    void shouldReturnZeroWhenCallNotExistingSell() {
        spreadsheet.setCellValue("A1", 1);
        spreadsheet.setCellValue("A2", "=A1+A3");
        Assertions.assertEquals(1, spreadsheet.getCellValue("A1"));
        Assertions.assertEquals(1, spreadsheet.getCellValue("A2"));
        Assertions.assertEquals(0, spreadsheet.getCellValue("A3"));
    }

    @Test
    void shouldFailValueValidationForString() {
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "=A1+A3+");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "= A1 + A3 ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "A1+A3+");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "=A1-A3");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "=B1+B3+");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            spreadsheet.setCellValue("A1", "dfbfdfsbd");
        });
    }
}