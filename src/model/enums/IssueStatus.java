package model.enums;

/**
 * Created by luiz on 01/10/15.
 */
public enum IssueStatus {
    EM_ABERTO,
    APROVADA,
    EM_ANDAMENTO,
    EMCERRADA,
    CANCELADA;
    /**
     * BACKLOG
     * APPROVED
     * STARTED
     * FINISHED
     * CANCELED
     **/

    public static IssueStatus getStatus(int status){
        for (IssueStatus value: values()) if (value.ordinal()==status) return value;
        return getStatus(0);
    }
}
