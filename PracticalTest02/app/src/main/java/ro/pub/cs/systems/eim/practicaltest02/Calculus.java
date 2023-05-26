package ro.pub.cs.systems.eim.practicaltest02;

import androidx.annotation.NonNull;

public class Calculus {

    private final Integer add;
    private final Integer mul;

    public Calculus(Integer add, Integer mul) {
        this.add = add;
        this.mul = mul;
    }

    public Integer getAdd() {
        return add;
    }

    public Integer getMul() {
        return mul;
    }

}