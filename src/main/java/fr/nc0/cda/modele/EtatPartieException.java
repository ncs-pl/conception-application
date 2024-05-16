/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

/** Exception lancée lorsqu'une action est tentée alors que la partie est terminée. */
public class EtatPartieException extends Exception {
  /**
   * Constructeur de l'exception.
   *
   * @param message le message de l'exception.
   */
  public EtatPartieException(String message) {
    super(message);
  }
}
