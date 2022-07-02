package hackerrank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BubbleAllZeroesTest {

    private final BubbleAllZeroes sut = new BubbleAllZeroes();

    @Test
    void shouldBubbleAllZeroesRight() {
        //given
        int[] source = new int[]{1, 3, 0, 6, 0, 0, -1, 0};
        int[] arrExpected = new int[]{1, 3, 6, -1, 0, 0, 0, 0};

        //when
        int[] arrActual = sut.bubbleAllZeroesRight(source);

        //then
        assertArrayEquals(arrActual, arrExpected);
    }
}