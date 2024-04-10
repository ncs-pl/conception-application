/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.EtatPartie;
import fr.nc0.cda.modele.Joueur;
import fr.nc0.cda.modele.nim.Nim;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;

/** Contrôleur du jeu de Nim. */
public class ControleurJeuNim extends ControleurTemplate {
  /** Interface homme-machine. */
  private final Ihm ihm;

  /** Liste des joueurs de la partie. */
  private final ArrayList<Joueur> lesJoueurs;

  /** Le nombre de tas pour les parties. */
  private final int nombreTas;

  /**
   * Créer un contrôleur de jeu de Nim.
   *
   * @param ihm l'interface homme-machine.
   */
  public ControleurJeuNim(Ihm ihm) {
    this.ihm = ihm;

    nombreTas = demanderNombreTas();

    lesJoueurs = new ArrayList<>(2);
    lesJoueurs.add(new Joueur(demanderNomJoueur(1)));
    lesJoueurs.add(new Joueur(demanderNomJoueur(2)));
  }

  /**
   * Demande le nom d'un joueur
   *
   * @param numJoueur le numéro du joueur demandé
   * @return le nom entré
   */
  private String demanderNomJoueur(int numJoueur) {
    return ihm.demanderString("Saisissez le nom du joueur " + numJoueur);
  }

  /**
   * Demande le nombre de tas pour la partie.
   *
   * @return le nombre de tas pour la partie.
   */
  private int demanderNombreTas() {
    while (true) {
      int tas = ihm.demanderInt("Saisissez le nombre de tas pour la partie");
      if (tas > 0) return tas;

      ihm.afficherErreur("Le nombre de tas ne peut pas être négatif ou nul");
    }
  }

  /**
   * Demande la contrainte pour la sélection de tas à retirer par coup.
   *
   * @return le nombre de tas à retirer par coup.
   */
  private int demanderContrainte() {
    while (true) {
      int contrainte =
          ihm.demanderInt(
              "Saisissez le nombre maximal d'allumettes à retirer par "
                  + "coup, ou 0 pour ne pas mettre de contrainte");
      if (contrainte >= 0) return contrainte;

      ihm.afficherErreur("La contrainte ne peut pas être négative");
    }
  }

  /**
   * Demande un choix de tas et d'allumettes à un joueur.
   *
   * @param nim le jeu de Nim en cours.
   * @param joueur le joueur qui doit choisir.
   * @return un tableau contenant le tas et le nombre d'allumettes, sous la forme [tas, allumettes].
   */
  private int[] demanderChoix(Nim nim, Joueur joueur) {
    while (true) {
      int[] choix = ihm.demanderDeuxInt(joueur.getNom() + ", à vous de jouer un coup");
      int tas = choix[0];
      int allumettes = choix[1];
      int contrainte = nim.getContrainte();

      if (tas < 1 || tas > nim.getListeTas().taille)
        ihm.afficherErreur("Le tas choisi n'existe pas");
      else if (nim.getListeTas().get(tas).getAllumettes() < 1)
        ihm.afficherErreur("Le tas choisi est vide");
      else if (allumettes < 1) ihm.afficherErreur("Nombre d'allumettes invalide");
      else if (allumettes > nim.getListeTas().get(tas).getAllumettes())
        ihm.afficherErreur("Nombre d'allumettes supérieur au nombre d'allumettes dans le tas");
      else if (contrainte != 0 && allumettes > contrainte)
        ihm.afficherErreur("Nombre d'allumettes supérieur à la contrainte");
      else return choix;
    }
  }

  /**
   * Demande si les joueurs veulent rejouer.
   *
   * @return true si les joueurs veulent rejouer, false sinon.
   */
  private boolean demanderRejouer() {
    return ihm.demanderBoolean("Voulez-vous rejouer ?");
  }

  /** Jouer une partie du jeu de Nim. */
  public void jouer() {
    int contrainte = demanderContrainte();
    Nim nim = new Nim(nombreTas, contrainte);
    Joueur joueurCourant = lesJoueurs.get(0);

    // Game loop

    while (nim.getEtatPartie() == EtatPartie.EN_COURS) {
      ihm.afficherMessage(nim.getListeTas().toString());

      int[] choix = demanderChoix(nim, joueurCourant);
      try {
        nim.retirerAllumettes(numeroJoueur(joueurCourant), choix[0], choix[1]);
      } catch (IllegalArgumentException e) {
        ihm.afficherErreur(e.getMessage());
      }

      joueurCourant = joueurSuivant(joueurCourant);
    }

    // Post-game

    Joueur gagnant =
        nim.getEtatPartie() == EtatPartie.VICTOIRE_JOUEUR_1 ? lesJoueurs.get(0) : lesJoueurs.get(1);
    Joueur perdant =
        nim.getEtatPartie() == EtatPartie.VICTOIRE_JOUEUR_1 ? lesJoueurs.get(1) : lesJoueurs.get(0);

    gagnant.ajouterPartieGagnee();
    ihm.afficherMessage("Victoire de " + gagnant.getNom() + " !");

    boolean rejouer = demanderRejouer();

    if (!rejouer) {
      int victoiresGagnant = gagnant.getNbrPartieGagnee();
      int victoiresPerdant = perdant.getNbrPartieGagnee();

      if (victoiresGagnant > victoiresPerdant) {
        gagnant = lesJoueurs.get(0);
        perdant = lesJoueurs.get(1);
      } else {
        gagnant = lesJoueurs.get(1);
        perdant = lesJoueurs.get(0);
      }

      ihm.afficherScores(gagnant, perdant);
      ihm.afficherVainqueur(gagnant, victoiresGagnant == victoiresPerdant);
      return;
    }

    ihm.afficherScores(gagnant, perdant);
    // Appel récursif à la fin pour profiter de la Tail-Call Optimization
    jouer();
  }

  /**
   * Récupère le joueur suivant.
   *
   * @param joueurCourant le joueur actuel.
   * @return le joueur suivant.
   */
  private Joueur joueurSuivant(Joueur joueurCourant) {
    if (joueurCourant == lesJoueurs.get(0)) return lesJoueurs.get(1);
    else return lesJoueurs.get(0);
  }

  /**
   * Récupère le numéro du joueur.
   *
   * @param joueur le joueur dont on veut le numéro
   * @return le numéro du joueur
   */
  private int numeroJoueur(Joueur joueur) {
    if (joueur == lesJoueurs.get(0)) return 1;
    else return 2;
  }
}
