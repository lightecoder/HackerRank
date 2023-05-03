package orkes;

import java.util.HashMap;
import java.util.Map;

public class SpreadSheet {
    private final Map<String, Object> spreadSheetMap;

    public SpreadSheet() {
        spreadSheetMap = new HashMap<>();
    }

    public void setCellValue(String cellId, Object value) {
        boolean isValueCorrect = validation(value);
        if (isValueCorrect) {
            spreadSheetMap.put(cellId, value);
        } else {
            throw new IllegalArgumentException("You are trying to assign incorrect value");
        }
    }

    private boolean validation(Object value) {
        String stringRegex = "^=A\\d+(\\+A\\d+)*$";
        if (value instanceof String) {
            return ((String) value).matches(stringRegex);
        } else return value instanceof Integer;
    }

    public int getCellValue(String cellId) {
        Object value = spreadSheetMap.get(cellId);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            return calculateStringValue((String) value);
        }
        return 0;
    }

    private int calculateStringValue(String expression) {
        expression = expression.substring(1);
        String[] cellArray = expression.split("\\+");
        int sum = 0;
        for (String cell : cellArray) {
            int cellValue = getCellValue(cell);
            sum += cellValue;
        }
        return sum;
    }

}
