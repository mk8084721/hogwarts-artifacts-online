package com.mfk.hogwarts_artifacts_online.system;

import com.mfk.hogwarts_artifacts_online.artifact.Artifact;
import com.mfk.hogwarts_artifacts_online.artifact.ArtifactRepository;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUser;
import com.mfk.hogwarts_artifacts_online.hogwartsuser.HogwartsUserRepository;
import com.mfk.hogwarts_artifacts_online.wizard.Wizard;
import com.mfk.hogwarts_artifacts_online.wizard.WizardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//@Profile("!test")
public class DBDataInitializer implements CommandLineRunner {

    private final ArtifactRepository artifactRepository;
    private final WizardRepository wizardRepository;
    private final HogwartsUserRepository hogwartsUserRepository;

    @Override
    public void run(String... args) throws Exception {

        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore...");
        a1.setImageUrl("ImageUrl");
//        a1.setOwner(dumbledore);

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");
//        a2.setOwner(harry);

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick...");
        a3.setImageUrl("ImageUrl");
//        a3.setOwner(dumbledore);

        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts...");
        a4.setImageUrl("ImageUrl");
//        a4.setOwner(harry);

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies...");
        a5.setImageUrl("ImageUrl");
//        a5.setOwner(neville);

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones...");
        a6.setImageUrl("ImageUrl");
//        a6.setOwner(null);

        Wizard dumbledore = new Wizard();
        dumbledore.setName("Albus Dumbledore");
        dumbledore.addArtifact(a1);
        dumbledore.addArtifact(a3);

        Wizard harry = new Wizard();
        harry.setName("Harry Potter");
        harry.addArtifact(a2);
        harry.addArtifact(a4);


        Wizard neville = new Wizard();
        neville.setName("Neville Longbottom");
        neville.addArtifact(a5);

        HogwartsUser user1 = new HogwartsUser();
        user1.setUsername("john");
        user1.setPassword("User1123456");
        user1.setEnabled(true);
        user1.setRoles("admin user");

        HogwartsUser user2 = new HogwartsUser();
        user2.setUsername("user_eric");
        user2.setPassword("User2123456");
        user2.setEnabled(true);
        user2.setRoles("user");

        HogwartsUser user3 = new HogwartsUser();
        user3.setUsername("user_tom");
        user3.setPassword("User3123456");
        user3.setEnabled(false);
        user3.setRoles("user");


        wizardRepository.save(dumbledore);
        wizardRepository.save(harry);
        wizardRepository.save(neville);

        artifactRepository.save(a6);

        hogwartsUserRepository.save(user1);
        hogwartsUserRepository.save(user2);
        hogwartsUserRepository.save(user3);

    }
}
