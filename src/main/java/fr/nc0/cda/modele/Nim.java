/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

import java.util.ArrayList;
import java.util.Iterator;

public class Nim {

  private final ArrayList<Integer> tas;
  private EtatPartie etatPartie;

  public Nim(int nbrTas) {
    tas = new ArrayList<Integer>(nbrTas);
    for (int i = 1; i <= nbrTas; ++i) {
      tas.add(2 * i - 1);
    }

    etatPartie = EtatPartie.EnCours;
  }

  public ArrayList<Integer> getTas() {
    return tas;
  }

  public void supprAllumettes(int[] choix) {
    tas.set(choix[0], tas.get(choix[0]) - choix[1]);
  }

  public EtatPartie getEtatPartie() {
    return etatPartie;
  }

  public void checkEtatPartie() {
    boolean estFini = true;
    Iterator<Integer> it = tas.iterator();
    while (it.hasNext()) {
      if (it.next() > 0) {
        estFini = false;
      }
    }

    if (estFini) {
      etatPartie = EtatPartie.Fini;
    }
  }

  public enum EtatPartie {
    EnCours,
    Fini
  }
}
