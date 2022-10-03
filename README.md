![Workflow Status](https://github.com/Jacbes/TestShift/actions/workflows/main.yml/badge.svg)

Тестовое задание для ШИФТ от Беседина Якова.

Java version: 1.8

Maven: 3.8.4

Запуск:

```Bash
mvn clean package
java -jar target/TestShift-1.0-SNAPSHOT.jar -d files/out.txt files/in1.txt files/in2.txt files/in3.txt
```

Поддерживаемые ключи: (берутся только первые два агрумента программы)

```
-d, Слияние отсортированных файлов по убыванию
-a, Слияние отсортированных файлов по неубыванию (никак не влияет, стандартная настройка)
-s, Сортировка строк
-i, Сортировка чисел (никак не влияет, стандартная настройка)
```

Выходным файлом является первый .txt файл. Поддерживаются только файлы .txt формата.