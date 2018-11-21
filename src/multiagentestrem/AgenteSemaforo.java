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
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author julio
 */
public class AgenteSemaforo extends Agent {

    boolean tremProximo, ultimoTremProximo, aberto = true;
    JLabel semaforo, semaforoInvertido;
    String semaforoAbertoUrl = "semaforo_aberto.png",
            semaforoAbertoInvertidoUrl = "semaforo_aberto_invertido.png",
            semaforoFechadoUrl = "semaforo_fechado.png",
            semaforoFechadoInvertidoUrl = "semaforo_fechado_invertido.png";

    protected void setup() {
        Inicializar();
        //Recebendo Mensagem do Trem!
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {
                ACLMessage msg = myAgent.receive();

                if (msg != null) {
                    String content = msg.getContent();
                    String ontology = msg.getOntology();
                    String array[] = new String[2];

                    //Recebendo a mensagem do Trem.
                    if (ontology.matches("Distância")) {

                        //Verifica a distancia
                        array = content.split(":");

                        if ((Integer.parseInt(array[1]) > 5) && (Integer.parseInt(array[1]) < 15)) {
                            tremProximo = true; //Se o Trem está próximo
                        } else {
                            tremProximo = false; //Se o Trem está Longe
                        }

                    } else if (ontology.matches("Sinaleira")) {

                        ACLMessage reply = msg.createReply();

                        reply.setPerformative(ACLMessage.INFORM);
                        if (tremProximo) {
                            reply.setContent("Fechado");
                        } else {
                            reply.setContent("Aberto");
                        }

                        myAgent.send(reply);

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
                    if (ultimoTremProximo == false) {
                        msg.setContent("Fechado");
                        aberto = false;
                        ultimoTremProximo = true;
                        myAgent.send(msg);
                        AlternarSemaforo();
                        aberto = true;
                    }
                } else {
                    if (ultimoTremProximo == true) {
                        msg.setContent("Aberto");
                        ultimoTremProximo = false;
                        AlternarSemaforo();
                        myAgent.send(msg);
                    }
                }

            }
        });
    }

    private void Inicializar() {
        Object[] args = getArguments();
        TelaPrincipal telaPrincipal = (TelaPrincipal) args[0];
        semaforo = telaPrincipal.getSemaforo();
        semaforoInvertido = telaPrincipal.getSemaforoInvertido();
    }

    private void AlternarSemaforo() {
        String path = System.getProperty("user.dir") + File.separator +"src" + File.separator + "multiagentestrem" + File.separator + "imagens" + File.separator;
        
        if (aberto) {
            semaforo.setIcon(new ImageIcon(path + semaforoAbertoUrl));
            semaforoInvertido.setIcon(new ImageIcon(path + semaforoAbertoInvertidoUrl));
        } else {
            semaforo.setIcon(new ImageIcon(path + semaforoFechadoUrl));
            semaforoInvertido.setIcon(new ImageIcon(path + semaforoFechadoInvertidoUrl));
        }
        semaforo.repaint();
        semaforoInvertido.repaint();
    }

}
