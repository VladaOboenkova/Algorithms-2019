package lesson6;

import kotlin.NotImplementedError;

import java.util.*;

import static java.lang.Math.max;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     *
     * Оценка сложности: O(nm);
     * Оценка памяти: O(nm);
     * где n,m - количество символов в строках
     */
    public static String longestCommonSubSequence(String first, String second) {
        int n = first.length();
        int m = second.length();
        int[][] resultTable = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                resultTable[i][j] = resultTable[i - 1][j - 1] + 1;
        else
                resultTable[i][j] = max(resultTable[i - 1][j], resultTable[i][j - 1]);
            }
        }
        StringBuilder result = new StringBuilder();
        int i = n;
        int j = m;
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                result.append(first.charAt(i - 1));
                i--;
                j--;
            }
            else if (resultTable[ i - 1][j] == resultTable[i][j]) i--;
            else j--;
        }
        return result.reverse().toString();
    }
    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     *
     * Оценка сложности: О(n^2);
     * Оценка памяти: O(n);
     * где n - количество элементов в списке
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        if (list.isEmpty()) return result;
        int[] memo = new int[list.size()];
        int[] pElements = new int[list.size()];
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            memo[i] = 1;
            pElements[i] = -1;
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && memo[j] + 1 > memo[i]) {
                        memo[i] = memo[j] + 1;
                        pElements[i] = j;
                    }
            }
            if (memo[i] > memo[max]) max = i;
        }
        while (max != -1) {
            result.add(list.get(max));
            max = pElements[max];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5

}
