import org.algandsd.GnomeSort;
import org.algandsd.SortState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GnomeSortTest {
    @Test
    @DisplayName("ПРОВЕРКА 3 2 1 0 ДЛЯ ПРОСТОЙ СОРТИРОВКИ")
    void testSort() {
        int[] array = {3, 2, 1, 0};
        List<SortState> sortStateList = GnomeSort.sort(array);

        int[][] resArrays = {{3, 2, 1, 0}, {2, 3, 1, 0}, {2, 1, 3, 0}, {1, 2, 3, 0}, {1, 2, 3, 0}, {1, 2, 0, 3}, {1, 0, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}, {0, 1, 2, 3}};

        for (int i = 0; i < resArrays.length; i++) {
            if (!Arrays.equals(sortStateList.get(i).getArray(), resArrays[i])) {
                assertArrayEquals(sortStateList.get(i).getArray(), resArrays[i], "МАССИВ НЕ ОТСОРТИРОВАН");
            }
        }

        assertArrayEquals(sortStateList.getFirst().getArray(), resArrays[0], "МАССИВ ОТСОРТИРОВАН");
    }

    @Test
    @DisplayName("ПРОВЕРКА 3 2 1 0 ДЛЯ СОРТИРОВКИ С ТЕЛЕПОРТОМ")
    void testFastSort() {
        int[] array = {3, 2, 1, 0};
        List<SortState> sortStateList = GnomeSort.fastSort(array);

        int[][] resArrays = {{3, 2, 1, 0}, {2, 3, 1, 0}, {2, 1, 3, 0}, {1, 2, 3, 0}, {1, 2, 0, 3}, {1, 0, 2, 3}, {0, 1, 2, 3}};

        for (int i = 0; i < resArrays.length; i++) {
            if (!Arrays.equals(sortStateList.get(i).getArray(), resArrays[i])) {
                assertArrayEquals(sortStateList.get(i).getArray(), resArrays[i], "БЫСТРЫЙ МАССИВ НЕ ОТСОРТИРОВАН");
            }
        }

        assertArrayEquals(sortStateList.getFirst().getArray(), resArrays[0], "МАССИВ ОТСОРТИРОВАН");
    }
}
