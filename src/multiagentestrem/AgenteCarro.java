/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagentestrem;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.CyclicBehaviour;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author julio
 */
public class AgenteCarro extends Agent {

    private int velocidade = 50;
    private int direcao = 1;
    private JLabel estrada1, estrada2, carro, trilho;
    private int estradaAtual = 1;//1 para estrada 1, 2 para estrada 2, 3 para trilho
    private boolean semaforoAberto = true;

    protected void setup() {
        InicializarCarro();

//Enviando Mensagem pro Semaforo
        //addBehaviour(new CyclicBehaviour(this) {
//        addBehaviour(new OneShotBehaviour(this) {
//
//            public void action() {
//                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                msg.addReceiver(new AID("AgenteSemaforo", AID.ISLOCALNAME));
//                msg.setLanguage("Português");
//                msg.setOntology("Sinaleira");
//                msg.setContent("Aberto");
//                myAgent.send(msg);
//                block(1000);
//
//            }
//        });
        //Recebendo Resposta do Semaforo
        addBehaviour(new CyclicBehaviour(this) {

            public void action() {

                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    String content = msg.getContent();

                    if (content.matches("Fechado")) {
                        semaforoAberto = false;
                    } else if (content.matches("Aberto")) {
                        semaforoAberto = true;
                    }

                } else {
                    block();
                }

            }
        });

        //andar
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {

                if (direcao == 1 && (carro.getBounds().y <= 10 && carro.getBounds().y >= -40)) {
                    EnviarMensagem();
                }else if(direcao == 2 && (carro.getBounds().y >= 245 && carro.getBounds().y <= 275)){
                    EnviarMensagem();
                }

                if (((direcao == 1 && carro.getBounds().y > 10) || (direcao == 2 && carro.getBounds().y < 265))/*posições onde os carros não devem parar*/
                        || estradaAtual == 3 /*se estiver em cima do trilho não para*/
                        || (estradaAtual == 2 && direcao == 1) /*se estiver indo pra cima e estiver na estrada de cima não há porque parar (já passou do semáforo)*/
                        || (estradaAtual == 1 && direcao == 2))/*mesma lógica que a de cima, mas ao contrário*/ {
                    RealizarMovimentacao();
                }else if(semaforoAberto){
                    RealizarMovimentacao();
                }

                block(30);
            }

            private void RealizarMovimentacao() {
                Rectangle bounds = carro.getBounds();
                if (direcao == 1) {

                    if (estradaAtual != 3) {
                        if (carro.getBounds().y <= -40) {
                            if (estradaAtual == 1) {
                                estrada1.remove(carro);
                                carro.setBounds(693, 95, 20, 41);
                                trilho.add(carro);
                                estradaAtual = 3;
                            } else {
                                estrada2.remove(carro);
                                carro.setBounds(110, 275, 20, 41);
                                estrada1.add(carro);
                                estradaAtual = 1;
                            }
                        } else {
                            carro.setBounds(bounds.x, bounds.y - 10, bounds.width, bounds.height);
                        }
                    } else {
                        if (carro.getBounds().y <= -30) {

                            trilho.remove(carro);
                            trilho.revalidate();
                            trilho.repaint();
                            carro.setBounds(110, 300, 20, 41);
                            estrada2.add(carro);
                            estradaAtual = 2;
                        } else {
                            carro.setBounds(bounds.x, bounds.y - 10, bounds.width, bounds.height);
                        }
                    }
                } else {
                    if (estradaAtual != 3) {
                        if (carro.getBounds().y >= 315) {
                            if (estradaAtual == 2) {
                                estrada2.remove(carro);
                                carro.setBounds(615, -30, 20, 41);
                                trilho.add(carro);
                                estradaAtual = 3;
                            } else {
                                estrada1.remove(carro);
                                carro.setBounds(35, 0, 20, 41);
                                estrada2.add(carro);
                                estradaAtual = 2;
                            }

                        } else {
                            carro.setBounds(bounds.x, bounds.y + 10, bounds.width, bounds.height);
                        }
                    } else {
                        if (carro.getBounds().y >= 95) {
                            trilho.remove(carro);
                            trilho.revalidate();
                            trilho.repaint();
                            carro.setBounds(35, 0, 20, 41);
                            estrada1.add(carro);
                            estradaAtual = 1;
                        } else {
                            carro.setBounds(bounds.x, bounds.y + 10, bounds.width, bounds.height);
                        }
                    }
                }
            }
        });
    }

    /*
        Inicializa o carro e adiciona na tela
     */
    private void InicializarCarro() {
        carro = new JLabel();
        Object[] args = getArguments();
        //a instância da telaé passada como primeiro argumento
        TelaPrincipal telaPrincipal = (TelaPrincipal) args[0];

        estrada1 = telaPrincipal.getEstrada1();
        estrada2 = telaPrincipal.getEstrada2();
        trilho = telaPrincipal.getTrilho();

        //o parâmtro [1] é a direção (1 pra cima e 2 pra baixo)
        if (args[1] != null) {
            direcao = (int) args[1];
            estradaAtual = direcao;
        }
        int x = 110, y = 275;

        if (direcao == 2) {
            x = 35;
            y = 0;
        }

        carro.setBounds(x, y, 20, 41);

        String path = System.getProperty("user.dir");
        ImageIcon image = new ImageIcon(path + "\\src\\multiagentestrem\\imagens\\carro" + direcao + ".png");
        carro.setIcon(image);

        if (direcao == 1) {
            estrada1.add(carro);
            estrada1.revalidate();
            estrada1.repaint();
        } else {
            estrada2.add(carro);
            estrada2.revalidate();
            estrada2.repaint();
        }
        trilho.revalidate();
        trilho.repaint();

        System.out.println("Carro inicializado");
    }

    private void EnviarMensagem() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID("AgenteSemaforo", AID.ISLOCALNAME));
        msg.setLanguage("Português");
        msg.setOntology("Sinaleira");
        msg.setContent("Aberto");
        this.send(msg);
    }

}
