package lesson1;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        File origFile = new File(inputName);
        FileReader fr = new FileReader(origFile);
        BufferedReader br = new BufferedReader(fr);
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        List<String> am = new ArrayList<>();
        List<String> pm = new ArrayList<>();
        for (String str : lines){
            if (str.contains("AM")) am.add(str);
            else if (str.contains("PM")) pm.add(str);
        }
        am = timeParser(am);
        pm = timeParser(pm);

        int[] sortedAm = numSort(am);
        int[] sortedPm = numSort(pm);

        String[] finalSortedAm = formattedTime(sortedAm);
        String[] finalSortedPm = formattedTime(sortedPm);

        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8)) {
            for (String stroke1 : finalSortedAm){
                writer.write(stroke1 + " AM\n");
            }
            for (String stroke2 : finalSortedPm){
                writer.write(stroke2 + " PM\n");
            }
        }


    }

    private static String[] formattedTime(int[] times){
        String[] m = new String[times.length];
        for (int i = 0; i < times.length; i++){
            String wholeTime;
            String hours;
            if (times[i] / 3600 < 10) hours = "0" + times[i] / 3600;
            else hours = times[i] / 3600 + "";
            String minutes;
            if ((times[i] - 3600 * Integer.parseInt(hours))/ 60 < 10)
                minutes = "0" + ((times[i] - 3600 * Integer.parseInt(hours)) / 60);
            else minutes = "" + ((times[i] - 3600 * Integer.parseInt(hours)) / 60);
            String seconds;
            if (times[i] - (Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60) < 10)
                seconds = "0" + (times[i] - (Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60));
            else seconds = "" + (times[i] - (Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60));
            wholeTime = hours + ":" + minutes + ":" + seconds;
            m[i] = wholeTime;
        }
        return m;
    }

    private static int[] numSort(List<String> time){
        int[] timeToInt = new int[time.size()];
        for (int i = 0; i < time.size(); i++){
            int ns = minutesToSeconds(time.get(i));
            timeToInt[i] = ns;
        }
        Sorts.mergeSort(timeToInt);
        return timeToInt;
    }

    private static List<String> timeParser(List<String> timeList){
        List<String> timeOnly = new ArrayList<>();
        for (String time : timeList){
            String[] times = time.split(" ");
            timeOnly.add(times[0]);
        }
        return timeOnly;
    }

    private static int minutesToSeconds(String time){
        String[] timeParts = time.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        if (hours == 12) hours = 0;
        return hours*3600 + Integer.parseInt(timeParts[1])*60 + Integer.parseInt(timeParts[2]);
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        File file = new File(inputName);
        Map<String, List<String>> address = new HashMap<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(inputName),
                StandardCharsets.UTF_8));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        if (lines.size() < 1000000) {
            for (String str : lines) {
                String[] addressName = str.split(" - ");
                if (address.containsKey(addressName[1])) {
                    address.get(addressName[1]).add(addressName[0]);
                } else {
                    List<String> value = new ArrayList<>();
                    value.add(addressName[0]);
                    address.put(addressName[1], value);
                }
            }
            for (Map.Entry<String, List<String>> entry : address.entrySet()) {
                if (entry.getValue().size() > 1) {
                    String[] h = entry.getValue().toArray(new String[0]);
                    Sorts.insertionSort(h);
                    address.put(entry.getKey(), Arrays.asList(h));
                }
            }
            File sortedFile = new File(outputName);
            Map<String, List<String>> addressSort = new TreeMap(address);

            try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputName), StandardCharsets.UTF_8))
            {
                for (Map.Entry<String, List<String>> entry : addressSort.entrySet()) {
                    String key = entry.getKey();
                    List<String> names = entry.getValue();
                    writer.write(key + " - ");
//                    System.out.println(key + " - ");
                    for (int i = 0; i < names.size(); i++) {
                        writer.write(names.get(i));
//                        System.out.println(names.get(i));
                        if (i != names.size() - 1) {
                            writer.write(", ");
//
                        }                    }
                    writer.write("\n");
                }
            }
        }
        else {
            System.out.println("Количество людей больше допустимого!");
            return;
        }
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {

    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {

    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {

    }
}
