/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.nim.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Représente une partie du jeu de Nim. */
public class Nim {
  /** Nombre de tas de la partie. */
  private final int nbrTas;

  /**
   * Liste des tas de la partie, sous forme d'une liste. L'élément i de cette liste correspond au
   * nombre d'allumettes dans le tas i.
   */
  private List<Integer> tas;

  /** État de la partie. */
  private EtatPartieNim etatPartie;

  /** Contrainte sur le nombre maximal d'allumettes à retirer par coup (0 => pas de contrainte). */
  private int contrainte;

  /**
   * Créer une partie et l'initialise avec le nombre de tas donné.
   *
   * @param nbrTas le nombre de tas de la partie.
   * @param contrainte le nombre maximal d'allumettes à retirer par coup.
   */
  public Nim(int nbrTas, int contrainte) {
    if (nbrTas >= 1) {
      this.nbrTas = nbrTas;
    } else {
      throw new IllegalArgumentException("Nombre de Tas choisis < 1");
    }
    this.contrainte = contrainte;
  }

  /** Initialise les tas de la partie avec le nombre d'allumettes correspondant. */
  private void setupTas() {
    tas = new ArrayList<>(nbrTas);
    for (int i = 1; i <= nbrTas; ++i) tas.add(2 * i - 1);
  }

  /**
   * Modifie la valeur de la contrainte.
   *
   * @param contrainte la nouvelle valeur de la contrainte.
   */
  public void setContrainte(int contrainte) {
    this.contrainte = contrainte;
  }

  /** Démarre la partie en initialisant les tas et en passant l'état de la partie à EnCours. */
  public void demarrerPartie() {
    setupTas();
    etatPartie = EtatPartieNim.EN_COURS;
  }

  /**
   * Récupère la liste des tas de la partie.
   *
   * @return la liste des tas de la partie.
   */
  public List<Integer> getTas() {
    return tas;
  }

  /**
   * Supprime un nombre donné d'allumettes dans un tas donné.
   *
   * @param choix un tableau de deux entiers, le premier correspondant à l'index du tas et le second
   *     au nombre d'allumettes à supprimer.
   * @throws IllegalArgumentException si le choix est invalide.
   */
  public void supprAllumettes(int[] choix) {
    try {
      verifierChoix(choix);
      tas.set(choix[0], tas.get(choix[0]) - choix[1]);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  /**
   * Vérifie si le choix est valide.
   *
   * @param choix un tableau de deux entiers, le premier correspondant à l'index du tas et le second
   */
  private void verifierChoix(int[] choix) {
    if (tas.size() <= choix[0] || choix[0] < 0) {
      throw new IllegalArgumentException("Valeur du tas incorrecte");
    } else {
      if (choix[1] <= 0 || choix[1] > tas.get(choix[0])) {
        throw new IllegalArgumentException("Valeur des allumettes incorrecte");
      }
    }
    if (this.contrainte != 0) {
      if (choix[1] > this.contrainte) {
        throw new IllegalArgumentException(
            "Valeur des allumettes incorrecte : vous pouvez retirer "
                + this.contrainte
                + " allumettes au maximum.");
      }
    }
  }

  /**
   * Récupère l'état de la partie.
   *
   * @return l'état de la partie.
   */
  public EtatPartieNim getEtatPartie() {
    return etatPartie;
  }

  /**
   * Vérifie si la partie est finie. Si tous les tas sont vides, l'état de la partie est mis à Fini.
   */
  public void checkEtatPartie() {
    boolean estFini = true;
    Iterator<Integer> it = tas.iterator();
    while (it.hasNext()) {
      if (it.next() > 0) {
        estFini = false;
      }
    }

    if (estFini) {
      etatPartie = EtatPartieNim.FINI;
    }
  }
}
