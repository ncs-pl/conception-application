/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.main;

import fr.nc0.cda.controleur.ControleurJeuNim;
import fr.nc0.cda.controleur.ControleurPuissance4;
import fr.nc0.cda.controleur.ControleurTemplate;
import fr.nc0.cda.vue.Ihm;

public class Main {
  public static void main(String[] args) {
    Ihm ihm = new Ihm();

    ihm.afficherMessage("Bienvenue dans notre application de jeux à deux !");

    ControleurTemplate jeu = null;
    while (jeu == null) {
      String jeuChoix =
          ihm.demanderString("Quel jeu voulez-vous lancer ? (Nim/Puissance4)").toLowerCase();

      jeu =
          switch (jeuChoix) {
            case "nim", "n" -> new ControleurJeuNim(ihm);
            case "puissance4", "p4", "p" -> new ControleurPuissance4(ihm);
            default -> null;
          };

      if (jeu == null) {
        ihm.afficherErreur("Jeu inconnu, veuillez réessayer.");
      }
    }

    jeu.jouer();
    ihm.afficherMessage("Merci d'avoir joué !");
  }
}