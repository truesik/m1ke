package org.j2overhead.m1ke;

public class M1keRunner {
    public static void main(String[] args) {
        if ("init".equals(args[0])) {

            //Для того чтобы запустить m1ke нужно выполнить команду

        } else if ("integrate".equals(args[0])) {

            //К примеру у нас есть папка C:\project . Для того чтобы просканировать папку на файлы и уже сделанные изменения нужно выполнить:
            // m1ke integrate %FOLDER_NAME%
            //После этой команды m1ke открывает конкретную юзер ветку (branch) которая там сохранилась
            // (если она там не одна, то открывается та ветка, в которой произошли последние изменения).
            // Если в этой папке ничего никогда со стороны m1ke не запускалось то запускается первичная ветка master. Юзер должен об этом предупрежден сообщением.
        } else if ("-m".equals(args[0])) {

        } else if ("-create-branch".equals(args[0])) {

        } else if ("-create-branch".equals(args[0])) {

        } else if ("-get-branch".equals(args[0])) {

        }
    }

    void init(String[] args) {
    }
}
