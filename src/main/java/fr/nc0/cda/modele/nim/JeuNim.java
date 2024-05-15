/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

import fr.nc0.cda.modele.jeu.*;

/** Représente une partie du jeu de Nim. */
public class JeuNim extends Jeu<PlateauNim, ChoixNim> {
  /** Contrainte sur le nombre maximal d'allumettes à retirer par coup (0 → pas de contrainte). */
  private final int contrainte;

  /** État de la partie. */
  private EtatPartie etatPartie = EtatPartie.EN_COURS;

  /** Le nombre de tas de la partie */
  private final int nombreTas;

  /**
   * Créer une partie et l'initialise avec le nombre de tas donné.
   *
   * @param nbrTas le nombre de tas de la partie.
   * @param contrainte le nombre maximal d'allumettes à retirer par coup.
   */
  public JeuNim(int nbrTas, int contrainte) {
    super(new PlateauNim(nbrTas));
    this.contrainte = contrainte;
    this.nombreTas = nbrTas;
  }

  @Override
  public void jouer(Joueurs joueur, ChoixNim choix)
      throws CoupInvalideException, EtatPartieException {
    if (etatPartie != EtatPartie.EN_COURS) {
      throw new EtatPartieException("Partie terminée.");
    }

    int tas = choix.getTas();
    int allumettes = choix.getAllumettes();
    if (tas < 1 || tas > nombreTas) {
      throw new CoupInvalideException("Numéro de tas invalide.");
    }

    if (allumettes < 1) {
      throw new CoupInvalideException("Nombre d'allumettes invalide.");
    }

    if (contrainte != 0 && allumettes > contrainte) {
      throw new CoupInvalideException(
          "Nombre d'allumettes supérieur à la contrainte de la partie.");
    }

    int allumettesRestantes = plateau.getAllumettesRestantes(tas);
    if (allumettes > allumettesRestantes) {
      throw new CoupInvalideException("Nombre d'allumettes invalide.");
    }

    plateau.retirerAllumettes(tas, allumettes);

    if (plateau.estVide()) {
      etatPartie =
          joueur == Joueurs.JOUEUR_1 ? EtatPartie.VICTOIRE_JOUEUR_2 : EtatPartie.VICTOIRE_JOUEUR_1;
    }
  }
}
