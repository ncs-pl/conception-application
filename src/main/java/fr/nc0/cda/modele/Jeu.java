/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

/** Jeu représente un jeu à deux joueurs pouvant être implémenté dans notre moteur de jeu. */
public abstract class Jeu<P extends Plateau, C extends Choix> {
  /** Le plateau du jeu */
  protected P plateau;

  /** L'état de la partie actuelle */
  protected EtatPartie etatPartie = EtatPartie.EN_COURS;

  /**
   * Créer un jeu.
   *
   * @param plateau le plateau du jeu.
   */
  protected Jeu(P plateau) {
    this.plateau = plateau;
  }

  /**
   * Retourne le plateau du jeu.
   *
   * @return le plateau actuel
   */
  public P getPlateau() {
    return plateau;
  }

  /**
   * Retourne l'état de la partie en cours.
   *
   * @return l'état de la partie.
   */
  public EtatPartie getEtatPartie() {
    return etatPartie;
  }

  /**
   * Jouer un coup de la partie
   *
   * @param joueur le joueur qui joue
   * @param choix le choix du joueur
   * @throws CoupInvalideException le coup est invalide.
   * @throws EtatPartieException la partie est invalide.
   */
  public abstract void jouer(Joueurs joueur, C choix)
      throws CoupInvalideException, EtatPartieException;
}
