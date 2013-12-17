package org.yinwang.rubysonar.ast;

import org.jetbrains.annotations.NotNull;
import org.yinwang.rubysonar.State;
import org.yinwang.rubysonar.types.NumType;
import org.yinwang.rubysonar.types.Type;


public class Num extends Node {

    public double n;
    public String complex = null;


    public Num(Object n, int start, int end) {
        super(start, end);
        if (n instanceof String) {
            String s = (String) n;
            Double maybeDouble = getDouble(s);
            if (maybeDouble != null) {
                this.n = maybeDouble;
            } else {
                this.complex = (String) n;
            }
        } else {
            this.n = (Double) n;
        }
    }


    public Double getDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }


    public Integer getInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }


    @NotNull
    @Override
    public Type transform(State s) {
        String typename;
        if (complex != null) {
            return new NumType("complex");
        } else {
            if (Math.floor(n) == n) {
                typename = "int";
            } else {
                typename = "float";

            }
            return new NumType(typename, n);
        }
    }


    @NotNull
    @Override
    public String toString() {
        return "<Num:" + n + ">";
    }


    @Override
    public void visit(@NotNull NodeVisitor v) {
        v.visit(this);
    }
}