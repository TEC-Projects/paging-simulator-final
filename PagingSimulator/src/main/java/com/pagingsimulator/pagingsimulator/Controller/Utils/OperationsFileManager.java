package com.pagingsimulator.pagingsimulator.Controller.Utils;

import com.pagingsimulator.pagingsimulator.Model.Operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OperationsFileManager {
    public ArrayList<Operation> generateOperations(long randomSeed, int numberOfOperations, int numberOfProcesses){
        Random random = new Random(randomSeed);
        ArrayList<Operation> operations = new ArrayList<>();
        List<ProcessTemp> processes = new ArrayList<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            processes.add(new ProcessTemp(i));
        }
        List<Integer> killedProcesses = new ArrayList<>();
        int ptrCount = 0;
        List<Integer> ptrs = new ArrayList<>();
        List<Integer> deletedPtrs = new ArrayList<>();

        for (int i = 0; i < numberOfOperations; i++) {
            if(numberOfOperations - i == processes.size()){
                ProcessTemp pid = processes.remove(random.nextInt(processes.size()));
                operations.add(new Operation("kill", pid.getPid()));
                killedProcesses.add(pid.getPid());
            }else if (ptrs.isEmpty()){
                int newSize = random.nextInt(500, 12000);
                ProcessTemp processTemp = processes.get(random.nextInt(processes.size()));
                operations.add(new Operation("new", processTemp.getPid() + "," + newSize));
                ptrs.add(ptrCount);
                processTemp.addPtr(ptrCount++);
            } else {
                double NEW_PTR = 37.5;
                double USE_PTR = 70;
                double DELETE_PTR = 100 - (numberOfProcesses*1.0/numberOfOperations)*50;

                int selection = random.nextInt(0, 100);

                if (selection <= NEW_PTR){
                    int newSize = random.nextInt(500, 12000);
                    ProcessTemp processTemp = processes.get(random.nextInt(processes.size()));
                    operations.add(new Operation("new", processTemp.getPid() + "," + newSize));
                    ptrs.add(ptrCount);
                    processTemp.addPtr(ptrCount++);
                }else if (selection <= USE_PTR || processes.size() == 1){
                    operations.add(new Operation("use", ptrs.get(random.nextInt(ptrs.size()))));
                }else if (selection <= DELETE_PTR){
                    int ptr = ptrs.remove(random.nextInt(ptrs.size()));
                    operations.add(new Operation("delete", ptr));
                    deletedPtrs.add(ptr);
                }else{
                    ProcessTemp pid = processes.remove(random.nextInt(processes.size()));
                    operations.add(new Operation("kill", pid.getPid()));
                    killedProcesses.add(pid.getPid());
                    for(Integer ptr : pid.getPtrs()){
                        if(ptrs.contains(ptr)){
                            int removedPtr = ptrs.remove(ptrs.indexOf(ptr));
                            deletedPtrs.add(removedPtr);
                        }
                    }
                }
            }
        }
        return operations;
    }

    public ArrayList<Operation> retrieveOperationsFromFile(File operationsFile) throws IOException {
        // CREATE INPUT FILE TO ACCESS THE GIVEN FILE
        FileInputStream fis = new FileInputStream(operationsFile);
        // READ THE WHOLE ARCHIVE AND STORE ITS CONTENTS
        String operations = new String(fis.readAllBytes());
        // SPLIT THE CONTENTS FILE BY BREAK LINE TO GET EACH INDIVIDUAL OPERATION STRING
        String[] splitOperations = operations.split("\n");

        // INITIALIZE OPERATIONS LIST
        ArrayList<Operation> parsedOperations = new ArrayList<>();

        // ITERATE OVER THE OPERATIONS TO PARSE THEM INTO OBJECTS
        for(String op: splitOperations){
            // ERASE THE ")" CHARACTER AS IT DOESN'T GIVE US RELEVANT INFORMATION
            op = op.replace(")", "");
            // SPLIT THE OPERATION NAME FROM ITS PARAMETERS
            String[] splitOp = op.split("[(]");
            parsedOperations.add(new Operation(splitOp[0], splitOp[1]));
        }

        return parsedOperations;
    }

    public String convertOperationsToFileString(ArrayList<Operation> operations){
        StringBuilder res = new StringBuilder();
        for (Operation op : operations){
            if(op.getParameters().size() > 1){
                res.append(op.getName()).append("(").append(op.getParameters().get(0)).append(",").append(op.getParameters().get(1)).append(")\n");
            }else{
                res.append(op.getName()).append("(").append(op.getParameters().get(0)).append(")\n");
            }
        }
        return res.toString();
    }
}
