package fr.univlille.mastermiage.car.miagecartp3mapreduceakka.akka;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class MapperActor extends UntypedActor {

    private final AkkaService akkaService;

    public MapperActor(AkkaService akkaService) {
        this.akkaService = akkaService;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String line) {
            System.out.println("Mapper " + getSelf().path().name() + " traite : " + line);
            ActorRef[] reducers = akkaService.getReducers();

            String[] words = line.split("\\s+");

            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!word.isEmpty()) {
                    ActorRef reducer = partition(reducers, word);
                    reducer.tell(word, getSelf());
                }
            }
        }
    }

    private ActorRef partition(ActorRef[] reducers, String word) {
        int index = Math.abs(word.hashCode()) % reducers.length;
        return reducers[index];
    }
}
