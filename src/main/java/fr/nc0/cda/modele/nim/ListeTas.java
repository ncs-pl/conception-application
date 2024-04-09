/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

import java.util.ArrayList;
import java.util.List;

/** Représente une liste indexée de tas de la partie du jeu de Nim. */
public class ListeTas {
  /** Taille de la liste de tas. */
  public final int taille;

  /** Liste des tas de la partie. */
  private final List<Tas> tas;

  /**
   * Crée une liste de tas avec une taille et une liste de tas.
   *
   * @param taille la taille de la liste de tas
   */
  public ListeTas(int taille) {
    this.taille = taille;

    tas = new ArrayList<>(taille);
    for (int i = 1; i <= taille; ++i) {
      // le tas i dispose de 2^i - 1 allumettes
      int allumettes = 2 * i - 1;
      tas.add(new Tas(i, allumettes, allumettes));
    }
  }

  /**
   * Récupère le tas à l'index donné.
   *
   * @param index l'index du tas à récupérer, doit être compris entre 1 et la taille de la liste
   * @return le tas à l'index donné, null si l'index est invalide
   */
  public Tas get(int index) {
    if (index < 1 || index > taille) return null;
    return tas.get(index - 1);
  }

  /**
   * Vérifie si la liste de tas est vide.
   *
   * @return true si la liste de tas est vide, false sinon
   */
  public boolean estVide() {
    for (Tas t : tas) if (t.getAllumettes() > 0) return false;
    return true;
  }

  /**
   * Retire un nombre d'allumettes d'un tas, si possible.
   *
   * @param indexTas le numéro du tas, doit être compris entre 1 et la taille de la liste
   * @param nbAllumettes le nombre d'allumettes à retirer, doit être supérieur ou égal à 1 et
   *     inférieur ou égal au nombre d'allumettes dans le tas
   * @throws IllegalArgumentException si l'index du tas ou le nombre d'allumettes est invalide
   */
  public void retirerAllumettes(int indexTas, int nbAllumettes) {
    if (indexTas < 1 || indexTas > taille)
      throw new IllegalArgumentException("Index de tas invalide");

    Tas t = get(indexTas);
    t.retirerAllumettes(nbAllumettes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Tas t : tas) sb.append(t).append("\n");
    return sb.toString();
  }
}
