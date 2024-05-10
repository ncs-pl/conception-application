/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.CoupInvalideException;
import fr.nc0.cda.modele.EtatPartie;
import fr.nc0.cda.modele.EtatPartieException;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.modele.nim.Nim;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;

/** Contrôleur du jeu de Nim. */
public class ControleurJeuNim extends ControleurTemplate {
  /** Liste des joueurs de la partie. */
  private final ArrayList<Joueur> lesJoueurs;

  /** Le nombre de tas pour les parties. */
  private final int nombreTas;

  /** Une partie du jeu de Nim */
  private Nim nim;

  /** Le numéro du joueur dont c'est le tour. */
  private int joueurCourant;

  /**
   * Créer un contrôleur de jeu de Nim.
   *
   * @param ihm l'interface homme-machine.
   */
  public ControleurJeuNim(Ihm ihm) {
    super(ihm);

    while (true) {
      int tas = ihm.demanderInt("Saisissez le nombre de tas pour la partie");
      if (tas > 0) {
        nombreTas = tas;
        break;
      }

      ihm.afficherErreur("Le nombre de tas ne peut pas être négatif ou nul");
    }

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

  @Override
  String creerAffichagePlateau() {
    return nim.getListeTas().toString();
  }

  @Override
  EtatPartie getEtatPartie() {
    return nim.getEtatPartie();
  }

  @Override
  void initialiserPartie() {
    while (true) {
      int contrainte =
          ihm.demanderInt(
              "Saisissez le nombre maximal d'allumettes à retirer par "
                  + "coup, ou 0 pour ne pas mettre de contrainte");

      if (contrainte >= 0) {
        nim = new Nim(nombreTas, contrainte);
        joueurCourant = 0;
        break;
      }

      ihm.afficherErreur("La contrainte ne peut pas être négative");
    }
  }

  @Override
  void jouerCoup() throws CoupInvalideException, EtatPartieException {
    while (true) {
      int[] choix =
          ihm.demanderDeuxInt(getJoueur(joueurCourant).toString() + ", à vous de jouer un coup");
      int tas = choix[0];
      int allumettes = choix[1];
      int contrainte = nim.getContrainte();

      if (tas < 1 || tas > nim.getListeTas().taille) {
        ihm.afficherErreur("Le tas choisi n'existe pas");
        continue;
      }

      if (nim.getListeTas().get(tas).getAllumettes() < 1) {
        ihm.afficherErreur("Le tas choisi est vide");
        continue;
      }

      if (allumettes < 1) {
        ihm.afficherErreur("Nombre d'allumettes invalide");
        continue;
      }

      if (allumettes > nim.getListeTas().get(tas).getAllumettes()) {
        ihm.afficherErreur("Nombre d'allumettes supérieur au nombre d'allumettes dans le tas");
        continue;
      }

      if (contrainte != 0 && allumettes > contrainte) {
        ihm.afficherErreur("Nombre d'allumettes supérieur à la contrainte");
        continue;
      }

      nim.retirerAllumettes(joueurCourant, choix[0], choix[1]);
      break;
    }
  }

  @Override
  Joueur getJoueur(int numeroJoueur) {
    if (numeroJoueur < 1 || numeroJoueur > lesJoueurs.size()) return null;
    return lesJoueurs.get(numeroJoueur - 1);
  }

  @Override
  void changerJoueurCourant() {
    if (joueurCourant == 1) ++joueurCourant;
    else if (joueurCourant == 2) --joueurCourant;
    else joueurCourant = 1; // unreachable.
  }
}
