package com.mfk.hogwarts_artifacts_online.artifact;

import com.mfk.hogwarts_artifacts_online.wizard.Wizard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Artifact implements Serializable {
    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wizard owner;

    public Artifact(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}
