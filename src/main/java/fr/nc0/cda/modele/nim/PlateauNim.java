/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

import fr.nc0.cda.modele.jeu.Plateau;

import java.util.ArrayList;
import java.util.List;

/** Représente une liste indexée de tas de la partie du jeu de Nim. */
public class PlateauNim implements Plateau {
  /** Taille de la liste de tas. */
  private final int taille;

  /** Contrainte de retrait */
  private final int contrainte;

  /** Liste des tas de la partie. */
  private final List<Integer> tas;

  /**
   * Crée une liste de tas avec une taille et une liste de tas.
   *
   * @param taille la taille de la liste de tas
   * @param contrainte contrainte de retrait
   */
  public PlateauNim(int taille, int contrainte) {
    super();
    this.taille = taille;
    this.contrainte = contrainte;

    tas = new ArrayList<>(taille);
    for (int i = 1; i <= taille; ++i) {
      // le tas i dispose de 2*i - 1 allumettes
      tas.add(2 * i - 1);
    }
  }

  /**
   * Récupère le nombre d'allumettes restantes dans le tas demandé.
   *
   * @param tas l'index du tas à récupérer, doit être compris entre 1 et la taille de la liste
   * @return le tas à l'index donné, null si l'index est invalide
   * @throws IllegalArgumentException si l'index du tas ou le nombre d'allumettes est invalide
   */
  public Integer getAllumettesRestantes(int tas) {
    if (tas < 1 || tas > taille) {
      throw new IllegalArgumentException("Index de tas invalide");
    }
    return this.tas.get(tas - 1);
  }

  /**
   * Retire un nombre d'allumettes d'un tas, si possible.
   *
   * @param tas le numéro du tas, doit être compris entre 1 et la taille de la liste
   * @param allumettes le nombre d'allumettes à retirer, doit être supérieur ou égal à 1 et
   *     inférieur ou égal au nombre d'allumettes dans le tas
   * @throws IllegalArgumentException si l'index du tas ou le nombre d'allumettes est invalide
   */
  public void retirerAllumettes(int tas, int allumettes) {
    if (tas < 1 || tas > taille) {
      throw new IllegalArgumentException("Index de tas invalide");
    }

    int allumettesRestantes = this.tas.get(tas - 1);
    if (allumettes < 1 || allumettes > allumettesRestantes) {
      throw new IllegalArgumentException("Nombre d'allumettes invalide");
    }

    if (allumettes > contrainte) {
      throw new IllegalArgumentException("Contrainte non respectée");
    }

    this.tas.set(tas - 1, allumettesRestantes - allumettes);
  }

  /**
   * Vérifie que tous les tas soient vides
   *
   * @return true si tous les tas sont vides
   */
  public boolean estVide() {
    for (Integer t : tas) {
      if (t != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Modifie à la main le nombre d'allumettes dans un tas. A NE PAS UTILISER PLEASE
   *
   * @param tas le tas
   * @param allumettes le nombre d'allumettes
   */
  public void setAllumettes(int tas, int allumettes) {
    if (tas < 1 || tas > taille) {
      throw new IllegalArgumentException("Index de tas invalide");
    }

    if (allumettes < 1) {
      throw new IllegalArgumentException("Nombre d' allumettes invalide");
    }

    this.tas.set(tas - 1, allumettes);
  }

  /**
   * Créer une copie du plateau et de sa grille.
   *
   * @return la copie
   */
  public PlateauNim dupliquer() {
    PlateauNim copie = new PlateauNim(taille, contrainte);

    for (int i = 1; i <= taille; ++i) {
      int allumettes = tas.get(i);
      copie.setAllumettes(i, allumettes);
    }

    return copie;
  }

  /**
   * Retourne la taille du plateau.
   *
   * @return la taille
   */
  public int getTaille() {
    return taille;
  }

  /**
   * Retourne la contrainte
   *
   * @return la contrainte.
   */
  public int getContrainte() {
    return contrainte;
  }

  @SuppressWarnings("StringConcatenationInLoop")
  @Override
  public String toString() {
    String string = "";
    int i = 1;
    for (Integer t : tas) {
      string += "    Tas \033[1m" + i + "\033[0m :   ";
      string += " \033[0;32m|\033[0m".repeat(Math.max(0, t));
      string += "\n";
      ++i;
    }
    return string;
  }
}
