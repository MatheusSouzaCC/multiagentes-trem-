/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;

/**
 *
 * @author julio
 */
public class AgenteCarro extends Agent {

    protected void setup() {
        System.out.println("Carro inicializado");
//Recebendo Mensagem do Semaforo
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    ACLMessage reply = msg.createReply();
                    String content = msg.getContent();

                    //Recebendo a mensagem do Semaforo.
                    //Recebendo que o Semaforo está fechado, e que vai aguardar a liberação
                    if (content.equalsIgnoreCase("Fechado")) {
                        System.out.println("O agente " + msg.getSender().getName() + " avisou que o Semaforo está Fechado");
                        System.out.println("Vou aguardar a liberação!");

                    //Recebendo que o Semaforo está Aberto, e que vai Continuar o trajeto
                    } else if (content.equalsIgnoreCase("Aberto")) {
                        System.out.println("O agente " + msg.getSender().getName() + " avisou que o Semaforo está Aberto");
                        System.out.println("Vou continuar meu Trajeto!");
                    }
                } else {
                    block();
                }
            }
        });
    }

}
