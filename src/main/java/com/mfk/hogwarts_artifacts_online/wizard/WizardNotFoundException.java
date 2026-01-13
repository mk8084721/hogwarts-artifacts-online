package com.mfk.hogwarts_artifacts_online.wizard;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(Integer id){
        super("Could not find artifact with Id "+id+" :(");
    }
}
