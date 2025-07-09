package br.com.triagem_ia_sus.triagem_ia_sus.domain;

public enum TipoColaborador {
    MEDIDO("admin"),
    ENFERMEIRO("user");

    private String role;

    TipoColaborador(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
