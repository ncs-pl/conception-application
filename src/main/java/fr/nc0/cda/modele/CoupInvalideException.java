/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

/** Exception levée lorsqu'un coup est invalide. */
public class CoupInvalideException extends Exception {
  /**
   * Constructeur de l'exception.
   *
   * @param message Message d'erreur.
   */
  public CoupInvalideException(String message) {
    super(message);
  }
}
