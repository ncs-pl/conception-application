/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.main;

import fr.nc0.cda.controleur.ControleurNim;
import fr.nc0.cda.controleur.ControleurPuissance4;
import fr.nc0.cda.controleur.ControleurTemplate;
import fr.nc0.cda.modele.Jeux;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.modele.joueur.StrategieHumainNim;
import fr.nc0.cda.modele.joueur.StrategieHumainPuissance4;
import fr.nc0.cda.vue.Ihm;

public class Main {
  /**
   * Initialise un joueur humain
   *
   * @param num le numéro du joueur
   * @param jeu le jeu auquel il compte jouer
   * @param ihm l'IHM
   * @return le joueur initialisé
   */
  private static Joueur initialiserJoueurHumain(int num, Jeux jeu, Ihm ihm) {
    while (true) {
      String nom = ihm.demanderString("Joueur " + num + ", quel est votre nom ?");
      if (nom.equalsIgnoreCase("ai")) {
        ihm.afficherErreur("Vous ne pouvez pas vous appeler ainsi.");
        continue;
      }

      Joueur joueur = new Joueur(nom);
      switch (jeu) {
        case NIM -> joueur.setStrategie(new StrategieHumainNim());
        case PUISSANCE4 -> joueur.setStrategie(new StrategieHumainPuissance4());
      }

      return joueur;
    }
  }

  public static void main(String[] args) {
    Ihm ihm = new Ihm();

    ihm.afficherMessage("Bienvenue dans notre application de jeux à deux !");

    Jeux jeu = null;
    while (jeu == null) {
      String choix = ihm.demanderString("À quel jeu voulez-vous jouer  ? (\"Nim\"/\"Puissance4\")");
      switch (choix.toLowerCase()) {
        case "nim", "n", "jeu de nim":
          {
            jeu = Jeux.NIM;
            break;
          }
        case "puissance4", "p4", "p", "puissance 4":
          {
            jeu = Jeux.PUISSANCE4;
            break;
          }
        default:
          ihm.afficherErreur("Ce jeu n'existe pas dans notre système.");
      }
    }

    boolean ai = ihm.demanderBoolean("Voulez-vous jouer contre une AI ?");
    Joueur joueur1 = initialiserJoueurHumain(1, jeu, ihm);
    Joueur joueur2 = ai ? new Joueur("AI") : initialiserJoueurHumain(2, jeu, ihm);

    ControleurTemplate controleur =
        switch (jeu) {
          case NIM -> new ControleurNim(ihm, joueur1, joueur2);
          case PUISSANCE4 -> new ControleurPuissance4(ihm, joueur1, joueur2);
        };
    controleur.jouer();

    ihm.afficherMessage("Merci d'avoir joué !");
  }
}
