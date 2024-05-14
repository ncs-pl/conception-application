/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur.strategie.strategienim;

import fr.nc0.cda.modele.joueur.strategie.Strategie;
import fr.nc0.cda.modele.nim.Nim;

public class StrategieNimGagnante implements Strategie {

    private Nim nim;

    public StrategieNimGagnante(Nim nim) {
        this.nim = nim;
    }

    public void jouerCoup(){}
}
