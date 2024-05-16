/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.modele.nim.PlateauNim;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieAINimAleatoire implements Strategie {

    @Override
    public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {

        PlateauNim nim = (PlateauNim) plateau;

        List<Integer> tasValide = new ArrayList<>();
        Random rand = new Random();

        for(int i = 1; i <= nim.taille; i++){
            if (nim.getAllumettesRestantes(i) > 0){
                tasValide.add(i);
            }
        }

        int tasChoix = tasValide.get(rand.nextInt(tasValide.size()));

        return new ChoixNim(tasChoix, rand.nextInt(nim.getAllumettesRestantes(tasChoix)) + 1);
    }
}