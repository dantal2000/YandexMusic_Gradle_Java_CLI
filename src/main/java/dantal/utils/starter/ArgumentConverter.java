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
package dantal.utils.starter;

import dantal.locale.utils.StringTaker;

import java.util.HashMap;
import java.util.Set;

/**
 * @author dantal
 */
public class ArgumentConverter {
    public static void convertLineArgs(String[] args, Runnable printHelp, Checker checker, Starter starter) {
        HashMap<String, Argument<String>> arguments = new HashMap<>();
        Argument<String> last = null;

        for (String arg : args) {
            if (arg.startsWith("--")) {
                if (last != null) {
                    last.setSterile(true);
                }
                String name = arg.substring(2).toLowerCase();
                last = new Argument<>(name, false);
                arguments.put(name, last);
            } else {
                if (last != null) {
                    try {
                        if (last.isSterile()) {
                            last.setSterile(false);
                        }
                        last.setValue(arg);
                        last = null;
                    } catch (ArgumentStructureException e) {
                        e.printStackTrace();
                    }
                } else {
                    //System.out.println("Нарушен порядок построения аргументов");
                    System.out.print(StringTaker.getString("ArgumentOrderException"));
                    System.exit(1);
                }
            }
        }

        Set<String> names = arguments.keySet();
        if (names.contains("help") || names.isEmpty() || !checker.check(arguments)) {
            printHelp.run();
            System.exit(0);
        }

        starter.start(arguments);
    }
}
