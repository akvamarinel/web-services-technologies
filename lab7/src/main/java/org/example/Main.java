package org.example;


import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("INFO message");
        log.debug("DEBUG message");
        log.error("ERROR message");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1. Зарегистрировать новый сервис\n2. Найти существующий сервис");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    log.info("Register service");
                    UDDIServiceManager.registerNewService();
                    break;
                case 2:
                    log.info("Search service");
                    UDDIServiceManager.searchAndInvokeExistingService();
                    break;
                default:
                    log.error("Please, choice correct option");
            }
        } catch (Exception e) {
            log.error("Unexpected exception: {}", e.getMessage(), e);
        }
    }
}
