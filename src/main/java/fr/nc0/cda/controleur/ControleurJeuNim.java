/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package main.java.fr.nc0.cda.controleur;

import main.java.fr.nc0.cda.modele.Joueur;
import main.java.fr.nc0.cda.modele.Nim;
import main.java.fr.nc0.cda.vue.Ihm;

import java.util.ArrayList;

public class ControleurJeuNim {

    private Ihm ihm;
    private ArrayList<Joueur> lesJoueurs;

    public ControleurJeuNim(Ihm ihm){
        this.ihm = ihm;
        lesJoueurs = new ArrayList<Joueur>(2);
    }

    public void jouer(){
        Nim nim = new Nim(ihm.selectNbrTas());
        ajouterJoueur();
        Joueur currentPlayer = lesJoueurs.get(0);

        while(nim.getEtatPartie() == Nim.EtatPartie.EnCours){
            ihm.afficherEtatPartie(nim.getTas());
            int[] choix = ihm.selectAlumette(currentPlayer.getNom());

            if(nim.getTas().size() > choix[0] && choix[0] >= 0){
                if(choix[1] > 0 && choix[1] <= nim.getTas().get(choix[0]) && choix[1] <= 3){
                    nim.supprAllumettes(choix);
                    nim.checkEtatPartie();
                    if(nim.getEtatPartie() == Nim.EtatPartie.EnCours){
                        currentPlayer = nextPlayer(currentPlayer);
                    }
                } else {
                    ihm.message("Valeur des allumettes incorrect");
                }
            } else {
                ihm.message("Valeur du tas incorrect");
            }
        }
        currentPlayer.ajouterPartieGagnee();
        Joueur gagnant = currentPlayer;
        Joueur perdant = nextPlayer(currentPlayer);
        if(ihm.finPartie(gagnant.getNom(), perdant.getNom(), gagnant.getNbrPartieGagnee(), perdant.getNbrPartieGagnee())){
            jouer();
        } else {
            boolean exaequo = false;
            if(lesJoueurs.get(0).getNbrPartieGagnee() > lesJoueurs.get(1).getNbrPartieGagnee()){
                gagnant = lesJoueurs.get(0);
                perdant = lesJoueurs.get(1);
            } else if(lesJoueurs.get(0).getNbrPartieGagnee() < lesJoueurs.get(1).getNbrPartieGagnee()) {
                gagnant = lesJoueurs.get(1);
                perdant = lesJoueurs.get(0);
            } else {
                exaequo = true;
            }
            ihm.afficheGagnant(gagnant.getNom(), perdant.getNom(), gagnant.getNbrPartieGagnee(), perdant.getNbrPartieGagnee(), exaequo);
        }
    }

    private Joueur nextPlayer(Joueur currentPlayer){
        if(currentPlayer == lesJoueurs.get(0)){
            return lesJoueurs.get(1);
        } else {
            return lesJoueurs.get(0);
        }
    }

    private void ajouterJoueur(){
        if(lesJoueurs.size() == 0){
            lesJoueurs.add(new Joueur(ihm.selectNomJoueur(1)));
            lesJoueurs.add(new Joueur(ihm.selectNomJoueur(2)));
        }
    }
}
