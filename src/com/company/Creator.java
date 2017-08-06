package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Ethan on 7/28/17.
 */
public class Creator {
    public void createWorkOrders() throws IOException {
        // read input, create work orders and write as json files
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        WorkOrder workOrder = new WorkOrder();

        System.out.println("Please enter a description for the work order.");
        workOrder.description = scanner.nextLine();

        System.out.println("Who is sending this work order?");
        workOrder.senderName = scanner.nextLine();

        workOrder.id = random.nextInt(1000000);
        workOrder.status = Status.INITIAL;

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(workOrder);
        File jsonFile = new File(workOrder.getId() + ".json");
        FileWriter fileWriter = new FileWriter(jsonFile);
        fileWriter.write(json);
        fileWriter.close();
    }

    public static void main(String args[]) throws IOException {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
