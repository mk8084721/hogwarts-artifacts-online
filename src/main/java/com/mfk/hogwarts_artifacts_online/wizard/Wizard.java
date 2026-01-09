package com.mfk.hogwarts_artifacts_online.wizard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Wizard implements Serializable {
    @Id
    private Integer id;
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Artifact> artifacts = new ArrayList<>();

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artifacts.add(artifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.getArtifacts().size();
    }
}
