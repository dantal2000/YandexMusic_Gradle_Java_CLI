/*
 * The MIT License
 *
 * Copyright 2017 dantal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dantal.launcher;

import java.io.File;
import java.io.IOException;

import dantal.locale.utils.StringTaker;
import dantal.utils.network.MusicLoader;
import dantal.utils.starter.ArgumentConverter;
import dantal.utils.starter.ArgumentStructureException;

/**
 * @author dantal
 */
public class MusicLoader_Launcher {

    public static void main(String[] args) {
        ArgumentConverter.convertLineArgs(args, () -> {
            System.out.print(StringTaker.getString("help_text"));
            /*System.out.println("Эта программа предназначена для скачивания музыкальных композиций с хранилища сервиса \"Яндекс.Музыка\".");
            System.out.println("Для работы необходим id композиции.");
            System.out.println("При успешном исполнении возвращает путь к сохраненному файлу.");
            System.out.println("Использование:");
            System.out.println("MusicLoaderLauncher [--[argName] [argValue]]");
            System.out.println("Аргументы:");
            System.out.println("help    -   Вывести эту справку и завершить выполнение программы");
            System.out.println("id      -   id композиции на сервере \"Яндекс.Музыка\" для скачивания");
            System.out.println("Пример использования:");
            System.out.println("MusicLoaderLauncher --id 31014492");*/
        }, arguments -> arguments.containsKey("id"), arguments -> {
            try {
                int id = Integer.parseInt(arguments.get("id").getValue());
                File file = MusicLoader.loadMusic(id);
                System.out.println(file.getAbsolutePath());
            } catch (ArgumentStructureException | IOException e) {
                e.printStackTrace();
            }
        });
    }
}
