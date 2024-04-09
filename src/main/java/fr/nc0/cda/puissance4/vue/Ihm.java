/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.vue;

import fr.nc0.cda.puissance4.modele.CellulePuissance4;
import fr.nc0.cda.puissance4.modele.Joueur;
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
   * @param joueur le joueur qui doit choisir
   * @return Le numéro de colonne choisi par le joueur.
   */
  public int choixColonne(Joueur joueur) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        joueur.getNom() + ", saisissez le numéro de colonne à jouer (entier entre 1 et 7) :");
    while (true) if (scanner.hasNextInt()) return scanner.nextInt();
  }

  /**
   * Demande à l'utilisateur s'il souhaite jouer avec la rotation de la grille.
   *
   * @return true si l'utilisateur souhaite activer la rotation, false sinon.
   */
  public boolean activerRotation() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Voulez-vous activer la rotation de la grille ? (Y/N)");
    while (true) if (scanner.hasNext()) return scanner.next().equalsIgnoreCase("y");
  }

  /**
   * Demande à l'utilisateur dans quel sens il souhaite tourner la grille.
   *
   * @param joueur le joueur qui doit choisir
   * @return "droite" si l'utilisateur souhaite tourner la grille dans le sens des aiguilles d'une
   *     montre, "gauche" sinon.
   */
  public String choixRotation(Joueur joueur) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(
        joueur.getNom() + ", dans quel sens voulez-vous tourner la grille ? (droite/gauche)");
    while (true) if (scanner.hasNext()) return scanner.next().toLowerCase();
  }

  /**
   * Demande à l'utilisateur ce qu'il souhaite faire.
   *
   * @param joueur Le joueur courant.
   * @return "jouer" si l'utilisateur souhaite jouer, "rotation" sinon.
   */
  public String choixJouer(Joueur joueur) {
    Scanner scanner = new Scanner(System.in);
    System.out.print(joueur.getNom() + ", que voulez-vous faire  ? (jouer/rotation)");
    while (true) if (scanner.hasNext()) return scanner.next().toLowerCase();
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
    while (true) if (scanner.hasNext()) return scanner.next();
  }

  /**
   * Affiche la grille de jeu.
   *
   * @param grille La grille de jeu représentée par une liste de listes de cellules.
   */
  public void afficherGrille(List<List<CellulePuissance4>> grille) {
    String affichage = "";

    for (List<CellulePuissance4> ligne : grille) {
      affichage += " |";
      for (CellulePuissance4 cellule : ligne) affichage += " " + cellule + " |";
      affichage += "\n";
    }

    affichage += "   1   2   3   4   5   6   7\n";

    System.out.println(affichage);
  }

  /**
   * Affiche le nom du vainqueur de la partie.
   *
   * @param gagnant le joueur gagnant.
   */
  public void afficherGagnant(Joueur gagnant) {
    System.out.println(" \n Le gagnant de cette partie est " + gagnant.getNom() + " !");
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

    while (true) {
      if (scanner.hasNext()) {
        switch (scanner.next()) {
          case "Y":
            return true;
          case "N":
            return false;
          default:
            System.out.println("L'entrée ne correspond pas à Y ou N");
        }

        scanner.nextLine();
      }
    }
  }

  /**
   * Affiche les statistiques de la partie.
   *
   * @param gagnant Le joueur gagnant.
   * @param perdant Le joueur perdant.
   * @param exaeco Indique si les deux joueurs ont le même nombre de parties gagnées.
   */
  public void afficherStats(Joueur gagnant, Joueur perdant, boolean exaeco) {
    String affichage =
        "Etat des scores : "
            + gagnant.getNom()
            + " -> "
            + gagnant.getNbrPartieGagnee()
            + " V | "
            + perdant.getNom()
            + " -> "
            + perdant.getNbrPartieGagnee()
            + " V";
    System.out.println(affichage);
    if (exaeco) System.out.println("Égalité !");
    else System.out.println("Victoire de " + gagnant.getNom());
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
