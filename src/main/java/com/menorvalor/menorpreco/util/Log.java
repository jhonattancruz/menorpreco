package com.menorvalor.menorpreco.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    static {
        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm:ss");

        PrintStream myStream = new PrintStream(System.out) {
            @Override
            public void println(String x) {
                super.println("[" + sdt.format(new Date()) + "] " + x);
            }
        };

        System.setOut(myStream);
    }

    public static void log(String mensagem,
                           Object... args) {
        try {
            if (args.length > 0) {
                String mensagemFinal = String.format(mensagem, args);

                logger.info(mensagemFinal);
            } else {
                logger.info(mensagem);
            }
        } catch (Exception ex) {
        }
    }
}
