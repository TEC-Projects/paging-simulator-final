package com.pagingsimulator.pagingsimulator.UI.Utils;

import java.io.File;

public class ValidatorUtil {

    public ValidatorUtil(){};

    public void randomSeedValidator(String randomSeed) throws Exception {
        try{
            Long.parseLong(randomSeed);
        }catch (Exception e){
            throw new Exception("Invalid random seed.");
        }
    }

    public void fileLoadValidator(File simulationFileRoute) throws Exception {
        if(simulationFileRoute == null){
            throw new Exception("A simulation file must be selected.");
        }
    }
}
