class Pair {
    int fst;
    int snd;
    Pair(int f, int s) {
        fst = f;
        snd = s;
    }
    public int hashCode() {
        return fst * 9991 + snd;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return fst == p.fst && snd == p.snd;
    }
}

import java.util.Objects;
class Pair<S, T> {
    S fst;
    T snd;
    Pair(S f, T s) {
        fst = f;
        snd = s;
    }
    public int hashCode() {
        return Objects.hash(fst, snd);
    }
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return Objects.equals(fst, p.fst) && Objects.equals(snd, p.snd);
    }
}