/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

import java.util.ArrayList;
import java.util.Iterator;

public class Nim {

    public enum EtatPartie {
        EnCours,
        Fini
    }

  private ArrayList<Integer> tas;
  private EtatPartie etatPartie;
  private final int nbrTas;

  public Nim(int nbrTas) {
      if (nbrTas >= 1){
          this.nbrTas = nbrTas;
      } else {
          throw new IllegalArgumentException("Nombre de Tas choisis < 1");
      }
  }

  private void setupTas(){
      tas = new ArrayList<Integer>(nbrTas);
      for (int i = 1; i <= nbrTas; ++i){
          tas.add(2 * i - 1);
      }
  }

  public void demarrerPartie(){
      setupTas();
      etatPartie = EtatPartie.EnCours;
  }

  public ArrayList<Integer> getTas() {
    return tas;
  }

  public void supprAllumettes(int[] choix) {
      try {
          verifierChoix(choix);
          tas.set(choix[0], tas.get(choix[0]) - choix[1]);
      } catch (IllegalArgumentException e){
          throw e;
      }
  }

  private void verifierChoix(int[] choix) {
      if (tas.size() <= choix[0] || choix[0] < 0){
        throw new IllegalArgumentException("Valeur du tas incorrect");
      } else {
          if (choix[1] <= 0 || choix[1] > tas.get(choix[0]))
          {
              throw new IllegalArgumentException("Valeur des allumettes incorrect");
          }
      }
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
}
