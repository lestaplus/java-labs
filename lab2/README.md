# Лабораторна робота №2: Реалізація equals та робота з JSON

## Завдання 2.1:

1. Реалізувати метод equals для класу Person у якого є декілька полів (прізвище, ім’я, вік).
2. Реалізуйте наступний сценарій:
   - a. Створіть екземпляр Person
   - b. Конвертуйте його в JSON
   - c. Конвертуйте назад в об’єкт
   - d. Перевірте equals-ом початковий і одержаний об'єкти
3. Реалізувати unit tests для методу `equals` за допомогою бібліотеки `EqualsVerifier`.

## Інструкція з локального запуску:

Усі необхідні залежності знаходяться в директорії `lib/`.

1. Склонуйте репозиторій
```bash
git clone https://github.com/lestaplus/java-labs.git
```

2. Перейдіть до теки лабораторної роботи
```bash
cd lab2
```

3. Скомпілюйте вихідний код і тести у папку `out`
```bash
javac -cp "lib/*" -d out src/*.java
```

4. Запустіть головну програму
```bash
java -cp "out;lib/gson-2.14.0.jar" Main
```

5. Запустіть unit-тести через консольний runner
```bash
java -jar lib/junit-platform-console-standalone-6.1.3.jar execute -cp "out;lib/equalsverifier-nodep-4.5.2.jar;lib/gson-2.14.0.jar" --select-class PersonTest
```