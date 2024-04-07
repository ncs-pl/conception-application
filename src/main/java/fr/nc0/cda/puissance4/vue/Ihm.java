/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.vue;

import fr.nc0.cda.puissance4.modele.CellulePuissance4;
import java.util.List;
import java.util.Scanner;

/**
 * Interface homme-machine (IHM) pour le jeu Puissance 4.
 *
 * <p>Cette classe gère les interactions entre les joueurs et le jeu, notamment l'affichage de la
 * grille de jeu, la saisie des coups des joueurs, la gestion des noms des joueurs et des
 * statistiques de la partie.
 */
public class Ihm {
  /**
   * Retourne le numéro de colonne choisie par le joueur courant.
   *
   * @param joueurCourant Le nom du joueur courant.
   * @return Le numéro de colonne choisi par le joueur.
   */
  public int choixColonne(String joueurCourant) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        joueurCourant
            + " à vous de jouer ! Saisissez le numéro de colonne à jouer (entier entre 1 et 7) :");
    int colonne = 0;
    if (scanner.hasNextInt()) {
      colonne = scanner.nextInt();
    }
    return colonne;
  }

  /**
   * Attribue à chacun des deux joueurs le nom qu'il choisit.
   *
   * @param numeroJoueur L'ordre du joueur (1 ou 2).
   * @return Le nom du joueur.
   */
  public String selectNomJoueur(int numeroJoueur) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Veuillez saisir le nom du joueur " + numeroJoueur + " : ");
    return scanner.next();
  }

  /**
   * Affiche la grille de jeu.
   *
   * @param grille La grille de jeu représentée par une liste de listes de cellules.
   */
 public void afficherGrille(List<List<CellulePuissance4>> grille) {
    String affichage = "";

    for (List<CellulePuissance4> ligne : grille) {
      affichage += "|";
      for (CellulePuissance4 cellule : ligne) affichage += " " + cellule + " |";
      affichage += "\n";
    }

    affichage += "  1    2    3    4    5    6    7\n";

    System.out.println(affichage);
  }

  /**
   * Affiche le nom du vainqueur de la partie.
   *
   * @param nomGagnant Le nom du joueur gagnant.
   */
  public void afficherGagnant(String nomGagnant) {
    System.out.println(" \n Le gagnant de cette partie est " + nomGagnant);
  }

  /** Affiche un message indiquant qu'il n'y a aucun gagnant pour cette partie (match nul). */
  public void matchNul() {
    System.out.println(" \n Aucun gagnant pour cette partie.");
  }

  /**
   * Propose aux joueurs de rejouer une partie.
   *
   * @return true si les joueurs souhaitent rejouer, false sinon.
   */
  public boolean rejouer() {
    System.out.println(" \n Souhaitez-vous rejouer une partie ? (Y/N)");
    Scanner scanner = new Scanner(System.in);
    boolean choix = false;
    boolean entreeInvalide = true;
    while (entreeInvalide) {
      if (scanner.hasNext()) {
        switch (scanner.next()) {
          case "Y":
            choix = true;
            entreeInvalide = false;
            break;
          case "N":
            entreeInvalide = false;
            break;
          default:
            System.out.println("L'entrée ne correspond pas à Y ou N");
        }
        scanner.nextLine();
      }
    }
    return choix;
  }

  /**
   * Affiche les statistiques de la partie.
   *
   * @param nomJoueur1 Le nom du premier joueur.
   * @param nomJoueur2 Le nom du deuxième joueur.
   * @param nbrVictoireJoueur1 Le nombre de parties gagnées par le premier joueur.
   * @param nbrVictoireJoueur2 Le nombre de parties gagnées par le deuxième joueur.
   * @param exaeco Indique si les deux joueurs ont le même nombre de parties gagnées.
   */
  public void afficherStats(
      String nomJoueur1,
      String nomJoueur2,
      int nbrVictoireJoueur1,
      int nbrVictoireJoueur2,
      boolean exaeco) {
    String affichage =
        "Etat des scores : "
            + nomJoueur1
            + " -> "
            + nbrVictoireJoueur1
            + " V | "
            + nomJoueur2
            + " -> "
            + nbrVictoireJoueur2
            + " V";
    if (exaeco) {
      System.out.println("Egalité ! Les deux joueurs ont gagné autant de parties. \n" + affichage);
    } else {
      System.out.println(affichage);
    }
  }

  /**
   * Affiche un message à l'utilisateur.
   *
   * @param msg Le message à afficher.
   */
  public void message(String msg) {
    System.out.println(msg);
  }
}
