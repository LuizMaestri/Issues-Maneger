package model.enums;

public enum IssueCategory {
    CORRECAO,
    MELHORIA,
    NOVA_FUNCINALIDADE;
    /**
     * FIX,
     * IMPROVEMENT
     * RELEASE
     **/

    public static IssueCategory getCategory(int category){
        for (IssueCategory value : values()) if (value.ordinal() == category) return value;
        return getCategory(0);
    }
}
