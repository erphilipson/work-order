package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ethan on 7/28/17.
 */
public class Processor {

    Set<WorkOrder> workOrders = new HashSet<>();
    Map<Status, Set> map = new HashMap<>();
    public void processWorkOrders() throws IOException {
        try {
            readIt();
            moveIt();
            Thread.sleep(5000l);
            processWorkOrders();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void moveIt() {
        // move work orders in map from one state to another
        if (map.containsKey(Status.INITIAL)) {
            map.remove(Status.INITIAL);
            map.put(Status.ASSIGNED, workOrders);
            System.out.println(map);
        } else if (map.containsKey(Status.ASSIGNED)) {
            map.remove(Status.ASSIGNED);
            map.put(Status.IN_PROGRESS, workOrders);
            System.out.println(map);
        } else if (map.containsKey(Status.IN_PROGRESS)) {
            map.remove(Status.IN_PROGRESS);
            map.put(Status.DONE, workOrders);
            System.out.println(map);
        } else if (map.containsKey(Status.DONE)) {
            System.out.println("All done");
        } else {
            System.out.println("Nothing to move yet");
        }

    }

    private void readIt() throws IOException {
        // read the json files into WorkOrders and put in map
        ObjectMapper mapper = new ObjectMapper();

        File currentDirectory = new File(".");
        File files[] = currentDirectory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                WorkOrder wo = mapper.readValue(f, WorkOrder.class);
                // f is a reference to a json file
                WorkOrder workOrder = new WorkOrder();
                workOrder.setId(wo.id);
                workOrder.setDescription(wo.description);
                workOrder.setSenderName(wo.senderName);
                workOrder.setStatus(wo.status);

                f.delete();

                System.out.println(workOrder);
                workOrders.add(workOrder);
                map.put(workOrder.status, workOrders);

            }
        }
    }

    public static void main(String args[]) throws IOException {
        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}
