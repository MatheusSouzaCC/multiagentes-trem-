/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.AID;

/**
 *
 * @author julio
 */
public class AgenteSemaforo extends Agent {

    boolean tremProximo;

    protected void setup() {

        //Recebendo Mensagem do Trem!
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    String content = msg.getContent();
                    String ontology = msg.getOntology();
                    String array[] = new String[2];

                    //Recebendo a mensagem do Trem.
                    if (ontology.matches("Distância")) {

                        //Verifica a distancia
                        
                        array = content.split(":");
                        
                        System.out.println(array[1]);
                        if ( ( Integer.parseInt(array[1]) > 5 ) || ( Integer.parseInt(array[1]) < 15) ) {
                            tremProximo = true; //Se o Trem está próximo
                        } else { 
                            tremProximo = false; //Se o Trem está Longe
                        }                    
                    }
                } else {
                    block();
                }
            }
        });

        // Enviando Mensagem pro AgenteCarro que o Sinal Está fechado!
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("AgenteCarro", AID.ISLOCALNAME));
                msg.setLanguage("Português");
                msg.setOntology("Estrada");

                if (tremProximo) {
                    msg.setContent("Fechado");
                } else {
                    msg.setContent("Aberto");
                }

                myAgent.send(msg);
            }
        });
    }

}
