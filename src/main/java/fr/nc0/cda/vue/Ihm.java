/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.vue;

import fr.nc0.cda.modele.joueur.Joueur;
import java.util.Scanner;

/** Interface homme-machine pour le système. */
public class Ihm {
  /** Prompt pour les demandes d'entrées d'utilisateur. */
  private static final String PROMPT = "> ";

  /**
   * Attend un certain nombre de millisecondes.
   *
   * @param ms le nombre de millisecondes à attendre
   */
  private void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Écris un message dans stdout.
   *
   * @param message le message à afficher
   */
  private void println(String message) {
    sleep(200);
    System.out.println(message);
  }

  /**
   * Écris un message dans stdout sans retour à la ligne.
   *
   * @param message le message à afficher
   */
  private void print(String message) {
    sleep(300);
    System.out.print(message);
  }

  /**
   * Écris un message dans stderr
   *
   * @param message le message d'erreur à afficher
   */
  private void eprintln(String message) {
    System.err.println(message);
  }

  // ===========================================
  //            Méthodes générales
  // ===========================================

  /**
   * Affiche un message dans la console.
   *
   * @param message le message à afficher
   */
  public void afficherMessage(String message) {
    println(message);
  }

  /**
   * Affiche un message d'erreur dans la console.
   *
   * @param message le message d'erreur à afficher
   */
  public void afficherErreur(String message) {
    eprintln(message);
  }

  /**
   * Demande un texte à l'utilisateur.
   *
   * @param question la question posée à l'utilisateur
   * @return la réponse de l'utilisateur
   */
  public String demanderString(String question) {
    println(question);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      print(PROMPT);
      if (scanner.hasNext()) return scanner.nextLine();

      eprintln("Valeur entrée invalide");
      scanner.nextLine();
    }
  }

  /**
   * Demande un entier à l'utilisateur.
   *
   * @param question la question posée à l'utilisateur
   * @return la réponse de l'utilisateur
   */
  public int demanderInt(String question) {
    println(question);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      print(PROMPT);
      if (scanner.hasNextInt()) return scanner.nextInt();

      eprintln("Valeur entrée non-numérique");
      scanner.nextLine();
    }
  }

  /**
   * Demande deux entiers à l'utilisateur, séparés par un espace.
   *
   * @param question la question posée à l'utilisateur
   * @return un tableau de deux entiers
   */
  public int[] demanderDeuxInt(String question) {
    println(question);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      print(PROMPT);
      if (scanner.hasNextInt()) {
        int[] choix = new int[2];
        choix[0] = scanner.nextInt();
        if (scanner.hasNextInt()) {
          choix[1] = scanner.nextInt();
          return choix;
        }
      }

      eprintln("Valeur entrée invalide");
      scanner.nextLine();
    }
  }

  /**
   * Demande un booléen à l'utilisateur (question fermée).
   *
   * @param question la question posée à l'utilisateur
   * @return true si l'utilisateur répond Oui, false sinon
   */
  public boolean demanderBoolean(String question) {
    println(question);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      print("> ");
      if (scanner.hasNext()) {
        switch (scanner.next().toLowerCase()) {
          case "oui", "o", "yes", "y", "true", "1", "on":
            return true;
          case "non", "n", "no", "false", "0", "off":
            return false;
          default:
            eprintln("Merci de ne rentrer que \"Oui\" ou \"Non\".");
        }

        scanner.nextLine();
      }
    }
  }

  /**
   * Affiche les scores des joueurs.
   *
   * @param joueur1 le premier joueur.
   * @param joueur2 le deuxième joueur.
   */
  public void afficherScores(Joueur joueur1, Joueur joueur2) {
    println(
        "Scores : \n"
            + joueur1.getNom()
            + " avec "
            + joueur1.getVictoires()
            + " victoire(s) et "
            + joueur2.getNom()
            + " avec "
            + joueur2.getVictoires()
            + " victoire(s)");
  }

  /**
   * Affiche le vainqueur de la partie.
   *
   * @param gagnant le joueur gagnant
   * @param exaequo indique si les deux joueurs ont le même nombre de parties gagnées
   */
  public void afficherVainqueur(Joueur gagnant, boolean exaequo) {
    if (exaequo) {
      println("Égalité !");
    } else {
      println("Victoire de " + gagnant.getNom());
    }
  }
}
