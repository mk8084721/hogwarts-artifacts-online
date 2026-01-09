package com.mfk.hogwarts_artifacts_online.artifact;

public class ArtifactNotFoundException extends RuntimeException{
    ArtifactNotFoundException(String id){
        super("Could not find artifact with id "+id+" :(");
    }
}
