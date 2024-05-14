/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

import fr.nc0.cda.modele.jeu.CoupInvalideException;
import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.EtatPartieException;

/** Représente une partie du jeu de Nim. */
public class JeuNim {
  /** Contrainte sur le nombre maximal d'allumettes à retirer par coup (0 → pas de contrainte). */
  private final int contrainte;

  /** Le plateau de la partie */
  private final PlateauNim plateauNim;

  /** État de la partie. */
  private EtatPartie etatPartie = EtatPartie.EN_COURS;

  /**
   * Créer une partie et l'initialise avec le nombre de tas donné.
   *
   * @param nbrTas le nombre de tas de la partie.
   * @param contrainte le nombre maximal d'allumettes à retirer par coup.
   */
  public JeuNim(int nbrTas, int contrainte) {
    if (nbrTas < 1) throw new IllegalArgumentException("Nombre de tas négatif ou nul");

    this.contrainte = contrainte;
    this.plateauNim = new PlateauNim(nbrTas);
  }

  /**
   * Retourne le nombre maximal d'allumettes à retirer par coup, 0 si pas de contrainte.
   *
   * @return le nombre maximal d'allumettes à retirer par coup.
   */
  public int getContrainte() {
    return contrainte;
  }

  /**
   * Récupère la liste des tas de la partie.
   *
   * @return la liste des tas de la partie.
   */
  public PlateauNim getPlateauNim() {
    return plateauNim;
  }

  /**
   * Récupère l'état de la partie.
   *
   * @return l'état de la partie.
   */
  public EtatPartie getEtatPartie() {
    return etatPartie;
  }

  /**
   * Vérifie si l'index du tas est valide, c'est-à-dire s'il est compris entre 1 et le nombre de
   * tas.
   *
   * @param index l'index du tas à vérifier, doit être supérieur ou égal à 1.
   * @return true si l'index est valide, false sinon
   */
  public boolean indexTasValide(int index) {
    return index > 0 && index <= plateauNim.taille;
  }

  /**
   * Retire un nombre d'allumettes d'un tas, si possible.
   *
   * @param joueur le joueur qui retire les allumettes, doit être 1 ou 2.
   * @param indexTas le numéro du tas, doit être compris entre 1 et la taille de la liste
   * @param nbAllumettes le nombre d'allumettes à retirer, doit être supérieur ou égal à 1 et
   *     inférieur ou égal au nombre d'allumettes dans le tas, ainsi qu'à la contrainte
   * @throws CoupInvalideException si l'index du tas ou le nombre d'allumettes est invalide
   * @throws EtatPartieException si la partie est terminée
   */
  public void retirerAllumettes(int joueur, int indexTas, int nbAllumettes)
      throws CoupInvalideException, EtatPartieException {
    if (etatPartie != EtatPartie.EN_COURS) {
      throw new EtatPartieException("Partie terminée.");
    }

    if (joueur < 1 || joueur > 2) {
      throw new CoupInvalideException("Numéro de joueur invalide.");
    }

    if (!indexTasValide(indexTas)) {
      throw new CoupInvalideException("Numéro de tas invalide.");
    }

    if (nbAllumettes < 1) {
      throw new CoupInvalideException("Nombre d'allumettes invalide.");
    }

    if (contrainte != 0 && nbAllumettes > contrainte) {
      throw new CoupInvalideException(
          "Nombre d'allumettes supérieur à la contrainte de la partie.");
    }

    int allumettesRestantes = plateauNim.getAllumettesRestantes(indexTas);
    if (nbAllumettes > allumettesRestantes) {
      throw new CoupInvalideException("Nombre d'allumettes invalide.");
    }

    plateauNim.retirerAllumettes(indexTas, nbAllumettes);

    // Check de victoire
    if (plateauNim.estVide()) {
      etatPartie = joueur == 1 ? EtatPartie.VICTOIRE_JOUEUR_2 : EtatPartie.VICTOIRE_JOUEUR_1;
    }
  }
}
