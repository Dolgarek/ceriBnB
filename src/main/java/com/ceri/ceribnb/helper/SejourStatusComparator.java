package com.ceri.ceribnb.helper;

import com.ceri.ceribnb.entity.Sejour;

import java.util.Comparator;

public class SejourStatusComparator implements Comparator<Sejour> {
    @Override
    public int compare(Sejour s1, Sejour s2) {
        if (s1.getStatus().equals("EN ATTENTE") && !s2.getStatus().equals("EN ATTENTE")) {
            return -1;
        } else if (s2.getStatus().equals("EN ATTENTE") && !s1.getStatus().equals("EN ATTENTE")) {
            return 1;
        } else if (s1.getStatus().equals("VALIDE") && s2.getStatus().equals("REFUSE")) {
            return -1;
        } else if (s1.getStatus().equals("REFUSE") && s2.getStatus().equals("VALIDE")) {
            return 1;
        } else {
            return 0;
        }
    }
}

