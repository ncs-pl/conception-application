/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

/** Représente un tas de la partie du jeu de Nim. */
public class Tas {
  /** Numéro du tas. */
  private final int numero;

  /** Nombre maximal d'allumettes dans le tas. */
  private final int maxAllumettes;

  /** Nombre d'allumettes dans le tas. */
  private int allumettes;

  /**
   * Crée un tas avec un numéro, un nombre maximal d'allumettes et un nombre d'allumettes.
   *
   * @param numero le numéro du tas, doit être supérieur ou égal à 1i.
   * @param maxAllumettes le nombre maximal d'allumettes, doit être supérieur ou égal à 1.
   * @param allumettes le nombre d'allumettes, doit être supérieur ou égal à 1 et inférieur ou égal
   *     au nombre maximal d'allumettes
   * @throws IllegalArgumentException si le numéro, le nombre maximal d'allumettes ou le nombre
   *     d'allumettes est invalide
   */
  public Tas(int numero, int maxAllumettes, int allumettes) {
    if (numero < 1) throw new IllegalArgumentException("Numéro de tas invalide");
    this.numero = numero;

    if (maxAllumettes < 1)
      throw new IllegalArgumentException("Nombre maximal d'allumettes invalide");
    this.maxAllumettes = maxAllumettes;

    if (allumettes < 1 || allumettes > maxAllumettes)
      throw new IllegalArgumentException("Nombre d'allumettes invalide");
    this.allumettes = allumettes;
  }

  /**
   * Récupère le nombre d'allumettes dans le tas.
   *
   * @return le nombre d'allumettes dans le tas
   */
  public int getAllumettes() {
    return allumettes;
  }

  /**
   * Récupère le nombre maximal d'allumettes dans le tas.
   *
   * @return le nombre maximal d'allumettes dans le tas
   */
  public int getMaxAllumettes() {
    return maxAllumettes;
  }

  /**
   * Récupère le numéro du tas.
   *
   * @return le numéro du tas
   */
  public int getNumero() {
    return numero;
  }

  /**
   * Vérifie si le tas est vide.
   *
   * @return true si le tas est vide, false sinon
   */
  public boolean estVide() {
    return allumettes == 0;
  }

  /**
   * Retire un nombre d'allumettes du tas.
   *
   * @param nbAllumettes le nombre d'allumettes à retirer
   * @throws IllegalArgumentException si le nombre d'allumettes à retirer est invalide
   */
  public void retirerAllumettes(int nbAllumettes) {
    if (nbAllumettes < 1 || nbAllumettes > allumettes)
      throw new IllegalArgumentException("Nombre d'allumettes à retirer invalide");
    allumettes -= nbAllumettes;
  }

  @Override
  public String toString() {
    return "    Tas \033[1m"
        + numero
        + "\033[0m :  "
        + " \033[0;32m|\033[0m".repeat(Math.max(0, allumettes));
  }
}
