package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class AkkaService {
    private ActorSystem actorSystem;
    private ActorRef[] mappers;
    private ActorRef[] reducers;

    public AkkaService() {
    }


    public ActorSystem getActorSystem() {
        return actorSystem;
    }

    public ActorRef[] getMappers() {
        return mappers;
    }

    public ActorRef[] getReducers() {
        return reducers;
    }

    public void init() {
        if (this.actorSystem == null) {
            this.actorSystem = ActorSystem.create("MapReduceSystem");
            this.mappers = new ActorRef[3];
            for (int i = 0; i < 3; i++) {
                mappers[i] = actorSystem.actorOf(Props.create(MapperActor.class), "mapper" + i);
            }

            this.reducers = new ActorRef[2];
            for (int i = 0; i < 2; i++) {
                reducers[i] = actorSystem.actorOf(Props.create(ReduceActor.class), "reducer" + i);
            }

            System.out.println("Système Akka initialisé avec " +
                    this.mappers.length + " Mappers et " +
                    this.reducers.length + " Reducers.");
        }
    }

    public void processFile(File file) {
        if (actorSystem == null || mappers == null) {
            System.out.println("Système Akka non initialisé !!!");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int index = 0;

            while (line != null) {
                mappers[index % mappers.length].tell(line, ActorRef.noSender());
                line = reader.readLine();
                index++;
            }
            System.out.println("Fin de l'envoi aux mappers :)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
