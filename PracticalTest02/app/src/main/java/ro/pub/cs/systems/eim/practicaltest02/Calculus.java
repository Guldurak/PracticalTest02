package ro.pub.cs.systems.eim.practicaltest02;

import androidx.annotation.NonNull;

public class Calculus {

    //private final Integer add;
    //private final Integer mul;
    private final Integer operator_1;
    private final Integer operator_2;

    public Calculus(Integer add, Integer mul, Integer operator_1, Integer operator_2) {
        //this.add = add;
        //this.mul = mul;
        this.operator_1 = operator_1;
        this.operator_2 = operator_2;
    }

    public Integer getAdd() {
        return operator_1 + operator_2;
    }

    public Integer getMul() {
        return operator_1 * operator_2;
    }

}