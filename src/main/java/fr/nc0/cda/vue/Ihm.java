/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.vue;

import java.util.ArrayList;
import java.util.Scanner;

public class Ihm {

  // !!!!! accepte les int suivant (input : 5  56 9 7) mais ne les traites pas
  // Pour tout les scanner voir delimiter pattern

  public int selectNbrTas() {

    boolean tasNotDone = true;
    int nbrTas = 0;

    Scanner scanner = new Scanner(System.in);

    while (tasNotDone) {
      System.out.print("Veuillez saisir le nombre de tas (entier >= 1)  pour la partie : ");
      if (scanner.hasNextInt()) {
        nbrTas = scanner.nextInt();
        tasNotDone = false;
      } else {
        scanner.nextLine();
        System.out.println("La saisie doit être un chiffre entier >= 1");
      }
    }
    return nbrTas;
  }

  public String selectNomJoueur(int numeroJoueur) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Veuillez saisir le nom du joueur " + numeroJoueur + " : ");
    return scanner.next();
  }

  public void afficherEtatPartie(ArrayList<Integer> tas) {
    String affichage = "";
    for (int i = 0; i < tas.size(); ++i) {
      affichage = affichage + "Tas " + (i + 1) + " : ";
      affichage = affichage + patternAffichage(" ", (tas.size() * 2 - 1) - tas.get(i));
      affichage = affichage + patternAffichage("| ", tas.get(i)) + "\n";
    }
    System.out.println("Etat de la partie : \n\n" + affichage);
  }

  private String patternAffichage(String pattern, int nbr) {
    if (nbr > 0) {
      return pattern + patternAffichage(pattern, nbr - 1);
    } else {
      return "";
    }
  }

  public int[] selectAlumette(String joueurNom) {
    Scanner scanner = new Scanner(System.in);
    boolean enCours = true;
    int[] choix = {0, 0};

    while (enCours) {
      System.out.print(
          joueurNom + " : à vous de jouer un coup (sous la forme 'tas allumettes') : ");
      if (scanner.hasNextInt()) {
        choix[0] = scanner.nextInt();
        if (scanner.hasNextInt()) {
          choix[1] = scanner.nextInt();
          enCours = false;
        } else {
          System.out.println(
              "Votre saisie ne comprend pas d'entier sous la forme ('tas alumette')");
        }
      } else {
        System.out.println("Votre saisie ne comprend pas d'entier sous la forme ('tas alumette')");
      }
      scanner.nextLine();
    }
    choix[0] = choix[0] - 1;
    return choix;
  }

  // Retourne true si les joueurs décide de relancer une partie, false si l'inverse
  public boolean finPartie(
      String nomGagnant, String nomPerdant, int nbrVictoireGagnant, int nbrVictoirePerdant) {
    etatNbrVictoire(nomGagnant, nomPerdant, nbrVictoireGagnant, nbrVictoirePerdant);
    System.out.println("Voulez-vous rejouer une nouvelle partie ? (Y/N) : ");

    Scanner scanner = new Scanner(System.in);
    boolean enCours = true;
    boolean choix = false;

    while (enCours) {
      if (scanner.hasNext()) {
        switch (scanner.next()) {
          case "Y":
            choix = true;
            enCours = false;
            break;
          case "N":
            enCours = false;
            break;
          default:
            System.out.println("L'entrée ne correspond pas à Y ou N");
        }
      }
      scanner.nextLine();
    }
    return choix;
  }

  public void afficheGagnant(
      String nomGagnant,
      String nomPerdant,
      int nbrVictoireGagnant,
      int nbrVictoirePerdant,
      boolean exaequo) {
    etatNbrVictoire(nomGagnant, nomPerdant, nbrVictoireGagnant, nbrVictoirePerdant);
    if (exaequo) {
      System.out.println(
          "Ex-aequo ! " + nomGagnant + " et " + nomPerdant + " ont gagnés autant de partie !");
    } else {
      System.out.println(nomGagnant + " est le grand gagnant !");
    }
  }

  private void etatNbrVictoire(
      String nomJoueur1, String nomJoueur2, int nbrVictoireJoueur1, int nbrVictoireJoueur2) {
    System.out.println(
        "Etat des scores : "
            + nomJoueur1
            + " -> "
            + nbrVictoireJoueur1
            + " V | "
            + nomJoueur2
            + " -> "
            + nbrVictoireJoueur2
            + " V");
  }

  public void message(String msg) {
    System.out.println(msg);
  }
}
