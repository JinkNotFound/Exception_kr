import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main001 {

    static final Scanner scanner = new Scanner(System.in);
    static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Введите данные через пробел: фамилия имя отчество дата_рождения номер_телефона пол");
                String userData = scanner.nextLine();
                processUserData(userData);
                System.out.println("Данные успешно обработаны!");
                break;
            } catch (IllegalArgumentException | InputMismatchException e) {
                System.err.println("Ошибка: Неверный формат данных.");
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("Ошибка: Проблема при работе с файлом.");
                e.printStackTrace();
            }
        }
    }

    private static void processUserData(String userData) throws IOException {
        String[] userDataParts = userData.split(" ");

        if (userDataParts.length != 6) {
            throw new IllegalArgumentException("Неверное количество данных.");
        }

        String lastName = userDataParts[0];
        String firstName = userDataParts[1];
        String middleName = userDataParts[2];
        String birthDateStr = userDataParts[3];
        String phoneStr = userDataParts[4];
        char gender = userDataParts[5].charAt(0);

        validateData(birthDateStr, phoneStr, gender);

        LocalDate birthDate = LocalDate.parse(birthDateStr, dateFormat);
        long phoneNumber = Long.parseLong(phoneStr);

        String fileName = lastName + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(String.format("%s %s %s %s %d %c\n", lastName, firstName, middleName, birthDate, phoneNumber, gender));
        }
    }

    /**
     * ИСКЛЮЧЕНИЯ в дне рождении, номере телефона, пола
     * @param birthDateStr дата рождения
     * @param phoneStr номер телефона
     * @param gender пол
     */
    private static void validateData(String birthDateStr, String phoneStr, char gender) {
        if (!birthDateStr.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalArgumentException("Неверный формат даты рождения.");
        }

        if (!phoneStr.matches("\\d+")) {
            throw new IllegalArgumentException("Неверный формат номера телефона.");
        }

        if (gender != 'm' && gender != 'f') {
            throw new IllegalArgumentException("Неверный символ пола.");
        }
    }
}
